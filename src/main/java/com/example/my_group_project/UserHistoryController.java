package com.example.my_group_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserHistoryController extends UserMenuController {

    @FXML
    private VBox historyVBox;

    @FXML
    private Pagination paginationHistory;

    private PaginaTion paginaTion = new PaginaTion();

    private String currentUserId = HelloController.userIdMain;

    @FXML
    public void initialize() {
        List<Book> clickedBooks = getClickedBooksFromDatabase();
        pagination(clickedBooks);
    }

    private Node displayHistory(List<Book> clickedBooks) {
        VBox pageVBox = new VBox();
        pageVBox.setSpacing(10);
        if (clickedBooks.isEmpty()) {
            pageVBox.getChildren().add(new Label("No books clicked yet."));
            return pageVBox;
        }

        for (Book book : clickedBooks) {
            if (book == null) {
                continue;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookProfileHighLight.fxml"));
                Pane bookCard = loader.load();

                UserBookProfileHighlightController controller = loader.getController();
                controller.setBookDetails(book);

                bookCard.setOnMouseClicked(event -> {
                    System.out.println("Book clicked: " + book.getTitle());
                    showBookProfile(book, bookCard); // Open book profile scene
                });

                pageVBox.getChildren().add(bookCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pageVBox;
    }

    public static List<Book> getClickedBooksFromDatabase() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_ID FROM borrow WHERE User_ID = ? ORDER BY backDate DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, User.getCurrentUser().getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String bookId = rs.getString("book_ID");
                    Book book = BookUtils.getBookById(bookId); // Ensure this method is public or accessible
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static void showBookProfile(Book book, Pane pane) {
        try {
            Book.setMainBook(book);
            FXMLLoader loader = new FXMLLoader(UserHistoryController.class.getResource("bookProfile.fxml"));
            Pane root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Book Profile");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void pagination(List<Book> borrowedBooks) {
        paginaTion.pagination(borrowedBooks, paginationHistory);
    }
}