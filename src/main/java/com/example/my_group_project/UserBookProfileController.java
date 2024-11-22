package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

public class UserBookProfileController extends UserMenuController {
    @FXML
    private Button homeButton;

    @FXML
    private Text nameOfBookTextField;

    @FXML
    private Text contentTextField;

    @FXML
    private Text nameOfAuthorTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button borrowButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button categoryComedyButton;

    @FXML
    private Text content;

    @FXML
    private Button highlightButton;


    @FXML
    private Text limitAge;

    @FXML
    private Button logOutButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Text nameOfAuthor;

    @FXML
    private Label nameOfBook;

    @FXML
    private Text numberOfBorrow;

    //***
    @FXML
    private Text numberOfRead;

    @FXML
    private Text pageViews;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;


    @FXML
    private ImageView bookImageView;

    @FXML
    private ImageView highlightImage;

    @FXML
    private VBox bookTableVbox;
    private static final String MAX_ALLOWED_MATURITY_RATING = "not-mature";
    private Book currentBook = Book.getMainBook(); // Hold the current book being viewed

    @FXML
    void clickToSaveBook(MouseEvent event) throws SQLException {
        System.out.println("Luu ne !!!!");
        Image currentImage = highlightImage.getImage();
        Image previousImage = new Image(getClass().getResource("/picture/remember.png").toExternalForm());
        Image lateImage = new Image(getClass().getResource("/picture/remembered.png").toExternalForm());
        if (currentImage != null && currentImage.getUrl() != null) {
            if (currentImage.getUrl().contains("remembered.png")) {
                //hien la ban co chac muon huy luu sach khong
                if (BaseController.showAlter("Hoi lai", "Ban co muon huy luu sach khong")) {
                    User.getCurrentUser().removedBooks(Book.getMainBook());
                    highlightImage.setImage(previousImage);
                    highlightImage.setFitWidth(21);
                    highlightImage.setFitHeight(23);
                    highlightImage.setPreserveRatio(true);
                    UserHomeController.showIntro("Ban da huy luu sach thanh cong!", BaseController.getMainStage());
                }else {
                    event.consume();
                }
            }  else if(currentImage.getUrl().contains("remember.png")) {
                highlightImage.setImage(lateImage);
                highlightBook();
                UserHomeController.showIntro("Ban da luu sach thanh cong!", BaseController.getMainStage());
            }
        } else {
            System.out.println("Không có ảnh hiển thị");
        }
    }


