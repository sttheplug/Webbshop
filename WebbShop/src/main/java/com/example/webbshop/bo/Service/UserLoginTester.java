package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.User;

public class UserLoginTester {
    public static void main(String[] args) {
        // Create an instance of UserService
        UserService userService = new UserService();

        // Define the username and password to test with
        String testUsername = "john_doe";
        String testPassword = "password123";

        // Attempt to log in with the provided username and password
        User loggedInUser = userService.loginUser(testUsername, testPassword);

        // Check if the login was successful
        if (loggedInUser != null) {
            System.out.println("Login successful!");
            System.out.println("User ID: " + loggedInUser.getUserID());
            System.out.println("Username: " + loggedInUser.getUsername());
            System.out.println("Role: " + loggedInUser.getRole());
        } else {
            System.out.println("Login failed! Invalid username or password.");
        }
    }
}
