package com.example.my_group_project;

import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomeScene1Controller extends AdminHomeScene{

    @FXML
    void homeScene2OnClicked() throws IOException {
        super.changeScene("AdminHomeScene2.fxml", "AdminHomeScene2");
    }

    @FXML
    void homeScene3OnClicked() throws IOException {
        super.changeScene("AdminHomeScene3.fxml", "AdminHomeScene3");
    }
}