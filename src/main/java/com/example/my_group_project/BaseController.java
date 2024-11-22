package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Stack;

public class BaseController {
    private static Stage mainStage;
    private static Stack<Scene> sceneStack = new Stack<>();

    // Method to set the main stage
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    // Method to get the main stage (optional convenience method)
    public static Stage getMainStage() {
        return mainStage;
    }

    // Method to change the scene and push the current scene to the stack
    protected void changeScene(String fxml, String title) {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage is not set. Ensure setMainStage is called.");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene newScene = new Scene(root);

            // Push the new scene to the stack
            sceneStack.push(newScene);
            mainStage.setTitle(title);
            mainStage.setScene(newScene);

            // Print the scene stack for debugging
            // printSceneStack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to handle the back button action
    @FXML
    void backButtonOnAction(ActionEvent event) {
        if (mainStage == null) {
            throw new IllegalStateException("Main stage is not set. Ensure setMainStage is called.");
        }
        if (!sceneStack.isEmpty()) {
            // Remove the current scene from the stack
            sceneStack.pop();
            if (!sceneStack.isEmpty()) {
                // Set the previous scene as the current scene
                Scene previousScene = sceneStack.peek();
                mainStage.setScene(previousScene);
            } else {
                System.out.println("No previous scene to go back to.");
            }
        } else {
            System.out.println("No previous scene to go back to.");
        }

        // Print the scene stack for debugging
        printSceneStack();
    }

    // Method to print the scene stack
    private void printSceneStack() {
        System.out.println("Scene Stack:");
        for (Scene scene : sceneStack) {
            System.out.println(scene);
        }
    }

    public static boolean showAlter(String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(null);

        ButtonType sureButton = new ButtonType("Đồng ý");
        ButtonType cancelButton = new ButtonType("Hủy bỏ", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(sureButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == sureButton) {
                return true;
            } else if (result.get() == cancelButton) {
                return false;
            }
        }
        return false;
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        if(BaseController.showAlter("Dang xuat", "Ban muon dang xuat chu???")){
            try {
                HelloApplication.saveRecentBooksToDatabase();
            } catch (SQLException e){
                e.printStackTrace();
            }
            changeScene("welcomeToWebsite.fxml", "HelloView");
        }else {
            event.consume();
        }


    }
}