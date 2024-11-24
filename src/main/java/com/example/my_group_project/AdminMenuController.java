package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.w3c.dom.Text;

import java.io.IOException;

public abstract class AdminMenuController extends BaseController{
    @FXML
    void homeScene1ButtonOnAction(ActionEvent event) {
        super.changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void bookBorrowButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminBorrowBook.fxml", "AdminBorrowBook");
    }

    @FXML
    void userManagementButtonOnAction(ActionEvent event) {
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
}
