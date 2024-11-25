package com.example.my_group_project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class BookUtils {

    // Get the book by ID
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
                            rs.getInt("viewCount"),
                            rs.getString("kind"),
                            rs.getString("description"),
                            rs.getTimestamp("addDate").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert book into the database
    public static void insertBookIntoDatabase(Book book) {
        // Check if the book already exists
        if (getBookById(book.getId()) != null) {
            System.out.println("Book with ID " + book.getId() + " already exists.");
            return;
        }

        String sql = "INSERT INTO books (book_ID, title, author, description, kind, viewCount, addDate"
                + (book.getImageUrl() != null ? ", image" : "")
                + ") VALUES (?, ?, ?, ?, ?, ?, ?"
                + (book.getImageUrl() != null ? ", ?" : "")
                + ")";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthors());
            pstmt.setString(4, book.getDescription());
            pstmt.setString(5, book.getGenre());
            pstmt.setInt(6, book.getViewCount());
            LocalDateTime dateTime = book.getTime();
            pstmt.setTimestamp(7, dateTime != null ? Timestamp.valueOf(dateTime) : Timestamp.valueOf(LocalDateTime.now()));

            if (book.getImageUrl() != null) {
                pstmt.setString(8, book.getImageUrl());
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save book to user history
    public static void saveBookToHistory(Book book, String userId) {
        // Ensure the book exists in the books table first
        insertBookIntoDatabase(book);

        // Insert the book into the user history table
        String sql = "INSERT INTO user_history (user_id, book_id, time) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, book.getId());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(book.getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove book from highlight
    public static void removeBookFromHighLight(Book book, User user) {
        String deleteQuery = "DELETE FROM highlightbook WHERE user_ID = ? AND book_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            deleteStmt.setString(1, user.getId());
            deleteStmt.setString(2, book.getId());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get saved books from highlight
    public static List<Book> getSavedBooksFromHighLight(User user) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM books b JOIN highlightbook h ON h.book_ID = b.book_ID WHERE h.user_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("book_ID"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("image"),
                        rs.getInt("viewCount"),
                        rs.getString("kind"),
                        rs.getString("description"),
                        rs.getTimestamp("addDate").toLocalDateTime()
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}