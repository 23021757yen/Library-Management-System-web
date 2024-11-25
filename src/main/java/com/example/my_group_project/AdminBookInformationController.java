package com.example.my_group_project;

import com.almasb.fxgl.audio.Sound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminBookInformationController extends AdminMenuController {

    @FXML
    private TextField nameAuthorText;

    @FXML
    private TextField limitBookText;

    @FXML
    private TextField categoryBookText;

    @FXML
    private TextField goalBookText;

    @FXML
    private TextArea content;

    @FXML
    private ImageView bookImage;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox vBox2;

    @FXML
    private TableView<BorrowInfo> borrowTable;

    @FXML
    private TableColumn<BorrowInfo, Integer> accessCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> bookmarkCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> borrowCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> returnCountColumn;

    private Book currentBook;

    public void setBookData(Book book) {
        this.currentBook = book;
        loadBookInformation(book.getId());
        loadBorrowInformation(book.getId());
    }

    @FXML
    public void initialize() {
        setFieldsEditable(false);  // Make fields non-editable by default

        // Initialize TableView columns
        accessCountColumn.setCellValueFactory(cellData -> cellData.getValue().accessCountProperty().asObject());
        bookmarkCountColumn.setCellValueFactory(cellData -> cellData.getValue().bookmarkCountProperty().asObject());
        borrowCountColumn.setCellValueFactory(cellData -> cellData.getValue().borrowCountProperty().asObject());
        returnCountColumn.setCellValueFactory(cellData -> cellData.getValue().returnCountProperty().asObject());
    }

    private void setFieldsEditable(boolean editable) {
        nameAuthorText.setEditable(editable);
        limitBookText.setEditable(editable);
        categoryBookText.setEditable(editable);
        goalBookText.setEditable(editable);
        content.setEditable(editable);
    }

    public void loadBookInformation(String bookId) {
        String query = "SELECT * FROM books WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nameAuthorText.setText(rs.getString("author"));
                limitBookText.setText(String.valueOf(rs.getInt("amount")));
                categoryBookText.setText(rs.getString("kind"));
                content.setText(rs.getString("description"));
                // Load and set the book image
                String imageUrl = rs.getString("image");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image bookImageView = new Image(imageUrl);
                    bookImage.setImage(bookImageView);
                } else {
                    bookImage.setImage(null); // Clear the image if URL is not available
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading book information: " + e.getMessage());
        }
    }

    public void loadBorrowInformation(String bookId) {
        // Query to fetch the count of borrow status of the book
        String borrowCountQuery = "SELECT COUNT(*) AS borrowCount FROM borrow WHERE book_ID = ? AND status = 'borrowed'";
        String returnCountQuery = "SELECT COUNT(*) AS returnCount FROM borrow WHERE book_ID = ? AND status = 'returned'";
        // Query to fetch the view count from the books table
        String viewCountQuery = "SELECT viewCount FROM books WHERE book_ID = ?";

        ObservableList<BorrowInfo> borrowInfoList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Fetch borrow count
            int borrowCount = 0;
            try (PreparedStatement pstmt = conn.prepareStatement(borrowCountQuery)) {
                pstmt.setString(1, bookId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    borrowCount = rs.getInt("borrowCount");
                }
            }

            // Fetch view count
            int viewCount = 0;
            try (PreparedStatement viewStmt = conn.prepareStatement(viewCountQuery)) {
                viewStmt.setString(1, bookId);
                ResultSet viewRs = viewStmt.executeQuery();
                if (viewRs.next()) {
                    viewCount = viewRs.getInt("viewCount");
                }
            }

            int returnCount = 0; // Logic to fetch returnCount
            try (PreparedStatement pstmt = conn.prepareStatement(returnCountQuery)) {
                pstmt.setString(1, bookId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    returnCount = rs.getInt("returnCount");
                }
            }
            int bookmarkCount = 0; // Replace with actual logic if applicable

            // Add the borrow info to the list
            borrowInfoList.add(new BorrowInfo(viewCount, bookmarkCount, borrowCount, returnCount));
            borrowTable.setItems(borrowInfoList);

        } catch (SQLException e) {
            System.err.println("Error loading borrow information: " + e.getMessage());
        }
    }


    @FXML
    void editButtonOnAction(ActionEvent event) {
        setFieldsEditable(true);  // Enable editing
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if (currentBook != null) {
            String query = "UPDATE books SET author = ?, amount = ?, kind = ?, description = ? WHERE book_ID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nameAuthorText.getText());
                pstmt.setInt(2, Integer.parseInt(limitBookText.getText()));
                pstmt.setString(3, categoryBookText.getText());
                pstmt.setString(4, content.getText());
                pstmt.setString(5, currentBook.getId());
                pstmt.executeUpdate();
                System.out.println("Book information updated successfully.");
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_Yeehaaa.wav");
                setFieldsEditable(false);// Disable editing after save
            } catch (SQLException e) {
                System.err.println("Error saving book information: " + e.getMessage());
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_Wrong_Answer.wav");
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + e.getMessage());
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_Wrong_Answer.wav");
            }
        }
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        if (currentBook != null) {
            String query = "DELETE FROM books WHERE book_ID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, currentBook.getId());
                pstmt.executeUpdate();

                // Clear text fields after deletion
                nameAuthorText.setText("");
                limitBookText.setText("");
                categoryBookText.setText("");
                goalBookText.setText("");
                content.setText("");

                // Clear borrow table
                borrowTable.getItems().clear();

                System.out.println("Book deleted successfully.");
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_KidsCheering.wav");
            } catch (SQLException e) {
                System.err.println("Error deleting book: " + e.getMessage());
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_Wrong_Answer.wav");
            }
        }
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        String searchQuery = searchTextField.getText();
        searchBooksByTitle(searchQuery);
    }

    private void searchBooksByTitle(String title) {
        String query = "SELECT b.User_ID, b.book_ID, bk.title, u.name, b.borrowDate, b.backDate, b.status " +
                "FROM borrow b " +
                "JOIN user u ON b.User_ID = u.user_ID " +
                "JOIN books bk ON b.book_ID = bk.book_ID " +
                "WHERE bk.title LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            vBox2.getChildren().clear(); // Clear existing items
            boolean booksFound = false;
            while (rs.next()) {
                booksFound = true;
                BorrowedBook book = new BorrowedBook(rs.getString("b.User_ID"), rs.getString("b.book_ID"),
                                             rs.getString("u.name"), rs.getString("b.borrowDate"),
                                             rs.getString("b.backDate"), rs.getString("b.status"));
                HBox row = createRow(book);
                vBox2.getChildren().add(row);
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
        SoundManager.playSound("src/main/resources/soundEffects/SEFE_Alert.wav");
    }

    private HBox createRow(BorrowedBook book) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.setPrefHeight(45);
        hBox.setPrefWidth(885);

        ScrollPane userIDPane = createScrollablePane(130, book.getUserId());
        ScrollPane bookIDPane = createScrollablePane(130, book.getId());
        ScrollPane usernamePane = createScrollablePane(170, book.getUsername());
        ScrollPane borrowDatePane = createScrollablePane(140, book.getDateBorrow());
        ScrollPane backDatePane = createScrollablePane(140, book.getDateBack());
        Pane statusPane = createCenterAlignedPane(140, book.getStatus());

        hBox.getChildren().addAll(userIDPane, bookIDPane, usernamePane, borrowDatePane, backDatePane, statusPane);

        // Add click event handler to hBox
        hBox.setOnMouseClicked(event -> {
            try {
                loadAdminBookInformationScene(book);
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_BellTransition.wav");
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

    private void loadAdminBookInformationScene(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/my_group_project/AdminBookInformation.fxml"));
        Parent root = loader.load();

        // Pass the book data to the new controller
        AdminBookInformationController controller = loader.getController();
        controller.setBookData(book);

        Stage stage = (Stage) vBox2.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
}