package com.example.my_group_project ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserHomeController extends BaseController {

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button readingNowButton;

    @FXML
    private Button searchButton;

    @FXML
    void cateButtonOnAction(ActionEvent event) {
        super.changeScene("categoryMenu.fxml", "Category Menu");
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    void moreInforButtonOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");}

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        super.changeScene("welcomeToWebsite.fxml", "HelloView");
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");}

    @FXML
    void searchButtonOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }

    @FXML
    void readingNowButtonOnAction(ActionEvent event) {
        super.changeScene("bookProfile.fxml", "Book Profile");
    }

}
