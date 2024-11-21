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
    private String image;
    private String description;
    private String genre;
    private int number_of_borrows= 0;
    private int viewCount = 0;// Add genre field
    private LocalDateTime time;
    private static Book mainBook;

    public Book() {}



    // Constructor with id and genre
    public Book(String id, String title, String authors, String image, String description, String genre) {
        this.id = id;           // Initialize id
        this.title = title;
        this.authors = authors;
        this.image = image;
        this.description = description;
        this.genre = genre;     // Initialize genre
    }
    public Book(String id, String title,String author, String genre, int viewCount, String image, String description){
        this.authors = author;
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.viewCount = viewCount;
        this.image = image;
        this.description = description;
    }

    //constructor
    public Book(String title, String authors, String image, String Description,String genre) {
        this.title = title;
        this.authors = authors;
        this.image = image;
        this.description = Description;
        this.genre = genre;
    }


    public Book(String id, String title, String authors,String image, String description, String genre, int viewCount, int number_of_borrows) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.image = image;
        this.description = description;
        this.genre = genre;
        this.viewCount = viewCount;
        this.number_of_borrows = number_of_borrows ;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image= image;
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

    public int getNumber_of_borrows() {
        return number_of_borrows;
    }

    public void setNumber_of_borrows(int number_of_borrows) {
        this.number_of_borrows = number_of_borrows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}