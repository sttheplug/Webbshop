package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.dao.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(String username, String password, User.Role role) {
        User user = new User(username, password, role);
        return userDAO.addUser(user);

    }

    public User loginUser(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public User searchUserById(int id) {
        return userDAO.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUserById(int id) {
        return userDAO.deleteUser(id);
    }

    private boolean updateUserNameById(int id, String userName) {
        return userDAO.updateUser(
                new User(id, userName, searchUserById(id).getPassword(), searchUserById(id).getRole())
        );
    }

    private boolean updatePasswordById(int id, String password) {
        return userDAO.updateUser(
                new User(id, searchUserById(id).getUsername(), password, searchUserById(id).getRole())
        );
    }

    private boolean updateRoleById(int id, User.Role role) {
        return userDAO.updateUser(
                new User(id, searchUserById(id).getUsername(), searchUserById(id).getPassword(), role));
    }
}
