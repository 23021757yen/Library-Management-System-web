package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class BookProfileController extends BaseController{

    @FXML
    private Button homeButton;

    @FXML
    private Text nameOfBookTextField;

    @FXML
    private Text contentTextField;

    @FXML
    private Text nameOfAuthorTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button borrowButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button categoryComedyButton;

    @FXML
    private Text content;

    @FXML
    private Button highlightButton;


    @FXML
    private Text limitAge;

    @FXML
    private Button logOutButton;

    @FXML
    private Button moreInforButton;

    @FXML
    private Text nameOfAuthor;

    @FXML
    private Text nameOfBook;

    @FXML
    private Text numberOfBorrow;

    @FXML
    private Text numberOfRead;

    @FXML
    private Text pageViews;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    @FXML
    void categoryButtonOnAction(ActionEvent event) {
        super.changeScene("categoryMenu.fxml", "Category Menu");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        super.changeScene("welcomeToWebsite.fxml", "HelloView");
    }

    @FXML
    void moreInforButtonOnAction(ActionEvent event) {
        super.changeScene("moreInformation.fxml", "More Information");
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        super.changeScene("profileUser.fxml", "Profile");
    }

    @FXML
    void searchButtonOnAction(ActionEvent event) {
        super.changeScene("searchBook.fxml", "Searching");
    }


    @FXML
    void homeButtonOnAction(ActionEvent event) {
        super.changeScene("home.fxml", "Home");
    }

    public void setBookDetails(Book book) {
        if (book == null) {
            // Add error handling as necessary
            System.err.println("Book is null.");
            return;
        }
        // Ensuring the text update is done on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            nameOfBookTextField.setText(book.getTitle());
            nameOfAuthorTextField.setText(book.getAuthors());
            contentTextField.setText(book.getDescription());
        });
    }

}