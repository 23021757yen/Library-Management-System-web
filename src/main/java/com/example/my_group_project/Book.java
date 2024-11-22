package com.example.my_group_project;

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
    private int amount;
    private LocalDateTime time;
    private static Book mainBook;

    public Book() {}

    public Book(String id, String title, String authors, String imageUrl, String description, String genre, int viewCount, int amount, LocalDateTime time) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = description;
        this.genre = genre;
        this.viewCount = viewCount;
        this.amount = amount;
        this.time = (time != null) ? time : LocalDateTime.now();
    }

    // Constructor with id and genre
    public Book(String id, String title, String authors, String imageUrl, String description, String genre) {
        this.id = id;           // Initialize id
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = description;
        this.genre = genre;     // Initialize genre
    }

    public Book(String id, String title,String author, String genre, int viewCount, String imageUrl, String description){
        this.authors = author;
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.viewCount = viewCount;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Book(String id, String title, String author, String imageUrl, int viewCount, String genre, String description, LocalDateTime time){
        this.authors = author;
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.viewCount = viewCount;
        this.imageUrl = imageUrl;
        this.description = description;
        this.time = (time != null) ? time : LocalDateTime.now();
    }

    //constructor
    public Book(String title, String authors, String imageUrl, String Description,String genre) {
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = Description;
        this.genre = genre;
    }

    public Book(String id, String title, String authors, String genre, int amount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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