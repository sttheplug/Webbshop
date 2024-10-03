package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.DatabaseConnectionManager;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, Password, role) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE UserID = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String UPDATE_USER = "UPDATE user SET username = ?, Password = ?, role = ? WHERE UserID = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE UserID = ?";
    private static final String SELECT_USER_BY_NAME_PASS = "SELECT * FROM user WHERE username = ? AND password = ?";

    public boolean addUser(User user) {
        String INSERT_USER_SQL = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name()); // Convert Enum to String

            int affectedRows = preparedStatement.executeUpdate(); // Execute the update statement

            // If rows were affected, insertion was successful
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedUserID = generatedKeys.getInt(1);
                        user.setUserID(generatedUserID); // Set the generated userID in the User object
                    }
                }
                return true; // Return true if insertion was successful
            } else {
                return false; // No rows were affected, insertion failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was a SQLException
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME_PASS)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("Password"),
                        User.Role.valueOf(rs.getString("role")) // Convert String to Enum
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
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
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
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
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
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
        User newUser = new User("john_doe", "password123", User.Role.customer);

        // Create the UserDAO object
        UserDAO userDAO = new UserDAO();

        // Add the user to the database
        userDAO.addUser(newUser);

        // Optionally, verify by fetching all users or checking the database directly
        System.out.println("User added to the database.");
    }


}
