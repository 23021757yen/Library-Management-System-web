package com.example.my_group_project;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {
    @FXML
    private Label loginMessageLabel;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private Button userButton;

    @FXML
    private Button adminButton;

    @FXML
    private Button checkLoginButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField enterPasswordField;

    @FXML
    void adminButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) adminButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("loginAdmin.fxml"));
        stage.setTitle("Admin Login");
        stage.setScene(new Scene(root));
    }

    @FXML
    void loginButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("logInUser.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
    }

    @FXML
    void signupButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("signUpUser.fxml"));
        stage.setTitle("Sign up");
        stage.setScene(new Scene(root));
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
        loginButton.setVisible(true);
        signupButton.setVisible(true);
        rectangle.setVisible(true);

    }

    public void checkLoginButtonOnAction(ActionEvent event) {
        if ((usernameTextField.getText().isBlank() == false) && (enterPasswordField.getText().isBlank() == false)) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter your username and password");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            loginMessageLabel.setText("Failed to connect to the database");
            System.out.println("Connection is null");
            return;
        }

        String verifyLogin = "select count(1) from user_account where username = '" + usernameTextField.getText() +
                "' and password = '" + enterPasswordField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 0) {
                    loginMessageLabel.setText("Invalid username or password");
                } else {
                    loginMessageLabel.setText("Login successful");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
