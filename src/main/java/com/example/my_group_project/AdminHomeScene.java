package com.example.my_group_project;

import com.almasb.fxgl.audio.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AdminHomeScene extends AdminMenuController {

    @FXML
    private VBox vBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Text borrowCountText;
    @FXML
    private Text numberOfBookText;

    @FXML
    public void initialize() {
        loadBorrowCount();
        loadNumberOfBooks();
        displayBooks();
        searchTextField.setOnKeyReleased(event -> filterSearch());
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.setPrefHeight(45);
        hBox.setPrefWidth(885);

        TextField bookIdField = createTextField("Book ID", 71);
        TextField titleField = createTextField("Name of Book", 217);
        TextField authorField = createTextField("Author", 157);
        TextField genreField = createTextField("Category", 114);
        TextField amountField = createTextField("Quantity", 71);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            saveBookDetails(bookIdField, titleField, authorField, genreField, amountField);
            SoundManager.playSound("src/resources/success.wav"); // Play sound on save
        });

        hBox.getChildren().addAll(bookIdField, titleField, authorField, genreField, amountField, saveButton);

        vBox.getChildren().add(hBox);
        SoundManager.playSound("src/main/resources/soundEffects/SEFE_Bell.wav");
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
            SoundManager.playSound("src/main/resources/soundEffects/SEFE_Denied.wav");
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
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_Censor.wav");
            } catch (SQLException e) {
                showAlert("Error", "Failed to save book details.");
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_Denied.wav");
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Amount must be a number.");
            SoundManager.playSound("src/main/resources/soundEffects/SEFE_Denied.wav");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadBorrowCount() {
        String borrowCountQuery = "SELECT COUNT(*) AS borrowCount FROM borrow WHERE status = 'borrowed'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(borrowCountQuery)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int borrowCount = rs.getInt("borrowCount");
                borrowCountText.setText(String.valueOf(borrowCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadNumberOfBooks() {
        String numberOfBooksQuery = "SELECT SUM(amount) AS totalAmount FROM books";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(numberOfBooksQuery)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalAmount = rs.getInt("totalAmount");
                numberOfBookText.setText(String.valueOf(totalAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected static List<Book> getBookFromDatabase() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT book_ID, title, author, kind, amount FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String bookID = rs.getString("book_ID");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("kind");
                int quantity = rs.getInt("amount");
                Book book = new Book(bookID, title, author, genre, quantity);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private void displayBooks() {
        List<Book> bookList = getBookFromDatabase();
        int index = 0;
        if (bookList.isEmpty()) {
            vBox.getChildren().add(new Label("No books."));
            return;
        } else {
            for (Book book : bookList) {
                if (book == null) {
                    continue;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePane.fxml"));
                    HBox bookHBox = loader.load();

                    AdminHomePaneController adminHomePane = loader.getController();
                    adminHomePane.setBookDetail(book); // Update book details
                    final int currentIndex = index;
                    if (currentIndex % 2 == 0) {
                        bookHBox.setStyle("-fx-background-color: #f7efd8;"); // Background color for even rows
                    } else {
                        bookHBox.setStyle("-fx-background-color: #ffffff;"); // Background color for odd rows
                    }

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(bookHBox);

                    stackPane.setOnMouseEntered(event -> {
                        bookHBox.setStyle("-fx-background-color: #ffc100; -fx-cursor: hand;");
                    });

                    stackPane.setOnMouseExited(event -> {
                        if (currentIndex % 2 == 0) {
                            bookHBox.setStyle("-fx-background-color: #f7efd8;");
                        } else {
                            bookHBox.setStyle("-fx-background-color: #ffffff;");
                        }
                    });
                    bookHBox.setOnMouseClicked(event -> {
                        showBook(adminHomePane.getCurrentBookID());
                    });
                    vBox.getChildren().add(stackPane);
                    index++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void filterSearch() {
        String search = searchTextField.getText();

        List<Book> filterBook = new ArrayList<>();
        for (Book book : getBookFromDatabase()) {
            if (book.getId() != null && book.getId().contains(search) ||
                    book.getTitle() != null && book.getTitle().contains(search) ||
                    book.getAuthors() != null && book.getAuthors().contains(search) ||
                    book.getGenre() != null && book.getGenre().contains(search) ||
                    String.valueOf(book.getAmount()).contains(search)) {
                filterBook.add(book);
            }
        }
        displayFilterSearch(filterBook);
    }

        private void displayFilterSearch(List<Book> filterBook) {
        vBox.getChildren().clear();

        if (filterBook.isEmpty()) {
            vBox.getChildren().add(new Label("No books found"));
            return;
        }

        int index = 0;
        for (Book book : filterBook) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePane.fxml"));
                HBox bookHBox = loader.load();

                AdminHomePaneController adminHomePane = loader.getController();
                adminHomePane.setBookDetail(book); // Update book details
                final int currentIndex = index;
                if (currentIndex % 2 == 0) {
                    bookHBox.setStyle("-fx-background-color: #f7efd8;"); // Background color for even rows
                } else {
                    bookHBox.setStyle("-fx-background-color: #ffffff;"); // Background color for odd rows
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(bookHBox);

                stackPane.setOnMouseEntered(event -> {
                    bookHBox.setStyle("-fx-background-color: #ffc100; -fx-cursor: hand;");
                });

                stackPane.setOnMouseExited(event -> {
                    if (currentIndex % 2 == 0) {
                        bookHBox.setStyle("-fx-background-color: #f7efd8;");
                    } else {
                        bookHBox.setStyle("-fx-background-color: #ffffff;");
                    }
                });
                bookHBox.setOnMouseClicked(event -> {
                    showBook(adminHomePane.getCurrentBookID());
                });
                vBox.getChildren().add(stackPane);
                index++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showBook(String bookID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminBookInformation.fxml"));
            Parent root = loader.load();

            // Pass the book data to the new controller
            AdminBookInformationController adminBookInformation = loader.getController();
            adminBookInformation.loadBookInformation(bookID);
            adminBookInformation.loadBorrowInformation(bookID);

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                HBox hBox = new HBox();
                hBox.setStyle("-fx-background-color: #ffffff;");
                hBox.setPrefHeight(45);
                hBox.setPrefWidth(885);

                Label bookIdLabel = new Label(book.getId());
                bookIdLabel.setPrefWidth(111);

                Label titleLabel = new Label(book.getTitle());
                titleLabel.setPrefWidth(242);

                ScrollPane authorPane = createScrollablePane(198, book.getAuthors());
                ScrollPane genrePane = createScrollablePane(147, book.getGenre());

                Label amountLabel = new Label(String.valueOf(book.getAmount()));
                amountLabel.setPrefWidth(161);

                hBox.getChildren().addAll(bookIdLabel, titleLabel, authorPane, genrePane, amountLabel);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(hBox);

                stackPane.setOnMouseEntered(event -> hBox.setStyle("-fx-background-color: #ffc100; -fx-cursor: hand;"));
                stackPane.setOnMouseExited(event -> hBox.setStyle("-fx-background-color: #ffffff;"));

                hBox.setOnMouseClicked(event -> {
                    try {
                        loadAdminBookInformationScene(book);
                        SoundManager.playSound("src/main/resources/soundEffects/SEFE_AngelsSinging.wav");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                vBox.getChildren().add(stackPane);
            }
            if (!booksFound) {
                showAlert("No book found", "No books found with the title: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAdminBookInformationScene(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminBookInformation.fxml"));
        Parent root = loader.load();

        // Pass the book data to the new controller
        AdminBookInformationController controller = loader.getController();
        controller.setBookData(book);

        Stage stage = (Stage) vBox.getScene().getWindow();
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

