package com.example.webbshop.bo.model;
public class User {
    // Fields that correspond to the columns in the database
    private int userID;                // Represents UserID in the database
    private String username;           // Represents username in the database
    private String password;           // Represents Password in the database

    public enum Role {
        admin, customer, warehouse_staff,
    }
    private Role role;
    public User() {
    }

    // Parameterized constructor for creating a User object easily
    public User(int userID, String username, String password, Role role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Optional: Override toString() method for debugging purposes
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
