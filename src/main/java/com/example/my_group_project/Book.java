package com.example.my_group_project;

public class Book {
    private String title;
    private String authors;
    private String imageUrl;
    private String description;

    public Book(String title, String authors) {
        this.title = title;
        this.authors = authors;
    }
    public Book(String title, String authors, String imageUrl) {
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
    }

    public Book(String title, String authors, String imageUrl, String Description) {
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.description = Description;
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
}

