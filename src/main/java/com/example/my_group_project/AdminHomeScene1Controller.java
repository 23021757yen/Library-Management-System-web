package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminHomeScene1Controller extends AdminMenuController {

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
    private ScrollPane scrollpane;

    @FXML
    private VBox vBox;

    @FXML
    void homeScene2OnClicked() throws IOException {
        super.changeScene("AdminHomeScene2.fxml", "AdminHomeScene2");
    }

    // Chuyển cảnh khi nhấn vào Pane 3
    @FXML
    void homeScene3OnClicked() throws IOException {
        super.changeScene("AdminHomeScene3.fxml", "AdminHomeScene3");
    }

    @FXML
    void addButtonOnAction(ActionEvent event) throws IOException {

    }
    // 1 hàm add sách, cũng là add thêm pane thoi(sau khiấn vào thì trực tiếp vào trạng thái edit để điền thông tin sách)
   // 1 ham cho tin kiem : ra thong tin
    // 1 ham de load cac pane ( da tao rieng 1 file fxml :))))
    // 1 ham chuyen scene chuyen sang bookinformation( 2 kieu : an nut cuoi ben phai// dup chuot)
}
