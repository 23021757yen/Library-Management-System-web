package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class ProfileUserController extends BaseController {

    @FXML
    private Button backButton;

    @FXML
    private Button highLightButton;

    @FXML
    private Button profileFormButton;

    @FXML
    private Button historyButton;

    @FXML
    private MenuItem homeMenuItem;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private MenuItem moreInforMenuItem;

    @FXML
    private MenuItem categoryMenuItem;

    @FXML
    private MenuItem searchMenuItem;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

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

    @FXML
    void homeMenuItemOnAction(ActionEvent event) {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    void moreInforMenuItemOnAction(ActionEvent event) {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    void categoryMenuItemOnAction(ActionEvent event) {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("categoryMenu.fxml", "Category");
    }

    @FXML
    void searchMenuItemOnAction(ActionEvent event) {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("searchBook.fxml", "Searching");
    }

    @FXML
    void logOutMenuItemOnAction(ActionEvent event) {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("welcomeToWebsite.fxml", "Hello View");
    }

}
