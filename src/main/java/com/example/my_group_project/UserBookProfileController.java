package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserBookProfileController extends UserMenuController {
    @FXML
    private Button categoryComedyButton;

    @FXML
    private Text content;

    @FXML
    private Button highlightButton;

    @FXML
    private Text nameOfAuthor;

    @FXML
    private Label nameOfBook;

    @FXML
    private Text numberOfBorrow;

    @FXML
    private Text pageViews;

    @FXML
    private ImageView bookImageView;

    @FXML
    private VBox bookTableVbox;
    private static final String MAX_ALLOWED_MATURITY_RATING = "not-mature";
    private Book currentBook = Book.getMainBook(); // Hold the current book being viewed

    @FXML
    void clickToSaveBook(MouseEvent event) throws SQLException {
        System.out.println("Luu ne !!!!");
        //highlightButton.setDisable(false);
        String highlightStyle = highlightButton.getStyle();
        if (highlightStyle.equals("-fx-text-fill: white")) {
            //hien la ban co chac muon huy luu sach khong
            if(BaseController.showAlter("Hoi lai", "Ban co muon huy luu sach khong")){
                User.getCurrentUser().removeBook(Book.getMainBook());
                highlightButton.setStyle("-fx-text-fill: black");
                //highlightButton.setVisible(true);
                UserHomeController.showIntro("Ban da huy luu sach thanh cong!", BaseController.getMainStage());
            }else {
                event.consume();
            }


        } else {
            highlightBook();
            highlightButton.setStyle("-fx-text-fill: white");
            //highlightButton.setVisible(false);
            UserHomeController.showIntro("Ban da luu sach thanh cong!", BaseController.getMainStage());
        }
    }


    void highlightBook() throws SQLException {
        try {
            //Code chỗ này
            User.getCurrentUser().saveBook(currentBook);
            //System.out.println(currentBook.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void moreBook(List<Book> books) throws IOException {
        for (int i = 0; i < books.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookRelative.fxml"));
            Pane book = fxmlLoader.load();

            ImageView image = (ImageView) book.lookup("#setImageBook");
            Label author = (Label) book.lookup("#setAuthor");
            Label title = (Label) book.lookup("#nameOfBook");
            Label category = (Label) book.lookup("#setCategory");
            Label borrow = (Label) book.lookup("#numberOfBorrow");
            Label read = (Label) book.lookup("#numberOfRead");

            Book newBook = books.get(i);
            author.setText(newBook.getAuthors());
            image.setImage(new Image(newBook.getImageUrl()));
            title.setText(newBook.getTitle());
            category.setText(newBook.getGenre());
            read.setText(String.valueOf(newBook.getViewCount()));

            image.setOnMouseClicked(mouseEvent -> {
                try {
                    UserHomeController homeController = new UserHomeController();
                    homeController.bookProfile(image, newBook);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            bookTableVbox.getChildren().add(book);
        }
    }


    public void get() {
        try {
            nameOfAuthor.setText(currentBook.getAuthors());
            nameOfBook.setText(currentBook.getTitle());
            bookImageView.setImage(new Image(currentBook.getImageUrl()));
            pageViews.setText(String.valueOf(currentBook.getViewCount()));
            numberOfBorrow.setText(String.valueOf(currentBook.getViewCount()));
            content.setText(currentBook.getDescription());
            categoryComedyButton.setText(currentBook.getGenre());

        } catch (NullPointerException e) {
            System.out.println("Null pointer here!");
        }
    }

    @FXML
    public void initialize() {
        try {
            get();
            // Ensure currentBook is initialized
            currentBook = Book.getMainBook();
            if (currentBook != null) {
                User.getCurrentUser().getRecentBook().addBook(currentBook);
                currentBook.setTime(LocalDateTime.now());

                if (User.getCurrentUser().getSavedBooks().contains(currentBook)) {
                    System.out.println("Sao lai khong cooooo");
                    highlightButton.setStyle("-fx-text-fill: white");
                } else {
                    System.out.println("Ua cai gi vayyyy");
                }

                if (!currentBook.getGenre().isEmpty()) {
                    List<Book> recommendedBooks = BookAPI.getRecommendedBooks(currentBook.getGenre(), MAX_ALLOWED_MATURITY_RATING);
                    moreBook(recommendedBooks);
                }
            } else {
                System.err.println("currentBook is null");
            }

        } catch (IOException | SQLException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    //them sach muon vao database
    @FXML
    void borrowButtonOnAction(ActionEvent event) {
        if (currentBook == null) {
            System.out.println("No book selected to borrow.");
            return;
        }
        if(UserHistoryController.getClickedBooksFromDatabase().contains(currentBook)){
            UserHomeController.showIntro("Ban da muon sach nay", BaseController.getMainStage());
            return;
        }

        // Establish the database connection
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Prepare the SQL query
            String sql = "INSERT INTO borrow (book_ID, borrowDate, backDate, User_ID, status, overTime) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, currentBook.getId());
                preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // Example due date: 7 days from now
                preparedStatement.setString(4, HelloController.userIdMain); // Replace with actual user ID
                preparedStatement.setString(5, "borrowed"); // Example status
                preparedStatement.setString(6, "no"); // Example overTime status

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book successfully borrowed: " + currentBook.getTitle());
                } else {
                    System.out.println("Failed to borrow book: " + currentBook.getTitle());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserHomeController.showIntro("Book borrowed successfully", BaseController.getMainStage());
    }

    @FXML
    void pageViewsOnAction(ActionEvent event) {
        incrementPageViews(currentBook.getId());
    }

    @FXML
    void numberOfBorrowOnAction(ActionEvent event) {
        incrementNumberOfBorrows(currentBook.getId());
    }


    public void setBookDetails(Book book) {
        if (book == null) {
            System.err.println("Book is null.");
            return;
        }
        currentBook = book;
        //saveBookToHistory(); // Save the book to history when setting details
        incrementPageViews(currentBook.getId()); // Increment page views on book details view

        javafx.application.Platform.runLater(() -> {
            nameOfBook.setText(book.getTitle());
            nameOfAuthor.setText(book.getAuthors());
            content.setText(book.getDescription());

            //highlightButton.setVisible(true);
            bookImageView.setImage(null); // Clear any previous image
            if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
                try {
                    Image image = new Image(book.getImageUrl());
                    bookImageView.setImage(image);
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + e.getMessage());
                }
            }

            // Fetch recommended books based on the genre of the selected book
            try {
                List<Book> recommendedBooks = BookAPI.getRecommendedBooks(book.getGenre(), MAX_ALLOWED_MATURITY_RATING);
                displayRecommendedBooks(recommendedBooks);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }

            // Fetch and display views, borrows, and readings
            fetchAndDisplayBookMetrics(currentBook.getId());
        });
    }



    private void saveBookToHistory() {
        BookUtils.saveBookToHistory(currentBook, HelloController.userIdMain);
    }

    private void fetchAndDisplayBookMetrics(String bookId) {
        String fetchMetricsSql = "SELECT viewCount, borrowCount FROM books WHERE book_ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement fetchStmt = connection.prepareStatement(fetchMetricsSql)) {

            fetchStmt.setString(1, bookId);
            try (ResultSet resultSet = fetchStmt.executeQuery()) {
                if (resultSet.next()) {
                    int views = resultSet.getInt("viewCount");
                    int borrows = resultSet.getInt("borrowCount");

                    javafx.application.Platform.runLater(() -> {
                        pageViews.setText(String.valueOf(views));
                        numberOfBorrow.setText(String.valueOf(borrows));
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error while fetching book metrics: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayRecommendedBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            Text noBooksText = new Text("No recommended books available.");
            noBooksText.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-fill: #333333;");
            bookTableVbox.getChildren().clear(); // Clear previous entries
            bookTableVbox.getChildren().add(noBooksText); // Show message if no books are available
            return;
        }

        bookTableVbox.getChildren().clear(); // Clear previous entries

        for (Book book : books) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCard.fxml"));
                Pane bookCard = loader.load();

                // Access the BookCardController to set the book details
                UserBookCardController cardController = loader.getController();
                cardController.setBookDetails(book);

                // Set click event on the bookCard
                bookCard.setOnMouseClicked(event -> {
                    System.out.println("Book clicked: " + book.getTitle());
                    setBookDetails(book); // Set the selected book details
                });

                bookTableVbox.getChildren().add(bookCard); // Add bookCard to bookTableVbox
            } catch (IOException e) {
                System.err.println("Failed to load book card: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void incrementPageViews(String bookId) {
        String query = "UPDATE books SET viewCount = viewCount + 1 WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void incrementNumberOfBorrows(String bookId) {
        String query = "UPDATE books SET bookCount =  bookCount + 1 WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
