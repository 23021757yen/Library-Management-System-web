package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminOneUserProfileController extends BaseController {

    @FXML private Button backButton;
    @FXML private Button bookBorrowButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private Button homeScene1Button;
    @FXML private Button logOutButton;
    @FXML private Button reportButton;
    @FXML private Button saveButton;
    @FXML private TextField searchTextField;
    @FXML private DatePicker userDatePicker;
    @FXML private TextField userEmail;
    @FXML private TextField userFullNameTextField;
    @FXML private TextField userIdTextField;
    @FXML private ImageView userImage;
    @FXML private Button userManagementButton;
    @FXML private TextField userNameTextField;
    @FXML private TextField userPhoneNumber;
    @FXML private TextField passwordTextField;

    private String userId;
    private boolean isEdited = false;
    private File selectedImageFile;

    @FXML
    public void initialize() {
        if (userId != null && !userId.isEmpty()) {
            loadUserProfile(userId);
        }
        setEditable(false);
    }

    @FXML
    void onEdit() {
        isEdited = true;
        setEditable(isEdited);
        saveButton.setDisable(!isEdited);
    }

    @FXML
    void setEditable(boolean edit) {
        userIdTextField.setEditable(edit);
        userFullNameTextField.setEditable(edit);
        userNameTextField.setEditable(edit);
        userPhoneNumber.setEditable(edit);
        userEmail.setEditable(edit);
        userDatePicker.setEditable(edit);
        genderComboBox.setDisable(!edit);
        passwordTextField.setEditable(edit);
    }

    @FXML
    public void loadUserProfile(String userId) {
        String query = "SELECT * FROM user WHERE User_ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userIdTextField.setText(resultSet.getString("User_ID"));
                userFullNameTextField.setText(resultSet.getString("fullname"));
                userNameTextField.setText(resultSet.getString("name"));
                userPhoneNumber.setText(resultSet.getString("phone"));
                userEmail.setText(resultSet.getString("email"));
                userDatePicker.setValue(resultSet.getDate("dateOfBirth").toLocalDate());
                passwordTextField.setText(resultSet.getString("password"));
                String gender = resultSet.getString("gender");
                if (gender != null) {
                    genderComboBox.setValue(gender);
                }

                if (resultSet.getBlob("profile_image") != null) {
                    byte[] imageBytes = resultSet.getBytes("profile_image");
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    userImage.setImage(image);
                }
            } else {
                System.out.println("No user found with UserId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateUserProfile(String userId) {
        String query = "UPDATE user SET User_ID = ?, fullname = ?, name = ?, phone = ?, email = ?, dateOfBirth = ?, gender = ?, profile_image = ?, password = ? WHERE User_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userIdTextField.getText());
            preparedStatement.setString(2, userFullNameTextField.getText());
            preparedStatement.setString(3, userNameTextField.getText());
            preparedStatement.setString(4, userPhoneNumber.getText());
            preparedStatement.setString(5, userEmail.getText());
            preparedStatement.setDate(6, java.sql.Date.valueOf(userDatePicker.getValue()));
            preparedStatement.setString(7, genderComboBox.getValue());

            if (selectedImageFile != null) {
                try (FileInputStream imageStream = new FileInputStream(selectedImageFile)) {
                    byte[] imageBytes = Files.readAllBytes(selectedImageFile.toPath());
                    preparedStatement.setBinaryStream(8, new ByteArrayInputStream(imageBytes), imageBytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while reading the image.");
                    return;
                }
            } else {
                preparedStatement.setNull(8, java.sql.Types.BLOB);
            }
            preparedStatement.setString(9, passwordTextField.getText());
            preparedStatement.setString(10, userId);

            preparedStatement.executeUpdate();
            System.out.println("Profile updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSave() {
        String userID = userIdTextField.getText();
        String fullname = userFullNameTextField.getText();
        String username = userNameTextField.getText();
        String phoneNumber = userPhoneNumber.getText();
        String email = userEmail.getText();
        String gender = genderComboBox.getValue();
        String dateOfBirth = null;
        String password = passwordTextField.getText();
        userId = userID;

        if (userDatePicker.getValue() != null) {
            dateOfBirth = userDatePicker.getValue().toString();
        }
        if (phoneNumber.length() < 10 || !phoneNumber.matches("\\d+")) {
            showAlert("Lỗi", "PhoneNumber không hợp lệ!");
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Lỗi", "Email không hợp lệ!");
        } else if (fullname.isEmpty() || username.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || gender == null || dateOfBirth == null) {
            showAlert("Loi", "Vui lòng điền đầy đủ thông tin");
        } else {
            if (!userID.isEmpty() && userID != null) {
                updateUserProfile(userID);
            }
        }
        setEditable(false);
    }

    @FXML
    void onUploadPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            userImage.setImage(image);
        }
    }

    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.backButtonOnAction(event);
    }

    @FXML
    void homeScene1ButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void bookBorrowButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminBorrowBook.fxml", "AdminBorrowBook");
    }

    @FXML
    void userManagementButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminUserManagement.fxml", "AdminUserManagement");
    }

    @FXML
    void reportButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("AdminReport.fxml", "AdminReport");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) throws IOException {
        super.changeScene("welcomeToWebsite.fxml", "welcomeToWebsite");
    }
}
