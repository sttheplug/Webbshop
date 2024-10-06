package com.example.webbshop.ui.DTO;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private int userId;
    private String username;
    private User.Role role;
    private List<ProductDTO> cart;

    // Constructor with parameters
    public UserDTO(int userId, String username, User.Role role, List<ProductDTO> cart) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.cart = cart;
    }

    public UserDTO(int userId, String username, User.Role role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.cart = new ArrayList<>();
    }
    // Getters and Setters

    public List<ProductDTO> getCart() {
        return cart;
    }

    public void setCart(List<ProductDTO> cart) {
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
    public void addToCart(ProductDTO product) {
        this.cart.add(product);
    }

    public void removeFromCart(ProductDTO product){
        this.cart.remove(product);
    }
    public void clearCart() {
        this.cart.clear();
    }
    public int getCartPrice() {
        int price = 0;
        for(ProductDTO product : this.cart) {
            price += product.getPrice();
        }
        return price;
    }

}