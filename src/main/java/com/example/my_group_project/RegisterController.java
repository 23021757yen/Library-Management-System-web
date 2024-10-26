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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private Button backButton;

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

        // Generate random ID using MySQL function
        String randomIDQuery = "SELECT generateRandomID(10) AS randomID";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(randomIDQuery);
            resultSet.next();
            String userID = resultSet.getString("randomID");

            String insertQuery = "INSERT INTO user (User_ID, name, password, email, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                registrationMessageLabel.setText("User has been registered successfully!");
                toLogInScene();
            } else {
                registrationMessageLabel.setText("Registration failed. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toLogInScene() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("logInUser.fxml"));
        stage.setTitle("Login Again");
        stage.setScene(new Scene(root));
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("welcomeToWebsite.fxml"));
        stage.setTitle("Hello!");
        stage.setScene(new Scene(root));
    }
}

