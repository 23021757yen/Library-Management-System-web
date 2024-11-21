package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class UserBookSearch extends BaseController {
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox resultsVBox;

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {

        System.out.println("a");
        searchBooks();
    }

    // Method to search books using BookAPI
    private void searchBooks() {
        System.out.println("0");
        String query = searchTextField.getText();
        System.out.println("1");
        try {
            List<Book> books = BookAPI.searchBooks(query);
            resultsVBox.getChildren().clear();
            for (Book book : books) {
                HBox bookInfoBox = new HBox(10);

                // Create Text for title and authors
                Text bookInfo = new Text(book.getTitle() + " by " + book.getAuthors());
                bookInfo.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-fill: #333333;");

                // Create ImageView for book image
                if (book.getImageUrl() != null) {
                    try {
                        Image image = new Image(book.getImageUrl());
                        ImageView bookImage = new ImageView(image);
                        bookImage.setFitHeight(50);
                        bookImage.setFitWidth(50);
                        bookInfoBox.getChildren().add(bookImage);
                    } catch (Exception e) {
                        System.out.println("Failed to load image: " + e.getMessage());
                    }
                }

                bookInfoBox.getChildren().add(bookInfo);

                // Set up click event to navigate to book details
                bookInfoBox.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookProfile.fxml"));
                        Parent root = loader.load();

                        UserBookProfileController controller = loader.getController();
                        controller.setBookDetails(book);

                        Stage stage = (Stage) resultsVBox.getScene().getWindow();
                        stage.setTitle("Book Profile");
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                resultsVBox.getChildren().add(bookInfoBox);
            }
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        changeScene("home.fxml", "Home");
    }
}
