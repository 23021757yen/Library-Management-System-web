package com.example.my_group_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class HelloController extends BaseController {
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button loginButton, signupButton;
    @FXML
    private ComboBox usernameComboBox;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private CheckBox rememberMeCheckBox;

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
    void backButtonOnAction(ActionEvent event) {
        super.changeScene("welcomeToWebsite.fxml", "Nhóm 3 con gián");
    }

    @FXML
    private void initialize() {
        // Ensure usernameComboBox is not null before loading details
        if (usernameComboBox != null) {
            loadLoginDetails();
        } else {
            System.out.println("usernameComboBox is null during initialization");
        }
    }

    @FXML
    private void rememberMeCheckBoxOnAction(ActionEvent event) {
        if (rememberMeCheckBox.isSelected()) {
            String username = usernameComboBox.getEditor().getText();
            String password = enterPasswordField.getText();
            saveLoginDetails(username, password);
        }
    }

    @FXML
    void checkLoginButtonOnAction(ActionEvent event) {
        String username = usernameComboBox.getEditor().getText();
        String password = enterPasswordField.getText();
        Connection connection = DatabaseConnection.getConnection();
        if (!username.isBlank() && !password.isBlank()) {
            User loggedUser = new User(getUserId(connection,username,password), username);
            User.setCurrentUser(loggedUser);
            validateLogin(username, password);
        } else {
            loginMessageLabel.setText("Please enter your username and password");
        }
    }

    public void validateLogin(String username, String password) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        if (connectDB == null) {
            loginMessageLabel.setText("Failed to connect to the database");
            System.out.println("Connection is null");
        } else {
            String verifyLogin = "SELECT COUNT(1) FROM user WHERE name = ? AND password = ?";
            try (PreparedStatement statement = connectDB.prepareStatement(verifyLogin)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet queryResult = statement.executeQuery()) {
                    if (queryResult.next() && queryResult.getInt(1) == 0) {
                        loginMessageLabel.setText("Invalid username or password");
                    } else {
                        loginMessageLabel.setText("Login successful");
                        userId = getUserId(connectDB, username, password);
                        System.out.println("User ID after login: " + userId);
                        userIdMain = userId;

                        if (rememberMeCheckBox.isSelected()) {
                            saveLoginDetails(username, password);
                        }
                        toHomeScene();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void toHomeScene() {
        super.changeScene("home.fxml", "Home");
    }

    public String getUserId(Connection connectDB, String username, String password) {
        String query = "SELECT User_ID FROM user WHERE name = ? AND password = ?";
        try (PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("User_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveLoginDetails(String username, String password) {
        Preferences prefs = Preferences.userNodeForPackage(HelloController.class);
        List<String> usernames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();

        try {
            for (String key : prefs.keys()) {
                if (key.startsWith("username")) {
                    usernames.add(prefs.get(key, ""));
                } else if (key.startsWith("password")) {
                    passwords.add(prefs.get(key, ""));
                }
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        if (!usernames.contains(username)) {
            usernames.add(username);
            passwords.add(password);

            for (int i = 0; i < usernames.size(); i++) {
                prefs.put("username" + i, usernames.get(i));
                prefs.put("password" + i, passwords.get(i));
            }
        }
    }

    private void loadLoginDetails() {
        Preferences prefs = Preferences.userNodeForPackage(HelloController.class);
        List<String> usernames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();

        try {
            for (String key : prefs.keys()) {
                if (key.startsWith("username")) {
                    String username = prefs.get(key, null);
                    if (username != null && !usernames.contains(username)) {
                        usernames.add(username);
                    }
                } else if (key.startsWith("password")) {
                    String password = prefs.get(key, null);
                    if (password != null && passwords.size() < usernames.size()) {
                        passwords.add(password);
                    }
                }
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        ObservableList<String> usernameList = FXCollections.observableArrayList(usernames);
        System.out.println("Loaded usernames: " + usernames);
        System.out.println("Loaded passwords: " + passwords);

        if (usernameComboBox != null) {
            usernameComboBox.setItems(usernameList);
            System.out.println("usernameComboBox initialized");

            usernameComboBox.setOnAction(event -> {
                int index = usernameComboBox.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < passwords.size()) {
                    enterPasswordField.setText(passwords.get(index));
                }
            });

            // Allow typing new usernames
            usernameComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                enterPasswordField.clear();
            });
        } else {
            System.out.println("usernameComboBox is null");
        }
    }
}

