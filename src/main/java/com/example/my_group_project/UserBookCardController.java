package com.example.my_group_project;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

public class UserBookCardController {

    @FXML
    private ImageView setImageBook;
    @FXML
    private Label nameOfBook;
    @FXML
    private Label numberOfRead;
    @FXML
    private Label numberOfBorrow;
    @FXML
    private Label setAuthor;
    @FXML
    private Label setCategory;

    public void setBookDetails(Book book) {
        nameOfBook.setText(book.getTitle());
        setAuthor.setText(book.getAuthors());
        setCategory.setText(book.getGenre());
        numberOfRead.setText("Views: " /* logic to get number of views */);
        numberOfBorrow.setText("Borrows: " /* logic to get number of borrows */);
        if (book.getImage() != null && !book.getImage().isEmpty()) {
            try {
                Image image = new Image(book.getImage());
                setImageBook.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
            }
        }
    }
}