    void highlightBook() throws SQLException {
        try {
            //Code chỗ này
            User.getCurrentUser().savedBooks(currentBook, User.getCurrentUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void moreBook(List<Book> books) throws IOException {
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookCard.fxml"));
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
            //borrow.setText(newbook.);
            read.setText(String.valueOf(newBook.getViewCount()));

            /*image.setOnMouseClicked(mouseEvent -> {
                try {
                    //Code chỗn này
                    UserHomeController homeController = new UserHomeController();
                    homeController.bookProfile(image, newBook);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

             */
            bookTableVbox.getChildren().add(book);
        }
    }


    public void get() {
        try {
            nameOfAuthor.setText(currentBook.getAuthors());
            nameOfBook.setText(currentBook.getTitle());
            bookImageView.setImage(new Image(currentBook.getImageUrl()));
            pageViews.setText(String.valueOf(currentBook.getViewCount()));
            numberOfBorrow.setText(String.valueOf(currentBook.getBorrowCount()));
            content.setText(currentBook.getDescription());
            categoryComedyButton.setText(currentBook.getGenre());

        } catch (NullPointerException e) {
            System.out.println("Null pointer here!");
        }
    }

    @FXML
    void initialize() {
        try {
            get();
            // ca cho nay nha
            User.getCurrentUser().getRecentBookConTroller().addBook(Book.getMainBook());
            currentBook.setTime(LocalDateTime.now());
            if (User.getCurrentUser().getSavedBooks().contains(Book.getMainBook())) {
                URL image = getClass().getResource("/picture/remembered.png");
                highlightImage.setImage(new Image(image.toExternalForm()));
                highlightImage.setFitWidth(21);
                highlightImage.setFitHeight(23);
                highlightImage.setPreserveRatio(true);
            } else {
                System.out.println("Ua cai gi vayyyy");
            }
            // BookAPI bookAPI = new BookAPI();
            if (!currentBook.getGenre().isEmpty()) {
                List<Book> recommendedBooks = BookAPI.getRecommendedBooks(currentBook.getGenre(), MAX_ALLOWED_MATURITY_RATING);
                displayRecommendedBooks(recommendedBooks);
            } else {
                //List<Book> recommendedBooks = BookAPI.getRecommendedBooks("", MAX_ALLOWED_MATURITY_RATING);
                //moreBook(recommendedBooks);
            }

        } catch (IOException | SQLException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    //them sach muon vao database
    @FXML
    void borrowButtonOnAction(ActionEvent event) throws SQLException {
        if (currentBook == null) {
            System.out.println("No book selected to borrow.");
            return;
        }
        if (UserHistoryController.getClickedBooksFromDatabase().contains(currentBook)) {
            //System.out.println("Chạy qua cái này rôi fnha má");
            UserHomeController.showIntro("Ban da muon sach nay", BaseController.getMainStage());
            return;
        }

        // Establish the database connection
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Prepare the SQL query
            String sql = "INSERT INTO borrow (book_ID, endDate, dueDate, User_ID, status, overTime) VALUES (?, ?, ?, ?, ?, ?)";
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

        UserHomeController.showIntro("Bạn đã muownj thành công", BaseController.getMainStage());
        BookController.updateViewCount(currentBook.getId(),
                "UPDATE books SET borrowCount = borrowCount + 1 WHERE book_ID = ?");
    }

    /*@FXML
    void pageViewsOnAction(ActionEvent event) {
        incrementPageViews(currentBook.getId());
    }

     */

    /*@FXML
    void numberOfReadOnAction(ActionEvent event) {
        incrementNumberOfReads(currentBook.getId());
    }

    @FXML
    void numberOfBorrowOnAction(ActionEvent event) {
        incrementNumberOfBorrows(currentBook.getId());
    }

     */


    public void setBookDetails(Book book) throws SQLException {
        if (book == null) {
            System.err.println("Book is null.");
            return;
        }
        currentBook = book;
        //saveBookToHistory(); // Save the book to history when setting details
        BookController.updateViewCount(currentBook.getId(),
                "UPDATE books SET viewCount = viewCount + 1 WHERE book_ID = ?"); // Increment page views on book details view

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
        String fetchMetricsSql = "SELECT viewCount, viewCount, borrowCount FROM books WHERE book_ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement fetchStmt = connection.prepareStatement(fetchMetricsSql)) {

            fetchStmt.setString(1, bookId);
            try (ResultSet resultSet = fetchStmt.executeQuery()) {
                if (resultSet.next()) {
                    int views = resultSet.getInt("viewCount");
                    int readings = resultSet.getInt("viewCount");
                    int borrows = resultSet.getInt("borrowCount");

                    javafx.application.Platform.runLater(() -> {
                        pageViews.setText(String.valueOf(views));
                        numberOfBorrow.setText(String.valueOf(borrows));
                        numberOfRead.setText(String.valueOf(readings));
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
                //UserBookCardController cardController = loader.getController();
                //cardController.setBookDetails(book);

                ImageView image = (ImageView) bookCard.lookup("#setImageBook");
                Label author = (Label) bookCard.lookup("#setAuthor");
                Label title = (Label) bookCard.lookup("#nameOfBook");
                Label category = (Label) bookCard.lookup("#setCategory");
                Label borrow = (Label) bookCard.lookup("#numberOfBorrow");
                Label read = (Label) bookCard.lookup("#numberOfRead");

                if (book.getImageUrl() == null || book.getImageUrl().isEmpty()) {
                    image.setImage(new Image(getClass().getResource("/picture/no_image.png").toExternalForm()));
                } else {
                    image.setImage(new Image(book.getImageUrl()));
                }
                if (UserHomeController.checkBook(book)) {
                    read.setText(String.valueOf(book.getViewCount()));
                    borrow.setText(String.valueOf(book.getBorrowCount()));
                } else {
                    borrow.setText(String.valueOf('0'));
                    read.setText(String.valueOf('0'));
                }
                //Book newBook = books.get(i);
                author.setText(book.getAuthors());

                title.setText(book.getTitle());
                category.setText(book.getGenre());


                // Set click event on the bookCard
                bookCard.setOnMouseClicked(event -> {
                    System.out.println("Book clicked: " + book.getTitle());
                    //UserHistoryController.showBookProfile(book, bookCard);
                    try {
                        UserHomeController.bookProfile(bookCard, book);
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    //setBookDetails(book); // Set the selected book details
                });


                bookTableVbox.getChildren().add(bookCard); // Add bookCard to bookTableVbox
            } catch (IOException e) {
                System.err.println("Failed to load book card: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void logOutOnAction(ActionEvent event) {
         super.logOutOnAction(event);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Main");
    }
}