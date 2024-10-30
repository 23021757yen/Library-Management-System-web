package com.example.my_group_project;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;  // Thêm import cho URL
import java.util.ResourceBundle;

public class BookProfileController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button borrowButton;

    @FXML
    private Button categoryComedyButton;

    @FXML
    private Text content;

    @FXML
    private Button highlightButton;

    @FXML
    private Button homeButton;

    @FXML
    private Text limitAge;

    @FXML
    private Button categoryButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Text nameOfAuthor;

    @FXML
    private Text nameOfBook;

    @FXML
    private Text numberOfBorrow;

    @FXML
    private Text numberOfRead;

    @FXML
    private Text pageViews;

    @FXML
    private Button profileButton;

    @FXML
    private Button readButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button settingButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Thực hiện các thao tác khởi tạo tại đây, ví dụ:
        // Thiết lập nội dung cho các Text
        nameOfBook.setText("Tên sách");
        nameOfAuthor.setText("Tác giả");
        numberOfRead.setText("0");
        numberOfBorrow.setText("0");
        pageViews.setText("0");
        content.setText("Nội dung sách");
        limitAge.setText("Dành cho 18+");
    }

    @FXML
    void categoryButtonOnAction(ActionEvent event) throws IOException {
        loadScene("categoryMenu.fxml", "Category");
    }

    @FXML
    void searchButtonOnAction(ActionEvent event) throws IOException {
        loadScene("searchBook.fxml", "Searching");
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) throws IOException {
        loadScene("home.fxml", "Home");
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) throws IOException {
        loadScene("profileUser.fxml", "ProfileUser");
    }

    @FXML
    void moreInforButtonOnAction(ActionEvent event) throws IOException {
        loadScene("moreInfor.fxml", "MoreInfor");
    }

    @FXML
    void settingButtonOnAction(ActionEvent event) throws IOException {
        loadScene("settingUser.fxml", "Setting");
    }

    @FXML
    void backButtonOnAction(ActionEvent event) throws IOException {
        loadScene("home.fxml", "Home");
    }

    void borrowButtonOnAction(ActionEvent event) {

    }

    private void loadScene(String fxml, String title) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage.setTitle(title);
        stage.setScene(new Scene(root));
    }
}
