package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UserProfileUserController extends UserMenuController {
    @FXML
    void highlightButtonOnAction(ActionEvent event) {
        super.changeScene("highlightBook.fxml", "HighLight Book");
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) {
        super.changeScene("history.fxml", "History");
    }

    @FXML
    void profileFormButtonOnAction(ActionEvent event) {
        super.changeScene("profileUserForm.fxml", "Profile");
    }
}

