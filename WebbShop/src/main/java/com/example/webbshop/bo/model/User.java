package com.example.webbshop.bo.model;

import com.example.webbshop.ui.DTO.ProductDTO;
import com.example.webbshop.ui.DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    // Fields that correspond to the columns in the database
    private int userID;                // Represents UserID in the database
    private String username;           // Represents username in the database
    private String password;           // Represents Password in the database
    private List<Product> cart;

    public enum Role {
        admin, customer, warehouse_staff,
    }
    private Role role;

    public User() {
    }

    public User(int userID, String username, String password, Role role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        cart = new ArrayList<>();
    }
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        cart = new ArrayList<>();
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

    public List<Product> getCart() {
        return new ArrayList<>(cart);
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public void addToCart(Product product) {
        this.cart.add(product);
    }

    public void removeFromCart(Product product){
        this.cart.remove(product);
    }
    public void clearCart() {
        this.cart.clear();
    }
    public int getCartPrice() {
        int price = 0;
        for(Product product : this.cart) {
           price += product.getPrice();
        }
        return price;
    }

    public static UserDTO toDTO(User user) {
        List<ProductDTO> cartDTOs = user.getCart().stream()
                .map(Product::toDTO)  // Use the toDTO method to convert each Product to ProductDTO
                .collect(Collectors.toList());  // Collect the result as a List<ProductDTO>

        return new UserDTO(user.getUserID(), user.getUsername(), user.getPassword(), user.getRole(), cartDTOs);
    }
    public static User convertToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null; // Handle the case where userDTO is null
        }
        User user = new User();
        user.setUserID(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Assuming User has an email field
        user.setRole(userDTO.getRole());
        if (userDTO.getCart() != null) {
            List<Product> cartItems = userDTO.getCart().stream()
                    .map(Product::convertToProduct) // Use a method to convert each ProductDTO to Product
                    .collect(Collectors.toList());
            user.setCart(cartItems); // Set the cart items in the User object
        }
        return user;
    }
}
