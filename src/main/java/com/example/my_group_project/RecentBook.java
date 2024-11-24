package com.example.my_group_project;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RecentBook {
    private static final int MAX_BOOKS = 10;
    private Queue<Book> recentbooks = new LinkedList<>(recentBooks());


    void addBook(Book book) throws SQLException {
        if(recentbooks.contains(book)){
            recentbooks.remove(book);
            deleteData(book);
        }
        if(recentbooks.size() > MAX_BOOKS){
            recentbooks.poll();
        }
        recentbooks.offer(book);
    }

    public List<Book> getRecentbooks() {

        List<Book> books = new ArrayList<>(recentbooks);
        books.sort(Comparator.comparing(Book::getTime, Comparator.nullsLast(LocalDateTime::compareTo)).reversed());

        return books;
    }



    public static List<Book> recentBooks() {

        String query = "SELECT b.*,time FROM books b JOIN recentbooks re ON re.book_ID = b.book_ID ORDER BY time desc LIMIT 10";
        List<Book> latestBooks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Book newBook = new Book(resultSet.getString("book_ID"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("kind"),
                        resultSet.getInt("viewCount"), resultSet.getString("image"), resultSet.getString("description"));
                String dateTimeStr = resultSet.getString("time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                newBook.setTime(LocalDateTime.parse(dateTimeStr,formatter));
                latestBooks.add(newBook);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return latestBooks;
    }

    void changeBooksToDate() throws SQLException {
        addToData(getRecentbooks());
    }

    void addToData(List<Book> books) throws SQLException {
        for(int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if(recentbooks.contains(book)){
                deleteData(book);
            }
            String query = "INSERT INTO `recentbooks`(user_ID, book_ID, time) VALUES (?,?,?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, User.getCurrentUser().getId());
                statement.setString(2, book.getId());
                statement.setString(3,String.valueOf(book.getTime()));
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    void deleteData(Book book) {
        String query = "DELETE FROM recentbooks WHERE user_ID = ? AND book_ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, User.getCurrentUser().getId());
            statement.setString(2, book.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


