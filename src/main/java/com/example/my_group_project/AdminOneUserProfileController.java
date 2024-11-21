package com.example.my_group_project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AdminOneUserProfileController extends BaseController {

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private ComboBox<?> genderComboBox;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Button logOutButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField searchTextFileld;

    @FXML
    private DatePicker userDatePicker;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userFullNameTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private ImageView userImage;

    @FXML
    private Button userManagementButton;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField userPhoneNumber;

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
    void logOutButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("welcomeToWebsite.fxml", "welcomeToWebsite");
    }

    // delete nguoi dung
    // edit thong tin
    // save thong tin
    // thuc hien load cac thong tin cua nguoi dung
    // search de loc
}
