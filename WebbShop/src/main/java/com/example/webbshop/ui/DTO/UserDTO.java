package com.example.webbshop.ui.DTO;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for User.
 * This class is used to transfer user data between application layers.
 */
public class UserDTO {
    private int userId;
    private String username;
    private User.Role role;
    private List<ProductDTO> cart;

    /**
     * Parameterized constructor for creating a UserDTO instance with a cart.
     *
     * @param userId   the ID of the user
     * @param username the username of the user
     * @param role     the role of the user
     * @param cart     the list of products in the user's cart
     */
    public UserDTO(int userId, String username, User.Role role, List<ProductDTO> cart) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.cart = cart;
    }

    /**
     * Parameterized constructor for creating a UserDTO instance without a cart.
     *
     * @param userId   the ID of the user
     * @param username the username of the user
     * @param role     the role of the user
     */
    public UserDTO(int userId, String username, User.Role role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.cart = new ArrayList<>();
    }

    /**
     * Gets the user's cart.
     *
     * @return the list of products in the user's cart
     */
    public List<ProductDTO> getCart() {
        return cart;
    }

    /**
     * Sets the user's cart.
     *
     * @param cart the list of products to set in the user's cart
     */
    public void setCart(List<ProductDTO> cart) {
        this.cart = cart;
    }

    /**
     * Gets the user ID.
     *
     * @return the ID of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the ID of the user to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username of the user to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public User.Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role of the user to set
     */
    public void setRole(User.Role role) {
        this.role = role;
    }

    /**
     * Adds a product to the user's cart.
     *
     * @param product the product to add to the cart
     */
    public void addToCart(ProductDTO product) {
        this.cart.add(product);
    }

    /**
     * Removes a product from the user's cart.
     *
     * @param product the product to remove from the cart
     */
    public void removeFromCart(ProductDTO product) {
        this.cart.remove(product);
    }

    /**
     * Clears all products from the user's cart.
     */
    public void clearCart() {
        this.cart.clear();
    }

    /**
     * Calculates the total price of products in the user's cart.
     *
     * @return the total price of the products in the cart
     */
    public int getCartPrice() {
        int price = 0;
        for (ProductDTO product : this.cart) {
            price += product.getPrice();
        }
        return price;
    }
}
