package com.example.my_group_project ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AdminUserManagementController extends BaseController {

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Button logOutButton;

    @FXML
    private Button reportButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button userManagementButton;

    @FXML
    private VBox vBox;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.backButtonOnAction(event);
    }

    @FXML
    void bookBorrowButtonOnAction(ActionEvent event) {
        changeScene("AdminBorrowBook.fxml", "AdminBorrowBook");
    }

    @FXML
    void homeScene1ButtononOnAction(ActionEvent event) {
        changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        super.logOutButtonOnAction(event);
    }

    @FXML
    void reportButtonOnAction(ActionEvent event) {
        changeScene("AdminReport.fxml", "AdminReport");
    }

}

