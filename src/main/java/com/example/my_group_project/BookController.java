package com.example.my_group_project;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    private static List<Book> listofBooks =  returnBookMostView("SELECT * FROM books ORDER BY viewCount desc LIMIT 10");


    public static List<Book> getListofBooks(){
        return listofBooks;
    }


    /*
    public static List<Book> fetchBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int id = resultSet.getInt("book_ID");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("kind");
                int publishedYear = resultSet.getInt("yearPublic");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                int viewCount = resultSet.getInt("viewCount");
                //Image img = new Image(image);
                books.add(new Book(id, title, author, genre, publishedYear, viewCount, image, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

     */

    public static void updateViewCount(String bookID) throws SQLException {
        String query = "UPDATE books SET viewCount = viewCount + 1 WHERE book_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bookID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> returnBookMostView(String message){
        String query = message;
        List<Book> popularBook = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                System.out.println("Co gi ma troi!");
                popularBook.add(new Book(resultSet.getString("book_ID"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("kind"),
                        resultSet.getInt("viewCount"), resultSet.getString("image"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listofBooks = popularBook;
        return popularBook;
    }

    /*public static List<Book> listOfPopularBooks() throws SQLException {
        listofBooks = returnBookMostView();
        return listofBooks;
    }
    */



   /* public static List<Book> fetchLatestBook() throws SQLException {
        String query = "SELECT * FROM books ORDER BY addDate LIMIT 10";
        List<Book> latestBooks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                latestBooks.add(new Book(resultSet.getInt("book_ID"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("kind"),
                        resultSet.getInt("yearPublic"), resultSet.getInt("viewCount"), resultSet.getString("image"), resultSet.getString("description")));
            }
        }
        return latestBooks;
    }


    public static List<Book> deleterecentBooks(String queryy) throws SQLException {
        String query = "SELECT * FROM books ORDER BY addDate LIMIT 10";
        List<Book> latestBooks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                latestBooks.add(new Book(resultSet.getInt("book_ID"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("kind"),
                        resultSet.getInt("yearPublic"), resultSet.getInt("viewCount"),resultSet.getString("image"),resultSet.getString("description")));
            }
        }
        return latestBooks;
    }

    */


}
