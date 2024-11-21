package com.example.my_group_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookUtils {
    public static Book getBookById(String bookId) {
        String sql = "SELECT * FROM books WHERE book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getString("book_ID"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("image"),
                            rs.getString("description"),
                            rs.getString("kind")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertBookIntoDatabase(Book book) {
        // Check if the book already exists
        if (getBookById(book.getId()) != null) {
            System.out.println("Book with ID " + book.getId() + " already exists.");
            return;
        }

        String sql = "INSERT INTO books (book_ID, title, author, image, description, kind) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthors());
            pstmt.setString(4, book.getImageUrl());
            pstmt.setString(5, book.getDescription());
            pstmt.setString(6, book.getGenre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveBookToHistory(Book book, String userId) {
        // Ensure the book exists in the book table first
        insertBookIntoDatabase(book);

        // Insert the book into the user history table
        String sql = "INSERT INTO user_history (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId); // Pass the actual user ID
            pstmt.setString(2, book.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}