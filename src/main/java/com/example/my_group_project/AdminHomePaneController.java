package com.example.my_group_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminHomePaneController {
    @FXML
    private Label bookID;

    @FXML
    private Label nameOfBook;

    @FXML
    private Label author;

    @FXML
    private Label category;

    @FXML
    private Label quantity;

    public static String currentBookID ;

    @FXML
    public void setBookDetail(Book book) {
        if (book == null) {
            System.out.println("Book is null.");
            return;
        }
        bookID.setText(book.getId());
        nameOfBook.setText(book.getTitle());
        author.setText(book.getAuthors());
        category.setText(book.getGenre());
        quantity.setText(String.valueOf(book.getAmount()));
    }

    public String getCurrentBookID() {
        currentBookID = bookID.getText();
        return currentBookID;
    }
}
