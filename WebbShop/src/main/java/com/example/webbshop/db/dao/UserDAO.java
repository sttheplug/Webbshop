package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing users in the database.
 * Provides methods to create, read, update, and delete user records.
 */
public class UserDAO {
    private static final String INSERT_USER_SQL = "INSERT INTO user (username, Password, role) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String UPDATE_USER = "UPDATE user SET username = ?, Password = ?, role = ? WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE user_id = ?";
    private static final String SELECT_USER_BY_NAME_PASS = "SELECT * FROM user WHERE username = ? AND password = ?";

    /**
     * Adds a new user to the database.
     *
     * @param user the User object to be added
     * @return the added User object with its generated ID, or null if insertion failed
     */
    public User addUser(User user) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserID(generatedKeys.getInt(1));
                    }
                }
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a user from the database using their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the User object if found, or null if not found
     */
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
                        User.Role.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id the ID of the user to be retrieved
     * @return the User object if found, or null if not found
     */
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
                        User.Role.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of User objects, or an empty list if no users are found
     */
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
                        User.Role.valueOf(rs.getString("role"))
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the User object containing updated information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setInt(4, user.getUserID());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id the ID of the user to be deleted
     * @return true if the user was deleted, false otherwise
     */
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
}
