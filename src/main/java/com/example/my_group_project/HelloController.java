package com.example.my_group_project ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.sql.Statement;
import javafx.scene.shape.Rectangle;



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

    private String userId;

    public static String userIdMain;

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
                    userId = getUserId(connectDB);  // Gọi phương thức getUserId để lấy User_ID
                    System.out.println("User ID after login: " + userId);
                    userIdMain = userId;
                    toMainScene();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserId(Connection connectDB) {
        String query = "SELECT User_ID FROM user WHERE name = ? AND password = ?";
        try (PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setString(1, usernameTextField.getText());
            statement.setString(2, enterPasswordField.getText());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getString("User_ID");
                System.out.print("acb");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("acbd");
        }
        return userId;
    }

    public void toMainScene() {
        super.changeScene("home.fxml", "Home");
    }
}

