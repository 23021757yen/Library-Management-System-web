package com.example.my_group_project;

import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomeScene2Controller extends AdminHomeScene {
    @FXML
    void homeScene1OnClicked() throws IOException {
        super.changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void homeScene3OnClicked() throws IOException {
        super.changeScene("AdminHomeScene3.fxml", "AdminHomeScene3");
    }

    // 1 ham chuyen scene chuyen sang bookinformation( 2 kieu : an nut cuoi ben phai// dup chuot)
}
