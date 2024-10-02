package com.example.webbshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
    // Step 1: Define the database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/webshop";
    private static final String USER = "root";
    private static final String PASSWORD = "Aprilapril23.";

    // Step 2: Method to create and return a new database connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Unable to load JDBC Driver");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Optional: Method to close the database connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            // Run a simple query to check if the connection is valid
            stmt.execute("SELECT 1");
            System.out.println("Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            return false;
        }
    }
}
