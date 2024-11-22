package com.example.my_group_project ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
public class AdminBorrowBookController extends BaseController {

    @FXML
    private Button addBookButton;

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
    private HBox searchTextField;

    @FXML
    private Button userManagementButton;

    @FXML
    private VBox vBox;

    @FXML
    void addButtonOnAction(ActionEvent event) {

    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.backButtonOnAction(event);
    }

    @FXML
    void homeScene1ButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void bookBorrowButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminBorrowBook.fxml", "AdminBorrowBook");
    }

    @FXML
    void userManagementButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminUserManagement.fxml", "AdminUserManagement");
    }

    @FXML
    void reportButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminReport.fxml", "AdminReport");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        super.logOutButtonOnAction(event);
    }


    // load pane san co
    //thuc hien tim kiem nhanh cho thanh search
    // add them sach theo pane( da noi o home scene1 )

}
