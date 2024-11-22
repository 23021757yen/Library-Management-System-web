package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdminUserManagementController extends AdminMenuController {

    @FXML
    private VBox vBox;

    @FXML
    public void initialize() {
        loadBorrowedBooks();
    }

    public void loadBorrowedBooks() {
        String query = "SELECT b.User_ID, b.book_ID, bo.title, bo.author, bo.image, bo.description, bo.kind, " +
                "bo.viewCount, bo.addDate, b.borrowDate, b.backDate, b.status " +
                "FROM borrow b JOIN books bo ON b.book_ID = bo.book_ID";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            vBox.getChildren().clear(); // Clear existing items
            while (rs.next()) {
                BorrowedBook borrowedBook = new BorrowedBook(
                        rs.getString("User_ID"),
                        rs.getString("book_ID"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("kind"),
                        rs.getInt("viewCount"),
                        LocalDateTime.parse(rs.getString("addDate")),
                        rs.getString("borrowDate"),
                        rs.getString("backDate"),
                        rs.getString("status")
                );
                HBox row = createRow(borrowedBook);
                vBox.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private HBox createRow(BorrowedBook borrowedBook) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.setPrefHeight(45);
        hBox.setPrefWidth(885);

        Label userIdLabel = new Label(borrowedBook.getUserId());
        userIdLabel.setPrefWidth(130);
        userIdLabel.setStyle("-fx-alignment: center;");

        Label bookIdLabel = new Label(borrowedBook.getId());
        bookIdLabel.setPrefWidth(130);
        bookIdLabel.setStyle("-fx-alignment: center;");

        Label bookNameLabel = new Label(borrowedBook.getTitle());
        bookNameLabel.setPrefWidth(200);
        bookNameLabel.setStyle("-fx-alignment: center;");

        Label dateBorrowLabel = new Label(borrowedBook.getDateBorrow());
        dateBorrowLabel.setPrefWidth(140);
        dateBorrowLabel.setStyle("-fx-alignment: center;");

        Label dateBackLabel = new Label(borrowedBook.getDateBack());
        dateBackLabel.setPrefWidth(140);
        dateBackLabel.setStyle("-fx-alignment: center;");

        Label statusLabel = new Label(borrowedBook.getStatus());
        statusLabel.setPrefWidth(140);
        statusLabel.setStyle("-fx-alignment: center;");

        hBox.getChildren().addAll(userIdLabel, bookIdLabel, bookNameLabel, dateBorrowLabel, dateBackLabel, statusLabel);
        return hBox;
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        // Implement search functionality, if needed
    }

    @FXML
    void addBookButtonOnAction(ActionEvent event) {
        // Implement add book functionality, if needed
    }
}