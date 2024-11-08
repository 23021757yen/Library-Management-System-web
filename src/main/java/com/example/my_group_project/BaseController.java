package com.example.my_group_project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        mainStage = stage;
    }

    public static Stage getMainStage() {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage is not set. Ensure setMainStage is called.");
        }
        return mainStage;
    }

    protected void changeScene(String fxml, String title) {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage is not set. Ensure setMainStage is called.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            mainStage.setTitle(title);
            Scene newScene = new Scene(root);
            mainStage.setScene(newScene);

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxml);
            e.printStackTrace();
        }
    }
}
