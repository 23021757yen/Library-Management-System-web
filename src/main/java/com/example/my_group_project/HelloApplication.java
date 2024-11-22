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

        stage.setOnCloseRequest(this::showAlert); // Save data before exiting
    }

    private void showAlert(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Are you sure you want to leave us?");
        alert.setContentText(null);

        ButtonType sureButton = new ButtonType("Goodbye!");
        ButtonType cancelButton = new ButtonType("I'll stay", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(sureButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == sureButton) {
                try {
                    saveRecentBooksToDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (result.get() == cancelButton) {
                event.consume(); // Cancel the close request
            }
        }
    }

    private void saveRecentBooksToDatabase() throws SQLException {
        if (User.getCurrentUser() != null) {
            User.getCurrentUser().getRecentBook().changeBooksToDate();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}