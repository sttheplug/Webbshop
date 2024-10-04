package com.example.webbshop.ui.DTO;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import java.util.List;

public class UserDTO {
    private int userId;
    private String username;
    private User.Role role;
    private List<Product> cart;

    // Constructor with parameters
    public UserDTO(int userId, String username, User.Role role, List<Product> cart) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.cart = cart;
    }

    // Getters and Setters

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

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

}