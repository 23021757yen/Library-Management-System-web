package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;


public class RegisterController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField setPasswordTextField;

    @FXML
    private TextField setEmailTextField;

    @FXML
    private TextField setPhoneTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    void loginButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("logInUser.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
    }

    @FXML
    void registerButtonOnAction(ActionEvent event) {
        boolean isValidEmail = setEmailTextField.getText().contains("@gmail.com");
        boolean isValidPhone = setPhoneTextField.getText().matches("\\d+");

        if (!usernameTextField.getText().isBlank() &&
                !setPasswordTextField.getText().isBlank() &&
                isValidEmail &&
                isValidPhone) {
            registerUser();
        } else {
            registrationMessageLabel.setText("Invalid input! Please check your details.");
        }
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String userName = usernameTextField.getText();
        String password = setPasswordTextField.getText();
        String email = setEmailTextField.getText();
        String phone = setPhoneTextField.getText();

        String insertFields = "INSERT INTO user_account(userName, password, email, phone) VALUES ('";
        String insertValues = userName + "', '" + password + "', '" + email + "', '" + phone + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
