package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class UserBookProfileController extends BaseController {
    @FXML
    private VBox bookTableVbox;

    @FXML
    private Button borrowButton;

    @FXML
    private Text nameOfBook;

    @FXML
    private Button categoryComedyButton;

    @FXML
    private Text limitAge;

    @FXML
    private Text nameOfAuthor;

    @FXML
    private Text pageViews;

    @FXML
    private Text numberOfRead;

    @FXML
    private Text numberOfBorrow;

    @FXML
    private Button highlightButton;

    @FXML
    private Text content;

    @FXML
    private Button categoryButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ImageView bookImageView;

    private static final String MAX_ALLOWED_MATURITY_RATING = "not-mature";

    @FXML
    void backButtonOnAction(ActionEvent event) {
        changeScene("searchBook.fxml", "Book Search");
    }

    @FXML
    void categoryButtonOnAction(ActionEvent event) {
        changeScene("categoryMenu.fxml", "CategoryMenu");
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        changeScene("home.fxml", "Home");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        super.changeScene("welcomeToWebsite.fxml", "HelloView");
    }

    @FXML
    void moreInforButtonOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");
    }

    @FXML
    void searchButtonOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }

    public void setBookDetails(Book book) {
        if (book == null) {
            System.err.println("Book is null.");
            return;
        }
        javafx.application.Platform.runLater(() -> {
            nameOfBook.setText(book.getTitle());
            nameOfAuthor.setText(book.getAuthors());
            content.setText(book.getDescription());
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
        });
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
}