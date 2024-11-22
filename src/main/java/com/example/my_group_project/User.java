package com.example.my_group_project;

import java.sql.SQLException;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private static User currentUser;

    private RecentBook recentBook = new RecentBook();

    public User() {}

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String id) {
        this.id = id;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RecentBook getRecentBook() {
        return recentBook;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Methods for managing saved books
    public void saveBook(Book book) throws SQLException {
        BookUtils.saveBookToHistory(book, this.id);
    }

    public void removeBook(Book book) {
        BookUtils.removeBookFromHighLight(book, this);
    }

    public List<Book> getSavedBooks() {
        return BookUtils.getSavedBooksFromHighLight(this);
    }
}
