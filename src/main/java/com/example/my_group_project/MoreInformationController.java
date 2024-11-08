package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;

public class MoreInformationController extends BaseController {
    @FXML
    private Button backButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        super.changeScene("welcomeToWebsite.fxml", "Hello View");
    }

    @FXML
    void categoryButtonOnAction(ActionEvent event) {
        super.changeScene("categoryMenu.fxml", "Category Menu");
    }

    @FXML
    void moreInforButtonOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");
    }

    @FXML
    void searchButtonOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }
}
