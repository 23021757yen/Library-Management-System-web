package com.example.my_group_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {

    private String userId;
    private String userName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;

    public AddUser(String gender, String dateOfBirth, String phone, String email, String userName, String userId) {
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.userName = userName;
        this.userId = userId;
    }

    public boolean insertIntoDatabase() {
        String sql = "INSERT INTO user (User_ID, name, email, phone, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, userName);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, dateOfBirth);
            pstmt.setString(6, gender);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
