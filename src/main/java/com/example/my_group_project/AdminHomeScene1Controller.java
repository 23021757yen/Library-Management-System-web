package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminHomeScene1Controller extends BaseController {

    @FXML
    private Button addBookButton;

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private HBox contentHbox;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Button logOutButton;

    @FXML
    private Pane paneHomeScene1;

    @FXML
    private Pane paneHomeScene2;

    @FXML
    private Pane paneHomeScene3;

    @FXML
    private Text quantity1Text;

    @FXML
    private Text quantity2Text;

    @FXML
    private Text quantity3Text;

    @FXML
    private Button reportButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button userManagementButton;

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


    @FXML
    void homeScene2PaneOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminHomeScene2.fxml", "AdminHomeScene2");
    }

    @FXML
    void homeScene3PaneOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminHomeScene3.fxml", "AdminHomeScene3");
    }

    @FXML
    void addButtonOnAction(ActionEvent event) throws IOException {

    }

   // 1 ham cho tin kiem : ra thong tin
    // 1 ham de load cac pane ( da tao rieng 1 file fxml :))))
    // 1 ham chuyen scene chuyen sang bookinformation( 2 kieu : an nut cuoi ben phai// dup chuot)
}
