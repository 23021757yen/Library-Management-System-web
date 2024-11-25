package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class UserBookSearch extends BaseController {
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox resultsVBox;

    // Create an instance of SearchCache
    private SearchCache searchCache = new SearchCache();

    // Create an instance of BookRecommendation
    private BookRecommendation bookRecommendation = new BookRecommendation();

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
        long startTime = System.currentTimeMillis(); // Start time measurement
        searchBooks();
        long endTime = System.currentTimeMillis(); // End time measurement
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " ms");
    }

    private void searchBooks() {
        String query = searchTextField.getText().trim();

        // Check if the query is empty
        if (query.isEmpty()) {
            resultsVBox.getChildren().clear();
            return;
        }

        // Check cache first
        if (searchCache.contains(query)) {
            System.out.println("Fetching books from cache for query: " + query);
            List<Book> cachedBooks = searchCache.get(query);
            displaySearchResults(cachedBooks);
            return;
        }

        // Search using the API
        List<Book> books;
        try {
            books = BookAPI.searchBooks(query);
            // Cache the results
            searchCache.put(query, books);
            System.out.println("Books fetched from API and cached for query: " + query);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return;
        }

        // Enhance search results using AI-based recommendation
        List<Book> recommendedBooks = bookRecommendation.recommendBooks(query, books);

        // Display search results
        displaySearchResults(recommendedBooks);
    }

    private void displaySearchResults(List<Book> books) {
        // Play a sound effect when displaying search results
        SoundManager.playSound("src/main/resources/soundEffects/SEFE_KidsCheering.wav");

        resultsVBox.getChildren().clear();
        for (Book book : books) {
            HBox bookInfoBox = new HBox(10);

            Text bookInfo = new Text(book.getTitle() + " by " + book.getAuthors());
            bookInfo.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-fill: #333333;");

            if (book.getImageUrl() != null) {
                try {
                    Image image = new Image(book.getImageUrl());
                    ImageView bookImage = new ImageView(image);
                    bookImage.setFitHeight(50);
                    bookImage.setFitWidth(50);
                    bookInfoBox.getChildren().add(bookImage);
                } catch (Exception e) {
                    System.out.println("Failed to load image: " + e.getMessage());
                }
            }

            BookUtils.insertBookIntoDatabase(book);

            bookInfoBox.getChildren().add(bookInfo);

            bookInfoBox.setOnMouseClicked(event -> {
                // Play a sound effect when a book item is clicked
                SoundManager.playSound("src/main/resources/soundEffects/SEFE_AngelsSinging.wav");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("bookProfile.fxml"));
                    Parent root = loader.load();

                    UserBookProfileController controller = loader.getController();
                    controller.setBookDetails(book);

                    Stage stage = (Stage) resultsVBox.getScene().getWindow();
                    stage.setTitle("Book Profile");
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            resultsVBox.getChildren().add(bookInfoBox);
        }
    }

    public static void main(String[] args) {
        UserBookSearch userBookSearch = new UserBookSearch();
        userBookSearch.testSearchBooks();
    }

    public void testSearchBooks() {
        try {
            String testQuery = "fiction";
            long startTime = System.currentTimeMillis(); // Start time measurement
            searchBooks(testQuery);
            long endTime = System.currentTimeMillis(); // End time measurement
            long executionTime = endTime - startTime;
            System.out.println("Execution time for query \"" + testQuery + "\": " + executionTime + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchBooks(String query) {
        // This method is for testing purposes, simulating the searchBooks method with a provided query
        // without needing to trigger from UI.
        searchTextField.setText(query);
        searchTextFieldOnAction(null);
    }
}