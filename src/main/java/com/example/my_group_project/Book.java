package com.example.my_group_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Book {
    private String id;         // Add id field
    private String title;
    private String authors;
    private String imageUrl;
    private String description;
    private String genre;
    private int viewCount = 0;// Add genre field
    private LocalDateTime time;
    private int borrowCount = 0;
    private static Book mainBook;

    public Book() {}



    // Constructor with id and genre
    public Book(String id, String title, String authors, String imageUrl, String description, String genre) {
        this.id = id;           // Initialize id
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = description;
        this.genre = genre;     // Initialize genre
    }
    public Book(String id, String title, String authors, String imageUrl, String description, String genre,int viewCount,int borrowCount){
        this.authors = authors;
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.viewCount = viewCount;
        this.imageUrl = imageUrl;
        this.description = description;
        this.borrowCount = borrowCount;
    }

    //constructor
    public Book(String title, String authors, String imageUrl, String Description,String genre) {
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = Description;
        this.genre = genre;
    }



    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public static Book getMainBook() {
        return mainBook;
    }

    public static void setMainBook(Book mainBook) {
        Book.mainBook = mainBook;
    }

    // Getters and setters
    public String getId() {
        return id;              // Getter for id
    }

    public void setId(String id) {
        this.id = id;           // Setter for id
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;           // Getter for genre
    }

    public void setGenre(String genre) {
        this.genre = genre;     // Setter for genre
    }

    public int getViewCount(){
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}