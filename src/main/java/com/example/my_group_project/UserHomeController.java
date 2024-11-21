package com.example.my_group_project;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.io.IOException;

public class UserHomeController extends BaseController {

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button readingNowButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label nameOfPopular;

    @FXML
    private Text descriptionHere;

    @FXML
    private Text authorBook;

    @FXML
    private Pane imageBook;

    @FXML
    private HBox booksHere;

    @FXML
    private VBox bookTableVbox;

    @FXML
    private HBox hBoxBooks;

    @FXML
    private ImageView imageBooks;


    //popularBook
    void suggestListBooks(List<Book> books) throws IOException, SQLException {
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookRequest.fxml"));
            Pane book = fxmlLoader.load();
            Book newBook = books.get(i);
            ImageView image = (ImageView) book.lookup("#setImageBook");
            Label description = (Label) book.lookup("#setContentBook");
            Text title = (Text) book.lookup("#setPageViews");

            description.setText(newBook.getDescription());
            title.setText(newBook.getTitle());
            image.setImage(new Image(newBook.getImage()));

            image.setOnMouseClicked(mouseEvent -> {
                try {
                    bookProfile(image, newBook);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });

            booksHere.getChildren().add(book);
        }
    }


    //latestbooks
    void latestBooks(List<Book> books) throws IOException, SQLException {
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookRequest.fxml"));
            Pane book = fxmlLoader.load();
            Book newBook = books.get(i);
            ImageView image = (ImageView) book.lookup("#setImageBook");
            Label description = (Label) book.lookup("#setContentBook");
            Text title = (Text) book.lookup("#setPageViews");

            description.setText(newBook.getDescription());
            title.setText(newBook.getTitle());
            image.setImage(new Image(newBook.getImage()));

            image.setOnMouseClicked(mouseEvent -> {
                try {
                    bookProfile(image, newBook);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });

            hBoxBooks.getChildren().add(book);
        }

    }


    //recentBooks
    void recentBooks(List<Book> books) throws IOException {
        //System.out.println(books);
        if(books.isEmpty()){
            return;
        }else {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookRelative.fxml"));
                Pane book = fxmlLoader.load();
                Book newbook = books.get(i);

                ImageView image = (ImageView) book.lookup("#setImageBook");
                Label author = (Label) book.lookup("#setAuthor");
                Label title = (Label) book.lookup("#nameOfBook");
                Label category = (Label) book.lookup("#setCategory");
                Label borrow = (Label) book.lookup("#numberOfBorrow");
                Label read = (Label) book.lookup("#numberOfRead");

                image.setImage(new Image(newbook.getImage()));
                author.setText(newbook.getAuthors());
                title.setText(newbook.getTitle());
                category.setText(newbook.getGenre());
                //borrow.setText(newbook.);
                read.setText(String.valueOf(newbook.getViewCount()));

                image.setOnMouseClicked(mouseEvent -> {
                    try {
                        bookProfile(image, newbook);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });


                bookTableVbox.getChildren().add(book);
            }
        }
    }

    //book popular
   void getPopularBook() throws SQLException {
        Book popularBook = BookController.getListofBooks().get(0);
        nameOfPopular.setText(popularBook.getTitle());
        descriptionHere.setText(popularBook.getDescription());
        authorBook.setText(popularBook.getAuthors());
        imageBooks.setImage(new Image(popularBook.getImage()));
        imageBooks.setOnMouseClicked(mouseEvent -> {
            try {
                bookProfile(imageBooks, popularBook);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }

    //hien ca
    @FXML
    void initialize() {
        try {
            showIntro("Chao mung ban!",BaseController.getMainStage());
            //System.out.println(BaseController.getMainStage().getTitle());
            getPopularBook();
            //BookAPI bookAPI = new BookAPI();
            //suggestListBooks(bookAPI.searchBooks("fiction"));
            //latestBooks(bookAPI.searchBooks("fiction"));
            //recentBooks(bookAPI.searchBooks("fiction"));
            suggestListBooks(BookController.getListofBooks());
            latestBooks(BookController.getListofBooks());
            //chu y cho nay
            recentBooks(User.getCurrentUser().getRecentBookConTroller().getRecentbooks());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    //hien ra main books
    @FXML
    void bookProfile(ImageView image, Book book) throws IOException, SQLException {
        book = checkBook(book);
        if (book.getId().isEmpty()) {
            addToData(book);
        } else {
            BookController.updateViewCount(book.getId());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookProfile.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) image.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Books");
        stage.setScene(scene);
    }

    //kiem tra xem sach da co trong sql
    Book checkBook(Book book) throws SQLException {
        Connection connectDB = DatabaseConnection.getConnection();
        String nameQuery = "SELECT * FROM books WHERE title = ? AND author = ?";
        PreparedStatement checkStatement = connectDB.prepareStatement(nameQuery);
        checkStatement.setString(1, book.getTitle());
        checkStatement.setString(2, book.getAuthors());
        ResultSet resultSet1 = checkStatement.executeQuery();

        String ID = "";
        if (resultSet1.next()) {
            ID = resultSet1.getString("book_ID");
            book.setViewCount(resultSet1.getInt("viewCount"));
            book.setId(resultSet1.getString("book_ID"));
        }
        Book.setMainBook(book);
        return book;
    }

    //them sach sau khi check
    void addToData(Book book) throws SQLException {
        String query = "INSERT INTO `books`(`title`, `amount`, `description`, `location`, `yearPublic`, `price`, `kind`, `author`, `image`, `addDate`, `viewCount`) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, 10);
            statement.setString(3, book.getDescription());
            statement.setString(4, "somewhere in library");
            statement.setInt(5, 2015);
            statement.setInt(6, 200);
            statement.setString(7, book.getGenre());
            statement.setString(8, book.getAuthors());
            statement.setString(9, book.getImage());
            statement.setString(10, String.valueOf(LocalDate.now()));
            statement.setInt(11, book.getViewCount() + 1);
            statement.executeUpdate();

            book.setViewCount(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //hien "Chao mung ban " sau khi dang nhap vao
    //gap loi luc nao vao home la cu hien TT
    public static void showIntro(String message, Stage stage){
        Popup popup = new Popup();
        popup.setAutoHide(true);

        Label popupContent = new Label(message);
        popupContent.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        popupContent.setFont(new Font("Arial", 14));
        popupContent.setTextFill(Color.WHITE);

        Pane pane = new Pane(popupContent);
        pane.setStyle("-fx-background-color: white ");
        popup.getContent().add(pane);
        popup.show(stage);
        popup.setX(stage.getX() + stage.getWidth() / 2 );
        popup.setY(stage.getY() + stage.getHeight() / 2);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(10), popupContent);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> popup.hide());

        fadeOut.play();
    }


    @FXML
    void cateButtonOnAction(ActionEvent event) {
        super.changeScene("categoryMenu.fxml", "Category Menu");
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    void moreInforButtonOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        if(BaseController.showAlter("Dang xuat", "Ban muon dang xuat chu???")){
            super.changeScene("welcomeToWebsite.fxml", "HelloView");
        }else {
            event.consume();
        }


    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");
    }

    @FXML
    void searchButtonOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }

    @FXML
    void readingNowButtonOnAction(ActionEvent event) {
        super.changeScene("bookProfile.fxml", "Book Profile");
    }

}
