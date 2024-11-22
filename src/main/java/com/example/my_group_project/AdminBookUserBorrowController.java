package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminBookUserBorrowController extends AdminMenuController {

    @FXML
    private Label authorNameLabel;

    @FXML
    private Label bookIdLabel;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookNameLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private DatePicker dateBorrow;

    @FXML
    private DatePicker dateReturn;

    @FXML
    private TextField noteTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Label statusLabel;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userFullNameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label userPhoneNumberLabel;

    private boolean isEditable = false;
    private Book currentBook;
    private User currentUser;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setBorrowedBookData(BorrowedBook borrowedBook) {
        loadBookInformation(borrowedBook.getId());
        loadUserInformation(borrowedBook.getUserId());

        dateBorrow.setValue(LocalDateTime.parse(borrowedBook.getDateBorrow(), dateTimeFormatter).toLocalDate());
        dateReturn.setValue(LocalDateTime.parse(borrowedBook.getDateBack(), dateTimeFormatter).toLocalDate());
        statusLabel.setText(borrowedBook.getStatus());

        // Ensure edit mode is set to false initially
        setEditMode(false);
    }

    private void loadBookInformation(String bookId) {
        String sql = "SELECT * FROM books WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    currentBook = new Book(
                            rs.getString("book_ID"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("image"),
                            rs.getInt("viewCount"),
                            rs.getString("kind"),
                            rs.getString("description"),
                            rs.getTimestamp("addDate").toLocalDateTime()
                    );
                    displayBookInformation();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUserInformation(String userId) {
        String sql = "SELECT * FROM user WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    currentUser = new User(
                            rs.getString("User_ID"),
                            rs.getString("name"),
                            rs.getString("fullname"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("dateOfBirth")
                    );
                    displayUserInformation();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayBookInformation() {
        if (currentBook != null) {
            bookIdLabel.setText(currentBook.getId());
            bookNameLabel.setText(currentBook.getTitle());
            authorNameLabel.setText(currentBook.getAuthors());
            categoryLabel.setText(currentBook.getGenre());
            if (currentBook.getImageUrl() != null) {
                bookImage.setImage(new Image(currentBook.getImageUrl()));
            }
        }
    }

    private void displayUserInformation() {
        if (currentUser != null) {
            userIdLabel.setText(currentUser.getId());
            userNameLabel.setText(currentUser.getUsername());
            userFullNameLabel.setText(currentUser.getFullname());
            userEmailLabel.setText(currentUser.getEmail());
            userPhoneNumberLabel.setText(currentUser.getPhone());
        }
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
        // Toggle edit mode
        isEditable = !isEditable;
        setEditMode(isEditable);
    }

    private void setEditMode(boolean editable) {
        dateBorrow.setDisable(!editable);
        dateReturn.setDisable(!editable);
        noteTextField.setDisable(!editable);
        saveButton.setDisable(!editable);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if (isEditable) {
            // Save the updated information
            LocalDate borrowDate = dateBorrow.getValue();
            LocalDate returnDate = dateReturn.getValue();
            String note = noteTextField.getText();

            try {
                // Assuming you have a method to update the borrow information
                updateBorrowInformation(borrowDate, returnDate);
                statusLabel.setText("Information saved successfully!");
                isEditable = false;
                setEditMode(false);
            } catch (SQLException e) {
                e.printStackTrace();
                statusLabel.setText("Failed to save information. Try again.");
            }
        }
    }

    private void updateBorrowInformation(LocalDate borrowDate, LocalDate returnDate) throws SQLException {
        String sql = "UPDATE borrow SET borrowDate = ?, backDate = ? WHERE book_ID = ? AND user_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(borrowDate));
            pstmt.setDate(2, java.sql.Date.valueOf(returnDate));
            pstmt.setString(3, currentBook.getId());
            pstmt.setString(4, currentUser.getId());
            pstmt.executeUpdate();
        }
    }
}