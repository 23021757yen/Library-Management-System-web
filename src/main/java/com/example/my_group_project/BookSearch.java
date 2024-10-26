package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class BookSearch {
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox resultsVBox;

    @FXML
    private Button readingHereButton;

    @FXML
    private Button popularButton;

    @FXML
    private ImageView bookImageView;

    @FXML
    void bookImageViewOnAction(MouseEvent event) {

    }

    @FXML
    void popularButtonOnAction(ActionEvent event) {

    }

    @FXML
    void readingHereButtonOnAction(ActionEvent event) {

    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        searchBooks();
    }

    // Method to search books using BookAPI
    private void searchBooks() {
        String query = searchTextField.getText();
        try {
            List<Book> books = BookAPI.searchBooks(query);
            resultsVBox.getChildren().clear();
            for (Book book : books) {
                Text bookInfo = new Text(book.getTitle() + " by " + book.getAuthors());
                resultsVBox.getChildren().add(bookInfo);
            }
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
