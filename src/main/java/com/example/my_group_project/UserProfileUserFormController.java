package com.example.my_group_project ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserProfileUserFormController extends BaseController {
    @FXML
    private MenuItem homeMenuItem;
    @FXML
    private Label name;
    @FXML
    private MenuItem moreInforMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private MenuItem searchMenuItem;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private Button backButton;

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private Button editButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button uploadButton;

    @FXML
    private ImageView imageProfile;

    private String userId ;

    private boolean isEdited = false;

    private File selectedImageFile;

    @FXML
    public void initialize() {
        this.userId =  HelloController.userIdMain ;
        genderBox.getItems().addAll("Male", "Female", "Other");
        if(userId != null && !userId.isEmpty()) {
            loadUserProfile(userId);
        }
        setEditable(false);
    }

    @FXML
    void onEdit() {
        isEdited= true;
        setEditable(isEdited);
        saveButton.setDisable(!isEdited);
    }
    @FXML
    void setEditable(boolean edit) {
        fullnameField.setEditable(edit);
        usernameField.setEditable(edit);
        phoneNumberField.setEditable(edit);
        emailField.setEditable(edit);
        dateOfBirthPicker.setEditable(edit);
        genderBox.setDisable(!edit);
    }

    @FXML
    private void loadUserProfile(String userId) {
        String query = "SELECT * FROM user WHERE User_ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                fullnameField.setText(resultSet.getString("fullname"));
                usernameField.setText(resultSet.getString("name"));
                phoneNumberField.setText(resultSet.getString("phone"));
                emailField.setText(resultSet.getString("email"));
                dateOfBirthPicker.setValue(resultSet.getDate("dateOfBirth").toLocalDate());
                String gender = resultSet.getString("gender");
                name.setText(resultSet.getString("name"));

                if (gender != null) {
                    genderBox.setValue(gender);
                }

                if (resultSet.getBlob("profile_image") != null) {
                    System.out.println("No user found with UserId///: " + userId);
                    byte[] imageBytes = resultSet.getBytes("profile_image");
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    imageProfile.setImage(image);
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
        String query = "UPDATE user SET fullname = ?, name = ?, phone = ?, email = ?, dateOfBirth = ?, gender = ?, profile_image = ? WHERE User_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, fullnameField.getText());
            preparedStatement.setString(2, usernameField.getText());
            preparedStatement.setString(3, phoneNumberField.getText());
            preparedStatement.setString(4, emailField.getText());
            preparedStatement.setDate(5, java.sql.Date.valueOf(dateOfBirthPicker.getValue()));
            preparedStatement.setString(6, genderBox.getValue());
            System.out.print("3sdnajsncdc");
            if (selectedImageFile != null) {
                try (FileInputStream imageStream = new FileInputStream(selectedImageFile)) {
                    System.out.print("2sdnajsncdc");
                    byte[] imageBytes = Files.readAllBytes(selectedImageFile.toPath());
                    preparedStatement.setBinaryStream(7, new ByteArrayInputStream(imageBytes), imageBytes.length);
                    System.out.println("Selected file: " + selectedImageFile.getAbsolutePath());
                    System.out.print("5sdnajsncdc");
                } catch (IOException e) {
                    System.out.print("1sdnajsncdc");
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while reading the image.");
                    return;
                }
            } else {
                System.out.print("sdnajsncdc");
                preparedStatement.setNull(7, java.sql.Types.BLOB);
            }
            preparedStatement.setString(8, userId);

            preparedStatement.executeUpdate();
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully!");
            } else {
                System.out.println("Failed to update profile.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSave() {
        String fullname = fullnameField.getText();
        String username = usernameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String gender = genderBox.getValue();
        String dateOfBirth = null;

        if (dateOfBirthPicker.getValue() != null) {
            dateOfBirth = dateOfBirthPicker.getValue().toString();
        }
        if (phoneNumber.length() < 10 || !phoneNumber.matches("\\d+")) {
            showAlert("Lỗi", "PhoneNumber không hợp lệ!");
        }
        else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Lỗi", "Email không hợp lệ!");
        }
        else if(fullname.isEmpty() || username.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || gender == null || dateOfBirth == null) {
            showAlert("Loi", "Vui long dien day du thong tin");
        }
        else {
            if(!userId.isEmpty() && userId != null ) {
                updateUserProfile(userId);
            }
        }
    }

    @FXML
    void onUploadPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageProfile.setImage(image);
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
    void backButtonOnAction (ActionEvent event) throws IOException {
        super.changeScene("home.fxml", "Home");
    }
    @FXML
    void homeMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("home.fxml", "Home");
    }
    @FXML
    void searchMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("searchBook.fxml", "Searching");
    }
    @FXML
    void logOutMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("welcomeToWebsite.fxml", "Hello View");
    }
    @FXML
    void moreInforMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("moreInformation.fxml", "MoreInfor");
    }
    @FXML
    void profileMenuItemOnAction(ActionEvent event) throws IOException {
        MenuItem clickedItem = (MenuItem) event.getSource();
        String selectedItem = clickedItem.getText();
        super.changeScene("profileUser.fxml", "ProfileUser");
    }

}
