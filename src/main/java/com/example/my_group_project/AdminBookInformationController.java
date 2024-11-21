package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminBookInformationController extends AdminMenuController {

    @FXML
    private Label nameAuthorText;

    @FXML
    private Label limitBookText;

    @FXML
    private Label categoryBookText;

    @FXML
    private Label goalBookText;

    @FXML
    private Text content;

    //intialise the book have clicked befroe
    @FXML
    public void initialize() {
        // Example: Load book information with ID 1
        loadBookInformation(1);
    }

    public void loadBookInformation(int bookId) {
        String query = "SELECT * FROM books WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nameAuthorText.setText(rs.getString("author"));
                limitBookText.setText(String.valueOf(rs.getInt("amount")));
                categoryBookText.setText(rs.getString("kind"));
                goalBookText.setText(rs.getString("goal"));
                content.setText(rs.getString("description"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading book information: " + e.getMessage());
        }
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
        // Allow editing of the text fields, if necessary
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        String query = "UPDATE books SET author = ?, amount = ?, kind = ?, goal = ?, description = ? WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nameAuthorText.getText());
            pstmt.setInt(2, Integer.parseInt(limitBookText.getText()));
            pstmt.setString(3, categoryBookText.getText());
            pstmt.setString(4, goalBookText.getText());
            pstmt.setString(5, content.getText());
            pstmt.setInt(6, 1); // Replace with the actual book ID
            pstmt.executeUpdate();
            System.out.println("Book information updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving book information: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        }
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        String query = "DELETE FROM books WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, 1); // Replace with the actual book ID
            pstmt.executeUpdate();

            // Clear text fields after deletion
            nameAuthorText.setText("");
            limitBookText.setText("");
            categoryBookText.setText("");
            goalBookText.setText("");
            content.setText("");

            System.out.println("Book deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        }
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        // Implement search functionality, if needed
    }

    @FXML
    void homeScene1ButtonOnAction(ActionEvent event) {
        changeScene("AdminHomeScene1.fxml", "AdminHomeScene1");
    }
}

// load thong tin cua sach
    // xoa sach khoi csdl
    // chinh sua thong tin sach
    // luu thong tin sach
    // load cao pane o muc ben duoi giup ntny
