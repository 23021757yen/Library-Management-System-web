package com.example.my_group_project ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserHistoryController extends UserMenuController {

    @FXML
    private VBox historyVBox;

    private String currentUserId = HelloController.userIdMain; // Replace with actual user ID handling

    @FXML
    public void initialize() {
        displayHistory();
    }



    private void displayHistory() {
        List<Book> clickedBooks = getClickedBooksFromDatabase();
        if (clickedBooks.isEmpty()) {
            historyVBox.getChildren().add(new Label("No books clicked yet."));
            return;
        }

        for (Book book : clickedBooks) {
            if (book == null) {
                continue; // Skip if the book object is null
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookProfileHighLight.fxml"));
                Pane bookCard = loader.load();

                UserBookProfileHighlightController controller = loader.getController();
                controller.setBookDetails(book);

                bookCard.setOnMouseClicked(event -> {
                    System.out.println("Book clicked: " + book.getTitle());

                    showBookProfile(book,bookCard); // Open book profile scene
                });

                historyVBox.getChildren().add(bookCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<Book> getClickedBooksFromDatabase() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_ID FROM borrow WHERE User_ID = ? ORDER BY dueDate DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, User.getCurrentUser().getId());//currentUserId);
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

    //private
    public static void showBookProfile(Book book, Pane pane) {
        try {
            Book.setMainBook(book);
            FXMLLoader loader = new FXMLLoader(UserHistoryController.class.getResource("bookProfile.fxml"));
            Pane root = loader.load();

            /*UserBookProfileController controller = loader.getController();

            controller.setBookDetails(book);

             */
            Scene scene = new Scene(root);
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Book Profile");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logOutOnAction(ActionEvent event) {
super.logOutButtonOnAction(event);
    }


    /*private void showBookProfile(Book book) {
        try {
            Book.setMainBook(book);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookProfile.fxml"));
            Pane root = loader.load();

            UserBookProfileController controller = loader.getController();

            controller.setBookDetails(book);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Profile");
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */
}
