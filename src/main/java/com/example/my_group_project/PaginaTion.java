package com.example.my_group_project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class PaginaTion {

    private int currentPage;
    private final int MAX_PAGE = 10;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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

                    UserHistoryController.showBookProfile(book, bookCard); // Open book profile scene
                });

                pageVBox.getChildren().add(bookCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pageVBox;
    }

    void pagination(List<Book> borrowedBooks, Pagination paginationHistory) {
        System.out.println("Running...");
        int booksPerPage = MAX_PAGE;
        ScrollPane scrollPane = new ScrollPane();
        int pageCount = (int) Math.ceil(borrowedBooks.size() / (double) booksPerPage);

        if (pageCount == 0) {
            pageCount = 1;
        }
        paginationHistory.setPageCount(pageCount);
        //paginationHistory.setCurrentPageIndex(0);
        paginationHistory.setPageFactory((Integer indexPage) -> {
            System.out.println("Hello!!!");
            System.out.println(indexPage);
            int start = indexPage * booksPerPage;
            int end = Math.min(start + booksPerPage, borrowedBooks.size());
            List<Book> pageBooks = borrowedBooks.subList(start, end);
            currentPage = indexPage;
            Node content = displayHistory(pageBooks);
            scrollPane.setContent(content);
            scrollPane.setFitToWidth(true);

            return scrollPane;
        });

    }

    int backButton(){
        return currentPage--;
    }
}
