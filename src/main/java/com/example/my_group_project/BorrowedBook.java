package com.example.my_group_project;

import java.time.LocalDateTime;

public class BorrowedBook extends Book {
    private String userId;
    private String dateBorrow;
    private String dateBack;
    private String status;

    // Constructor
    public BorrowedBook(String userId, String bookId, String title, String authors, String imageUrl, String description,
                        String genre, int viewCount, int amount, LocalDateTime time, String dateBorrow, String dateBack, String status) {
        super(bookId, title, authors, imageUrl, description, genre, viewCount, amount, time);
        this.userId = userId;
        this.dateBorrow = dateBorrow;
        this.dateBack = dateBack;
        this.status = status;
    }

    public BorrowedBook(String userId, String bookId, String title, String authors, String imageUrl, String description,
                        String genre, int viewCount, LocalDateTime time, String dateBorrow, String dateBack, String status) {
        super(bookId, title, authors, imageUrl, viewCount, genre, description, time);
        this.userId = userId;
        this.dateBorrow = dateBorrow;
        this.dateBack = dateBack;
        this.status = status;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public String getDateBack() {
        return dateBack;
    }

    public void setDateBack(String dateBack) {
        this.dateBack = dateBack;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}