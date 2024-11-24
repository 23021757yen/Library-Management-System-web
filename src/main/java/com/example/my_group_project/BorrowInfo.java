package com.example.my_group_project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BorrowInfo {
    private final IntegerProperty accessCount;
    private final IntegerProperty bookmarkCount;
    private final IntegerProperty borrowCount;
    private final IntegerProperty returnCount;

    public BorrowInfo(int accessCount, int bookmarkCount, int borrowCount, int returnCount) {
        this.accessCount = new SimpleIntegerProperty(accessCount);
        this.bookmarkCount = new SimpleIntegerProperty(bookmarkCount);
        this.borrowCount = new SimpleIntegerProperty(borrowCount);
        this.returnCount = new SimpleIntegerProperty(returnCount);
    }

    // Getters
    public int getAccessCount() {
        return accessCount.get();
    }

    public IntegerProperty accessCountProperty() {
        return accessCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount.get();
    }

    public IntegerProperty bookmarkCountProperty() {
        return bookmarkCount;
    }

    public int getBorrowCount() {
        return borrowCount.get();
    }

    public IntegerProperty borrowCountProperty() {
        return borrowCount;
    }

    public int getReturnCount() {
        return returnCount.get();
    }

    public IntegerProperty returnCountProperty() {
        return returnCount;
    }
}
