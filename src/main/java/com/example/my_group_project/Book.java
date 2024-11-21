package com.example.my_group_project;

public class Book {
    private String title;
    private String authors;
    private String imageUrl;
    private String description;
    private String genre; // Add genre field

    // Constructor with genre
    public Book(String title, String authors, String imageUrl, String description, String genre) {
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = description;
        this.genre = genre; // Initialize genre
    }

    // Getters and setters
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
        return genre; // Getter for genre
    }

    public void setGenre(String genre) {
        this.genre = genre; // Setter for genre
    }
}