package com.example.my_group_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminBorrowBookController extends AdminMenuController{

    @FXML
    private VBox vBox;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML
    public void initialize() {
        loadBorrowedBooks();
    }

    public void loadBorrowedBooks() {
        String query = "SELECT bo.book_ID, bo.title, bo.author, bo.image, bo.description, bo.kind, bo.viewCount, bo.addDate, " +
                "b.User_ID, b.borrowDate, b.backDate, b.status " +
                "FROM borrow b JOIN books bo ON b.book_ID = bo.book_ID";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
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
                        LocalDateTime.parse(rs.getString("addDate"), formatter),
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

        Pane bookIdPane = createCenterAlignedPane(110, borrowedBook.getId());
        ScrollPane bookNamePane = createScrollablePane(185, borrowedBook.getTitle());
        Pane userIdPane = createCenterAlignedPane(100, borrowedBook.getUserId());
        Pane dateBorrowPane = createPane(160, borrowedBook.getDateBorrow());
        Pane dateBackPane = createPane(160, borrowedBook.getDateBack());
        Pane statusPane = createCenterAlignedPane(132, borrowedBook.getStatus());

        hBox.getChildren().addAll(bookIdPane, bookNamePane, userIdPane, dateBorrowPane, dateBackPane, statusPane);

        // Add click event handler
        hBox.setOnMouseClicked(event -> {
            try {
                loadAdminBookUserBorrowScene(borrowedBook);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return hBox;
    }

    private Pane createPane(double width, String value) {
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(45);

        Label label = new Label(value);
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(27.0);
        label.setLayoutY(13.0);
        label.setFont(new javafx.scene.text.Font(15.0));

        pane.getChildren().add(label);
        return pane;
    }

    private Pane createCenterAlignedPane(double width, String value) {
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(45);

        Label label = new Label(value);
        label.setAlignment(Pos.CENTER);
        label.setLayoutX((width - 80) / 2); // Center align based on Pane width
        label.setLayoutY(13.0);
        label.setFont(new javafx.scene.text.Font(15.0));

        pane.getChildren().add(label);
        return pane;
    }

    private ScrollPane createScrollablePane(double width, String value) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(width);
        scrollPane.setPrefHeight(45);

        Pane pane = new Pane();
        Label label = new Label(value);
        label.setLayoutX(10.0);
        label.setLayoutY(13.0);
        label.setFont(new javafx.scene.text.Font(15.0));
        pane.getChildren().add(label);

        scrollPane.setContent(pane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("hidden-scrollbar");

        return scrollPane;
    }

    private void loadAdminBookUserBorrowScene(BorrowedBook borrowedBook) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminBook_UserBorrow.fxml"));
        Parent root = loader.load();

        // Pass the borrowedBook data to the new controller
        AdminBookUserBorrowController controller = loader.getController();
        controller.setBorrowedBookData(borrowedBook);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
