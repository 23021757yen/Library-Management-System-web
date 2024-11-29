package com.example.my_group_project.Controllers.User;

import com.example.my_group_project.Controllers.BaseController;
import com.example.my_group_project.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.SQLException;

public abstract class UserMenuController extends BaseController {
    @FXML
    public void homeOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    public void moreInforOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    public void gameButtonOnAction(ActionEvent event) { super.changeScene("bookQuizGameStart.fxml", "bookQuizGameStart");};

    @FXML
    void logOutOnAction(ActionEvent event) {
        if (showAlert("Dang xuat", "Ban muon dang xuat chu???")) {
            try {
                HelloApplication.saveRecentBooksToDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            changeScene("welcomeToWebsite.fxml", "HelloView");
        } else {
            event.consume();
        }


    }

    @FXML
    public void profileOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");
    }

    @FXML
    public void searchOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }

    @FXML
    public void gameOnAction(ActionEvent event) {
        super.changeScene("bookQuizGame.fxml", "Game Time!");
    }
}
