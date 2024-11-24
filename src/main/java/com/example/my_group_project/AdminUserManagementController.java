package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminUserManagementController extends AdminMenuController {

    @FXML
    private VBox vBox;

    @FXML
    private TextField searchTextField;

    @FXML
    public void initialize() {
        displayUser();

        searchTextField.setOnKeyReleased(event -> filterSearch());
    }

    @FXML
    protected static List<User> getUserFromDatabase() {
        List<User> getUserFromDatabase = new ArrayList<>();
        String sql = "SELECT User_ID, name, email, phone, dateOfBirth, gender FROM user;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String userId = rs.getString("User_ID");
                    String userName = rs.getString("name");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phone");
                    String dateOfBirth = rs.getString("dateOfBirth");
                    String gender = rs.getString("gender");
                    User user = new User(userId, userName, email, phoneNumber, dateOfBirth, gender);
                    getUserFromDatabase.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getUserFromDatabase;
    }

    private void displayUser() {
        List<User> userList = getUserFromDatabase();
        int index = 0;
        if (userList.isEmpty()) {
            vBox.getChildren().add(new Label("No user."));
            return;
        } else {
            for (User user : userList) {
                if (user == null) {
                    continue;
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminUserManagementPane.fxml"));
                    HBox userHBox = loader.load();
                    AdminUserManagementPaneController userManagementPane = loader.getController();
                    userManagementPane.setUserDetail(user);
                    final int currentIndex = index;
                    if (currentIndex % 2 == 0) {
                        userHBox.setStyle("-fx-background-color: #f7efd8;");
                    } else {
                        userHBox.setStyle("-fx-background-color: #ffffff;");
                    }

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(userHBox);

                    stackPane.setOnMouseEntered(event -> {
                        userHBox.setStyle("-fx-background-color: #ffc100; -fx-cursor: hand;");
                    });

                    stackPane.setOnMouseExited(event -> {
                        if (currentIndex % 2 == 0) {
                            userHBox.setStyle("-fx-background-color: #f7efd8;");
                        } else {
                            userHBox.setStyle("-fx-background-color: #ffffff;");
                        }
                    });
                    userHBox.setOnMouseClicked(event -> {
                        showUser(userManagementPane.getUserId());
                    });
                    vBox.getChildren().add(stackPane);
                    index++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showUser(String userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminOneUserProfile.fxml"));
            Pane userProfilePane = loader.load();

            AdminOneUserProfileController userProfileController = loader.getController();

            userProfileController.loadUserProfile(userId);
            userProfileController.displayUserBorrow(userId);

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.setScene(new Scene(userProfilePane));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {
        String newUserId = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminOneUserProfile.fxml"));
        try {
            Pane userProfilePane = loader.load();
            AdminOneUserProfileController controller = loader.getController();
            controller.setEditable(true);

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.setScene(new Scene(userProfilePane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        //code here
    }

    private void filterSearch() {
        String search = searchTextField.getText();

        List<User> filterUser = new ArrayList<>();
        for (User user : getUserFromDatabase()) {
            if (user.getId() != null && user.getId().contains(search) ||
                    user.getUsername() != null && user.getUsername().contains(search) ||
                    user.getEmail() != null && user.getEmail().contains(search) ||
                    user.getPhone() != null && user.getPhone().contains(search) ||
                    user.getDateOfBirth() != null && user.getDateOfBirth().contains(search) ||
                    user.getGender() != null && user.getGender().contains(search)) {
                filterUser.add(user);
            }
        }
        displayFilterSearch(filterUser);
    }

    private void displayFilterSearch(List<User> filterUser) {
        vBox.getChildren().clear();

        if (filterUser.isEmpty()) {
            vBox.getChildren().add(new Label("No user found"));
            return;
        }

        int index = 0;
        for (User user : filterUser) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminUserManagementPane.fxml"));
                HBox userHBox = loader.load();

                AdminUserManagementPaneController userManagementPane = loader.getController();
                userManagementPane.setUserDetail(user);  // Cập nhật chi tiết người dùng
                final int currentIndex = index;
                if (currentIndex % 2 == 0) {
                    userHBox.setStyle("-fx-background-color: #f7efd8;");  // Màu nền cho dòng chẵn
                } else {
                    userHBox.setStyle("-fx-background-color: #ffffff;");  // Màu nền cho dòng lẻ
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(userHBox);

                stackPane.setOnMouseEntered(event -> {
                    userHBox.setStyle("-fx-background-color: #ffc100; -fx-cursor: hand;");
                });

                stackPane.setOnMouseExited(event -> {
                    if (currentIndex % 2 == 0) {
                        userHBox.setStyle("-fx-background-color: #f7efd8;");
                    } else {
                        userHBox.setStyle("-fx-background-color: #ffffff;");
                    }
                });
                userHBox.setOnMouseClicked(event -> {
                    showUser(userManagementPane.getUserId());
                });
                vBox.getChildren().add(stackPane);
                index++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}