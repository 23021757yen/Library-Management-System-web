package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminHomeScene extends AdminMenuController {

    @FXML
    private VBox vBox;

    @FXML
    private TextField searchTextField;

    @FXML
    void addButtonOnAction(ActionEvent event) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.setPrefHeight(45);
        hBox.setPrefWidth(885);

        TextField bookIdField = createTextField("Book ID", 111);
        TextField titleField = createTextField("Name of Book", 242);
        TextField authorField = createTextField("Author", 198);
        TextField genreField = createTextField("Category", 147);
        TextField amountField = createTextField("Quantity", 161);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveBookDetails(bookIdField, titleField, authorField, genreField, amountField));

        hBox.getChildren().addAll(bookIdField, titleField, authorField, genreField, amountField, saveButton);

        vBox.getChildren().add(hBox);
    }

    private TextField createTextField(String placeholder, double width) {
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setPrefWidth(width);
        textField.setPrefHeight(45);
        return textField;
    }

    private void saveBookDetails(TextField bookIdField, TextField titleField, TextField authorField, TextField genreField, TextField amountField) {
        String bookId = bookIdField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        String amount = amountField.getText();

        // Validate input
        if (bookId.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || amount.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        try {
            int amountInt = Integer.parseInt(amount);

            // Database logic to save the book details
            String query = "INSERT INTO books (book_ID, title, author, kind, amount) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, bookId);
                pstmt.setString(2, title);
                pstmt.setString(3, author);
                pstmt.setString(4, genre);
                pstmt.setInt(5, amountInt);
                pstmt.executeUpdate();
                showAlert("Success", "Book details saved successfully!");
            } catch (SQLException e) {
                showAlert("Error", "Failed to save book details.");
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Amount must be a number.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        String searchQuery = searchTextField.getText();
        searchBooksByTitle(searchQuery);
    }

    private void searchBooksByTitle(String title) {
        String query = "SELECT book_ID, title, author, kind, amount FROM books WHERE title LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            vBox.getChildren().clear(); // Clear existing items
            boolean booksFound = false;
            while (rs.next()) {
                booksFound = true;
                Book book = new Book(rs.getString("book_ID"), rs.getString("title"), rs.getString("author"), rs.getString("kind"), rs.getInt("amount"));
                HBox row = createRow(book);
                vBox.getChildren().add(row);
            }
            if (!booksFound) {
                showAlert("No books found with the title: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Results");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        loadBooks();
    }

    public void loadBooks() {
        String query = "SELECT book_ID, title, author, kind, amount FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            vBox.getChildren().clear(); // Clear existing items
            while (rs.next()) {
                Book book = new Book(rs.getString("book_ID"), rs.getString("title"), rs.getString("author"), rs.getString("kind"), rs.getInt("amount"));
                HBox row = createRow(book);
                vBox.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private HBox createRow(Book book) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.setPrefHeight(45);
        hBox.setPrefWidth(885);

        ScrollPane bookIdPane = createScrollablePane(111, book.getId());
        ScrollPane titlePane = createScrollablePane(242, book.getTitle());
        ScrollPane authorPane = createScrollablePane(198, book.getAuthors());
        ScrollPane kindPane = createScrollablePane(147, book.getGenre());
        Pane amountPane = createCenterAlignedPane(161, Integer.toString(book.getAmount()));

        hBox.getChildren().addAll(bookIdPane, titlePane, authorPane, kindPane, amountPane);

        // Add click event handler to hBox
        hBox.setOnMouseClicked(event -> {
            try {
                loadAdminBookInformationScene(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return hBox;
    }

    private Pane createCenterAlignedPane(double width, String value) {
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(45);

        Label label = new Label(value);
        label.setAlignment(Pos.CENTER);
        label.setLayoutX((width - 80) / 2); // Center align based on Pane width
        label.setLayoutY(14);
        label.setFont(new Font("System Bold", 15));

        pane.getChildren().add(label);
        return pane;
    }

    private ScrollPane createScrollablePane(double width, String value) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(width);
        scrollPane.setPrefHeight(45);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide vertical scroll bar

        Pane pane = new Pane();
        Label label = new Label(value);
        label.setLayoutX(10);
        label.setLayoutY(14);
        label.setFont(new Font("System Bold", 15));
        pane.getChildren().add(label);

        scrollPane.setContent(pane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("hidden-scrollbar");

        return scrollPane;
    }

    private void loadAdminBookInformationScene(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/my_group_project/AdminBookInformation.fxml"));
        Parent root = loader.load();

        // Pass the book data to the new controller
        AdminBookInformationController controller = loader.getController();
        controller.setBookData(book);

        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
