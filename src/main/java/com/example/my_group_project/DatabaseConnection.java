package com.example.my_group_project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection() {
        String databaseName = "library";
        String databaseUser = "root";
        String databasePassword = "Catelineblanc4";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Attempting to connect to database...");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Connection established!");
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}