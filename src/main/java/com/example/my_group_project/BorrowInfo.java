package com.example.my_group_project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BorrowInfo {
    private final IntegerProperty accessCount;
    private final IntegerProperty bookmarkCount;
    private final IntegerProperty borrowCount;
    private final IntegerProperty returnCount;
    private final IntegerProperty waitingCount;
    private final IntegerProperty lostCount;

    public BorrowInfo(int accessCount, int bookmarkCount, int borrowCount, int returnCount, int waitingCount, int lostCount) {
        this.accessCount = new SimpleIntegerProperty(accessCount);
        this.bookmarkCount = new SimpleIntegerProperty(bookmarkCount);
        this.borrowCount = new SimpleIntegerProperty(borrowCount);
        this.returnCount = new SimpleIntegerProperty(returnCount);
        this.waitingCount = new SimpleIntegerProperty(waitingCount);
        this.lostCount = new SimpleIntegerProperty(lostCount);
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

    public int getWaitingCount() {
        return waitingCount.get();
    }

    public IntegerProperty waitingCountProperty() {
        return waitingCount;
    }

    public int getLostCount() {
        return lostCount.get();
    }

    public IntegerProperty lostCountProperty() {
        return lostCount;
    }
}
