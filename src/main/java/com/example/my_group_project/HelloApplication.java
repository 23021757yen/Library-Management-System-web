package com.example.my_group_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
        alert.setHeaderText("Bạn chắc chắn muốn rời bỏ chúng tôi chứ???");
        alert.setContentText(null);

        ButtonType sureButton = new ButtonType("Tạm biệt!");
        ButtonType cancelButton = new ButtonType("Tôi ở lại", ButtonBar.ButtonData.CANCEL_CLOSE);

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