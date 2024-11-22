package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class UserProfileUserController extends UserMenuController {
    @FXML
    void highlightButtonOnAction(ActionEvent event) {
        super.changeScene("highlightBook.fxml", "HighLight Book");
    }

    @FXML
    void logOutOnAction(ActionEvent event) {
         super.logOutOnAction(event);
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

