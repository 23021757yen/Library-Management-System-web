package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController extends BaseController {
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button loginButton, signupButton, userButton, adminButton, checkLoginButton, backButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;

    @FXML
    void adminButtonOnAction(ActionEvent event) {
        super.changeScene("logInAdmin.fxml", "Admin Login");
    }

    @FXML
    void loginButtonOnAction(ActionEvent event) {
        super.changeScene("logInUser.fxml", "Login");
    }

    @FXML
    void signupButtonOnAction(ActionEvent event) {
        super.changeScene("signUpUser.fxml", "Sign up");
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
        loginButton.setVisible(true);
        signupButton.setVisible(true);
        rectangle.setVisible(true);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {super.changeScene("welcomeToWebsite.fxml", "Nhóm 3 con gián");
    }

    @FXML
    public void checkLoginButtonOnAction(ActionEvent event) {
        if (!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
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
        } else {
            String verifyLogin = "SELECT COUNT(1) FROM user WHERE name = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() + "'";
            try (Statement statement = connectDB.createStatement();
                 ResultSet queryResult = statement.executeQuery(verifyLogin)) {
                if (queryResult.next() && queryResult.getInt(1) == 0) {
                    loginMessageLabel.setText("Invalid username or password");
                } else {
                    loginMessageLabel.setText("Login successful");
                    toMainScene();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void toMainScene() {
        super.changeScene("home.fxml", "Home");
    }
}