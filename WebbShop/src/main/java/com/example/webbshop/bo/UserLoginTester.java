package com.example.webbshop.bo;

import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.model.User;

import java.util.List;

public class UserLoginTester {
    public static void main(String[] args) {
        // Create the UserService object
        UserService userService = new UserService();

        // Fetch all users
        List<User> users = userService.getAllUsers();
        userService.updateUserNameById(3, "Simon1");
        userService.updatePasswordById(3, "Godis5");
        userService.updateRoleById(4, User.Role.admin);
        // Print user details
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("List of users:");
            for (User user : users) {
                System.out.println("UserID: " + user.getUserID() + ", Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }
}
