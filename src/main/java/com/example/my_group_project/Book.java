package com.example.my_group_project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.List;

class Book {
    private Image imageSrc;
    private String title;
    private String authors;
    private String content;
    private int pageViews;
    private int numberOfRead;
    private int numberOfBorrow;
    private List<String>category ;

    public Book(Image imageSrc, String title, String authors, String content, int pageViews,
                int numberOfRead, int numberOfBorrow, List<String> category) {
        this.imageSrc = imageSrc;
        this.title = title;
        this.authors = authors;
        this.content = content;
        this.pageViews = pageViews;
        this.numberOfRead = numberOfRead;
        this.numberOfBorrow = numberOfBorrow;
        this.category = category;

    }

    public Book(String title, String authors) {
        this.title = title;
        this.authors = authors;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public int getNumberOfBorrow() {
        return numberOfBorrow;
    }

    public void setNumberOfBorrow(int numberOfBorrow) {
        this.numberOfBorrow = numberOfBorrow;
    }

    public int getNumberOfRead() {
        return numberOfRead;
    }

    public void setNumberOfRead(int numberOfRead) {
        this.numberOfRead = numberOfRead;
    }

    public int getPageViews() {
        return pageViews;
    }

    public void setPageViews(int pageViews) {
        this.pageViews = pageViews;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(Image imageSrc) {
        this.imageSrc = imageSrc;
    }
}
