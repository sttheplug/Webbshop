package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.dao.UserDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class responsible for handling operations related to users.
 */
public class UserService {
    private UserDAO userDAO;

    /**
     * Constructs a UserService instance and initializes the UserDAO.
     */
    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Registers a new user in the system.
     *
     * @param username the username for the new user
     * @param password the password for the new user
     * @param role     the role of the new user (e.g., ADMIN, CUSTOMER)
     * @return the newly created User object, or null if the registration failed
     */
    public User registerUser(String username, String password, User.Role role) {
        User user = new User(username, password, role);
        return userDAO.addUser(user);
    }

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the authenticated User object, or null if authentication failed
     */
    public User loginUser(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    /**
     * Searches for a user by their unique identifier (ID).
     *
     * @param id the ID of the user to be searched
     * @return the User object associated with the given ID, or null if not found
     */
    public User searchUserById(int id) {
        return userDAO.getUserById(id);
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return a List of User objects, or an empty list if there are no users
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Deletes a user identified by their unique identifier (ID).
     *
     * @param id the ID of the user to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteUserById(int id) {
        return userDAO.deleteUser(id);
    }

    /**
     * Updates the username of a user identified by their ID.
     *
     * @param id       the ID of the user to update
     * @param userName the new username for the user
     * @return true if the update was successful, false otherwise
     */
    public boolean updateUserNameById(int id, String userName) {
        return userDAO.updateUser(
                new User(id, userName, searchUserById(id).getPassword(), searchUserById(id).getRole())
        );
    }

    /**
     * Updates the password of a user identified by their ID.
     *
     * @param id       the ID of the user to update
     * @param password the new password for the user
     * @return true if the update was successful, false otherwise
     */
    public boolean updatePasswordById(int id, String password) {
        return userDAO.updateUser(
                new User(id, searchUserById(id).getUsername(), password, searchUserById(id).getRole())
        );
    }

    /**
     * Updates the role of a user identified by their ID.
     *
     * @param id   the ID of the user to update
     * @param role the new role for the user
     * @return true if the update was successful, false otherwise
     */
    public boolean updateRoleById(int id, User.Role role) {
        return userDAO.updateUser(
                new User(id, searchUserById(id).getUsername(), searchUserById(id).getPassword(), role));
    }
}
