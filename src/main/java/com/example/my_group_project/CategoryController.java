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

public class CategoryController {
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
    private MenuItem settingMenuItem;
    @FXML
    private void loadScene(String fxml, String title) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage.setTitle(title);
        stage.setScene(new Scene(root));
    }
    @FXML
    void backButtonOnAction (ActionEvent event) throws IOException {
        loadScene("home.fxml", "Home");
    }
    @FXML
    void homeMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        loadScene("home.fxml", "Home");
    }
    @FXML
    void searchMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        loadScene("searchBook.fxml", "Searching");
    }
    @FXML
    void settingMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        loadScene("settingUser.fxml", "Setting");
    }
    @FXML
    void moreInforMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        loadScene("moreInfor.fxml", "MoreInfor");
    }
    @FXML
    void profileMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        loadScene("profileUser.fxml", "ProfileUser");
    }
}
