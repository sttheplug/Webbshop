package com.example.webbshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the database connection for the application.
 * Provides methods to create, close, and test database connections.
 */
public class DatabaseConnectionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/webshop";
    private static final String USER = "root";
    private static final String PASSWORD = "Aprilapril23.";

    /**
     * Creates and returns a new database connection.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Unable to load JDBC Driver");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Closes the provided database connection.
     *
     * @param connection the Connection object to be closed
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tests the database connection by executing a simple query.
     *
     * @return true if the connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("SELECT 1");
            System.out.println("Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            return false;
        }
    }
}
