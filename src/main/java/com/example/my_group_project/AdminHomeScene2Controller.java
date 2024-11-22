package com.example.my_group_project;

import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomeScene2Controller extends AdminHomeScene {
    @FXML
    void homeScene1OnClicked() throws IOException {
        super.changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    // Chuyển cảnh khi nhấn vào Pane 3
    @FXML
    void homeScene3OnClicked() throws IOException {
        super.changeScene("AdminHomeScene3.fxml", "AdminHomeScene3");
    }

    // 1 hàm add sách, cũng là add thêm pane thoi(sau khiấn vào thì trực tiếp vào trạng thái edit để điền thông tin sách)
    // 1 ham cho tin kiem : ra thong tin
    // 1 ham de load cac pane ( da tao rieng 1 file fxml :))))
    // 1 ham chuyen scene chuyen sang bookinformation( 2 kieu : an nut cuoi ben phai// dup chuot)
}
