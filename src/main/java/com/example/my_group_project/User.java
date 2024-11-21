package com.example.my_group_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private String phone;
    private String dateOfBirth;
    private static User currentUser;
    private RecentBookController recentBookConTroller = new RecentBookController();
    private List<Book> savedBooks = new ArrayList<>();

    public RecentBookController getRecentBookConTroller() {
        return recentBookConTroller;
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }



    public void savedBooks(Book book, User user) throws SQLException {
        System.out.println("Ua sao no khong luu vay");

        String query = "INSERT INTO highlightbook (user_ID, book_ID) VALUES (?, ?)";
        String checkQuery = "SELECT * FROM highlightbook WHERE user_ID = ? AND book_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, user.getId());
            checkStmt.setString(2, book.getId());
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {

                System.out.println("Hong hong hong ne ne ne ");
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setString(1, user.getId());
                    preparedStatement.setString(2, book.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while saving book", e);
        }

    }

    public void removedBooks(Book book){
        //savedBooks.remove(book);
        // chu y cho nay
        System.out.println(book.getId());
        String query = "DELETE FROM highlightbook WHERE user_ID = ? AND book_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){

            preparedStatement.setString(1, User.currentUser.getId());
            preparedStatement.setString(2,book.getId());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User() {

    }

    public User (String id, String userName, String email, String phone, String dateOfBirth, String gender) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSavedBooks(List<Book> savedBooks) {
        this.savedBooks = savedBooks;
    }


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getSavedBooks(){
        List<Book> books = new ArrayList<>();
        if(currentUser != null) {
            System.out.println("Hallooo");
            String query = "SELECT b.* FROM books b " +
                    "JOIN highlightbook h ON h.book_ID = b.book_ID " +
                    "WHERE h.user_ID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, User.currentUser.getId());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Book book = new Book();
                    book.setId(resultSet.getString("book_ID"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthors(resultSet.getString("author"));
                    book.setDescription(resultSet.getString("description"));
                    book.setImage(resultSet.getString("image"));
                    book.setViewCount(resultSet.getInt("viewCount"));
                    books.add(book);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return books;
    }
}
