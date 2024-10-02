package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, Password, role) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE UserID = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, Password = ?, role = ? WHERE UserID = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE UserID = ?";

    public void addUser(User user) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name()); // Convert Enum to String

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedUserID = generatedKeys.getInt(1);
                        user.setUserID(generatedUserID); // Set the userID in the User object
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a user by ID
    public User getUserById(int id) {
        User user = null;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("UserID"),
                        rs.getString("username"),
                        rs.getString("Password"),
                        User.Role.valueOf(rs.getString("role")) // Convert String to Enum
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("username"),
                        rs.getString("Password"),
                        User.Role.valueOf(rs.getString("role")) // Convert String to Enum
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method to update an existing user
    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name()); // Convert Enum to String
            statement.setInt(4, user.getUserID());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    // Method to delete a user by ID
    public boolean deleteUser(int id) {
        boolean rowDeleted = false;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {

            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public static void main(String[] args) {
        // Create a new User object
        User newUser = new User("john_doe", "password123", User.Role.CUSTOMER);

        // Create the UserDAO object
        UserDAO userDAO = new UserDAO();

        // Add the user to the database
        userDAO.addUser(newUser);

        // Optionally, verify by fetching all users or checking the database directly
        System.out.println("User added to the database.");
    }
}
