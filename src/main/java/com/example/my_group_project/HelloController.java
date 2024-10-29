//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.my_group_project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
    private Button backButton;

    public HelloController() {
    }

    @FXML
    void adminButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)this.adminButton.getScene().getWindow();
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("logInAdmin.fxml"));
        stage.setTitle("Admin Login");
        stage.setScene(new Scene(root));
    }

    @FXML
    void loginButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)this.loginButton.getScene().getWindow();
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("logInUser.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
    }

    @FXML
    void signupButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)this.signupButton.getScene().getWindow();
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("signUpUser.fxml"));
        stage.setTitle("Sign up");
        stage.setScene(new Scene(root));
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
        this.loginButton.setVisible(true);
        this.signupButton.setVisible(true);
        this.rectangle.setVisible(true);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("welcomeToWebsite.fxml"));
        stage.setTitle("Nhóm 3 con gián");
        stage.setScene(new Scene(root));
    }

    @FXML
    public void checkLoginButtonOnAction(ActionEvent event) throws IOException {
        if (!this.usernameTextField.getText().isBlank() && !this.enterPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            if(!this.usernameTextField.getText().isBlank() && this.enterPasswordField.getText().isBlank()) {
                this.loginMessageLabel.setText("Please enter your password!");
            } else if (this.usernameTextField.getText().isBlank() && !this.enterPasswordField.getText().isBlank()) {
                this.loginMessageLabel.setText("Please enter your username!");
            } else {
                this.loginMessageLabel.setText("Please enter your username and password!");
            }
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        if (connectDB == null) {
            this.loginMessageLabel.setText("Failed to connect to the database");
            System.out.println("Connection is null");
        } else {
            String var10000 = this.usernameTextField.getText();
            String verifyLogin = "select count(1) from user where name = '" + var10000 + "' and password = '" + this.enterPasswordField.getText() + "'";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while(queryResult.next()) {
                    if (queryResult.getInt(1) == 0) {
                        this.loginMessageLabel.setText("Invalid username or password");
                    } else {
                        this.loginMessageLabel.setText("Login successful");
                        toMainScene();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    public void toMainScene() throws IOException {
        Stage stage = (Stage) checkLoginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(root));
    }



}
