package com.example.my_group_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AdminOneUserProfilePaneController {
    @FXML
    private Label bookId;

    @FXML
    private HBox hBox;

    @FXML
    private Label bookName;

    @FXML
    private Label dueDate;

    @FXML
    private Label endDate;

    @FXML
    private Label status;

    @FXML
    private Label userId;

    @FXML
    public void setUserBorrowDetail(BorrowedBook brBook) {
        if (brBook == null) {
            return;
        }
        userId.setText(brBook.getUserId());
        status.setText(brBook.getStatus());
        endDate.setText(brBook.getDateBorrow());
        dueDate.setText(brBook.getDateBack());
        bookId.setText(brBook.getId());
        bookName.setText(brBook.getTitle());
    }

}
