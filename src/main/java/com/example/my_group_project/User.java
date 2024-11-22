package com.example.my_group_project;

import java.sql.SQLException;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String fullname;
    private String password;
    private String gender;
    private String email;
    private String phone;
    private String dateOfBirth;

    private static User currentUser;

    private RecentBook recentBook = new RecentBook();

    public User() {}

    public User(String id, String username, String fullname, String email, String phone, String dateOfBirth) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
