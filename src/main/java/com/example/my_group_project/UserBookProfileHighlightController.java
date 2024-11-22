package com.example.my_group_project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

public class UserBookProfileHighlightController {

    @FXML
    private Text authorTextField;

    @FXML
    private Text contentTextField;

    @FXML
    private Label nameOfBookTextField;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Button highlightButton;

    @FXML
    private ScrollPane scrollPane;

    public void setBookDetails(Book book) {
        if (book == null) {
            System.err.println("Book is null in setBookDetails.");
            return;
        }
        nameOfBookTextField.setText(book.getTitle());
        authorTextField.setText(book.getAuthors());
        contentTextField.setText(book.getDescription());
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            try {
                Image image = new Image(book.getImageUrl());
                bookImageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
            }
        }
    }

}
