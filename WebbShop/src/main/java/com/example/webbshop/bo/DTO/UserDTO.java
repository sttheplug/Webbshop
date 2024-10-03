package com.example.webbshop.bo.DTO;

import com.example.webbshop.bo.DTO.*;
import com.example.webbshop.bo.model.User;

public class UserDTO {
    private int userId;
    private String username;
    private User.Role role;

    // Constructor with parameters
    public UserDTO(int userId, String username, User.Role role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    // Method to convert a User to UserDTO
    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getUserID(), user.getUsername(), user.getRole());
    }
}