package com.example.my_group_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BaseController.setMainStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcomeToWebsite.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            showAlter(event); // Lưu dữ liệu trước khi thoát
        });

    }

    void showAlter(WindowEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát khỏi ứng dụng");
        alert.setHeaderText("do you really wanna quit it");
        alert.setContentText(null);

        alert.getDialogPane().setStyle("-fx-background-color: #f7efd8; -fx-border-radius: 20px;");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/custom.css").toExternalForm());

        ButtonType sureButton = new ButtonType("tam biet");
        ButtonType cancelButton = new ButtonType("toi o lai", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(sureButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == sureButton) {
                try {
                    saveRecentBooksToDatabase();
                    /*Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Save Status");
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("Chúc bạn một ngày mới vui vẻ!!!");
                    infoAlert.showAndWait();

                     */
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (result.get() == cancelButton) {
                event.consume();// Hủy đóng cửa sổ
            }
        }
    }

    void saveRecentBooksToDatabase() throws SQLException {
        if(User.getCurrentUser() != null) {
            User.getCurrentUser().getRecentBookConTroller().changeBooksToDate();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}