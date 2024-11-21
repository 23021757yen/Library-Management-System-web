package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminReportOneUserController extends BaseController{

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Text contentText;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField noteTextField;

    @FXML
    private Button reportButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private CheckBox statusCheckBox;

    @FXML
    private Label userEmailTextField;

    @FXML
    private Label userFullNameTextField;

    @FXML
    private Label userIdTextField;

    @FXML
    private Button userManagementButton;

    @FXML
    private Label userNameTextField;

    @FXML
    private Label userPhoneNumberTextField;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private VBox vBox;

    @FXML
    private ImageView userImage;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.backButtonOnAction(event);
    }

    @FXML
    void homeScene1ButtononOnAction(ActionEvent event) {
        changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void bookBorrowButtonOnAction(ActionEvent event) {
        changeScene("AdminBorrowBook.fxml", "AdminBorrowBook");
    }

    @FXML
    void reportButtonOnAction(ActionEvent event) {
        changeScene("AdminReport.fxml", "AdminReport");
    }

    @FXML
    void userManagementButtonOnAction(ActionEvent event) {
        changeScene("AdminUserManagement.fxml", "AdminUserManagement");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        changeScene("welcomToWebsite.fxml", "welcomeToWebsite");
    }

    //load thong tin dung theo report ( lay tu email tai trang MoreInformation cua nguoi dung)
    //tick trang thai sau khi su ly thong tin
    //note ghi chu

}
