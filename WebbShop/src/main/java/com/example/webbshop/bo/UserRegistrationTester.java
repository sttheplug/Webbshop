package com.example.webbshop.bo;

import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.model.User;

import java.util.List;

public class UserRegistrationTester {
    public static void main(String[] args) {
        // Create the UserService object
        UserService userService = new UserService();

        // Test Case 1: Register a new user
        System.out.println("Test Case 1: Registering a new user...");
        User user = new User("Williamss", "testPassword123", User.Role.customer);
        User createdUser = userService.registerUser(user.getUsername(), user.getPassword(), user.getRole());
        System.out.println(createdUser.getUserID());
    }
}
