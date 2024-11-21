package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public abstract class UserMenuController extends BaseController{
    @FXML
    void categoryOnAction(ActionEvent event) {
        super.changeScene("categoryMenu.fxml", "Category Menu");
    }

    @FXML
    void homeOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    void moreInforOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    void logOutOnAction(ActionEvent event) {
        super.changeScene("welcomeToWebsite.fxml", "HelloView");
    }

    @FXML
    void profileOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }
}
