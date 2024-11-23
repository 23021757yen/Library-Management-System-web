package com.example.my_group_project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminBookInformationController extends AdminMenuController {

    @FXML
    private TextField nameAuthorText;

    @FXML
    private TextField limitBookText;

    @FXML
    private TextField categoryBookText;

    @FXML
    private TextField goalBookText;

    @FXML
    private TextArea content;

    @FXML
    private TableView<BorrowInfo> borrowTable;

    @FXML
    private TableColumn<BorrowInfo, Integer> accessCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> bookmarkCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> borrowCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> returnCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> waitingCountColumn;

    @FXML
    private TableColumn<BorrowInfo, Integer> lostCountColumn;

    private Book currentBook;

    public void setBookData(Book book) {
        this.currentBook = book;
        loadBookInformation(book.getId());
        loadBorrowInformation(book.getId());
    }

    @FXML
    public void initialize() {
        setFieldsEditable(false);  // Make fields non-editable by default

        // Initialize TableView columns
        accessCountColumn.setCellValueFactory(cellData -> cellData.getValue().accessCountProperty().asObject());
        bookmarkCountColumn.setCellValueFactory(cellData -> cellData.getValue().bookmarkCountProperty().asObject());
        borrowCountColumn.setCellValueFactory(cellData -> cellData.getValue().borrowCountProperty().asObject());
        returnCountColumn.setCellValueFactory(cellData -> cellData.getValue().returnCountProperty().asObject());
        waitingCountColumn.setCellValueFactory(cellData -> cellData.getValue().waitingCountProperty().asObject());
        lostCountColumn.setCellValueFactory(cellData -> cellData.getValue().lostCountProperty().asObject());
    }

    private void setFieldsEditable(boolean editable) {
        nameAuthorText.setEditable(editable);
        limitBookText.setEditable(editable);
        categoryBookText.setEditable(editable);
        goalBookText.setEditable(editable);
        content.setEditable(editable);
    }

    public void loadBookInformation(String bookId) {
        String query = "SELECT * FROM books WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nameAuthorText.setText(rs.getString("author"));
                limitBookText.setText(String.valueOf(rs.getInt("amount")));
                categoryBookText.setText(rs.getString("kind"));
                content.setText(rs.getString("description"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading book information: " + e.getMessage());
        }
    }

    public void loadBorrowInformation(String bookId) {
        // Query to fetch borrow counts grouped by status
        String borrowQuery = "SELECT COUNT(*) AS count, status FROM borrow WHERE book_ID = ? GROUP BY status";
        // Query to fetch view count from the books table
        String viewCountQuery = "SELECT viewCount FROM books WHERE book_ID = ?";

        ObservableList<BorrowInfo> borrowInfoList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Fetch borrow information
            try (PreparedStatement pstmt = conn.prepareStatement(borrowQuery)) {
                pstmt.setString(1, bookId);
                ResultSet rs = pstmt.executeQuery();

                int accessCount = 0;  // This will be updated with the actual viewCount
                int bookmarkCount = 0;  // Replace with actual logic if applicable
                int borrowCount = 0;
                int returnCount = 0;
                int waitingCount = 0;
                int lostCount = 0;

                while (rs.next()) {
                    int count = rs.getInt("count");
                    String status = rs.getString("status");

                    switch (status) {
                        case "borrowed":
                            borrowCount = count;
                            break;
                        case "returned":
                            returnCount = count;
                            break;
                        case "waiting":
                            waitingCount = count;
                            break;
                        case "lost":
                            lostCount = count;
                            break;
                        // Add cases for other statuses if necessary
                    }
                }

                // Fetch view count
                try (PreparedStatement viewStmt = conn.prepareStatement(viewCountQuery)) {
                    viewStmt.setString(1, bookId);
                    ResultSet viewRs = viewStmt.executeQuery();
                    if (viewRs.next()) {
                        accessCount = viewRs.getInt("viewCount");
                    }
                }

                // Add the borrow info to the list
                borrowInfoList.add(new BorrowInfo(accessCount, bookmarkCount, borrowCount, returnCount, waitingCount, lostCount));
                borrowTable.setItems(borrowInfoList);
            }

        } catch (SQLException e) {
            System.err.println("Error loading borrow information: " + e.getMessage());
        }
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
        setFieldsEditable(true);  // Enable editing
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if (currentBook != null) {
            String query = "UPDATE books SET author = ?, amount = ?, kind = ?, description = ? WHERE book_ID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nameAuthorText.getText());
                pstmt.setInt(2, Integer.parseInt(limitBookText.getText()));
                pstmt.setString(3, categoryBookText.getText());
                pstmt.setString(4, content.getText());
                pstmt.setString(5, currentBook.getId());
                pstmt.executeUpdate();
                System.out.println("Book information updated successfully.");
                setFieldsEditable(false);  // Disable editing after save
            } catch (SQLException e) {
                System.err.println("Error saving book information: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + e.getMessage());
            }
        }
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        if (currentBook != null) {
            String query = "DELETE FROM books WHERE book_ID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, currentBook.getId());
                pstmt.executeUpdate();

                // Clear text fields after deletion
                nameAuthorText.setText("");
                limitBookText.setText("");
                categoryBookText.setText("");
                goalBookText.setText("");
                content.setText("");

                // Clear borrow table
                borrowTable.getItems().clear();

                System.out.println("Book deleted successfully.");
            } catch (SQLException e) {
                System.err.println("Error deleting book: " + e.getMessage());
            }
        }
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        // Implement search functionality, if needed
    }
}