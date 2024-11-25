package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AdminLogInController extends BaseController {
    @FXML
    private TextField passwordTextField;

    @FXML
    void adminLogInButtonOnAction(ActionEvent event) {
        if (passwordTextField.getText().equals("amen")) {
            changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
            SoundManager.playSound("src/main/resources/soundEffects/SEFE_CartoonAccent.wav");
        } else {
            showAlert("Error", "Sai roi nguoi anh chi em. Thu lai de");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        SoundManager.playSound("src/main/resources/soundEffects/SEFE_Conspiracy Theory.wav");
        alert.showAndWait();
    }
}
