package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class AdminLogInController extends BaseController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    void adminLogInButtonOnAction(ActionEvent event) {

        if(passwordTextField.getText().equals("ntnynguqua4*!")) {
            changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
        }
    }
}
// admin chi dang nhap voi 1 mat khau