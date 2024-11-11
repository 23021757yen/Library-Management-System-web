package com.example.my_group_project ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class UserCategoryController extends BaseController{
    @FXML
    private Button backButton;

    @FXML
    private MenuItem homeMenuItem;

    @FXML
    private MenuItem moreInforMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private MenuItem searchMenuItem;

    @FXML
    private MenuItem logOutMenuItem;
    @FXML
    void backButtonOnAction (ActionEvent event) throws IOException {
        super.changeScene("home.fxml", "Home");
    }
    @FXML
    void homeMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("home.fxml", "Home");
    }
    @FXML
    void searchMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("searchBook.fxml", "Searching");
    }
    @FXML
    void logOutMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("welcomeToWebsite.fxml", "Hello View");
    }
    @FXML
    void moreInforMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("moreInformation.fxml", "MoreInfor");
    }
    @FXML
    void profileMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("profileUser.fxml", "ProfileUser");
    }
}
