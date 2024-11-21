package com.example.my_group_project ;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class AdminUserManagementPaneController extends BaseController {

    @FXML
    private Label dateOfBirth;

    @FXML
    private Label email;

    @FXML
    private Label gender;

    @FXML
    private HBox hbox;

    @FXML
    private Button moreButton;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label userId;

    @FXML
    private Label userName;

    public void set userDetails ( User user) {
        userId.setText(user.getId());
        userName.setText(user.getUserName());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhone());
        dateOfBirth.setText(user.getDateOfBirth());
        gender.setText(user.getGender());
    }

}
