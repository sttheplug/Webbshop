package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Connection connection;
    private static final String ADD_CART_QUERY = "INSERT INTO Cart (user_id, created_at) VALUES (?, ?)";
    private static final String UPDATE_CART_QUERY = "UPDATE Cart SET user_id = ? WHERE cart_id = ?";
    private static final String DELETE_CART_QUERY = "DELETE FROM Cart WHERE cart_id = ?";
    private static final String GET_CART_BY_ID_QUERY = "SELECT * FROM Cart WHERE cart_id = ?";
    private static final String GET_ALL_CARTS_QUERY = "SELECT * FROM Cart";

    // Constructor that initializes the database connection
    public CartDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a cart
    public void addCart(Cart cart) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(ADD_CART_QUERY)) {
            stmt.setInt(1, cart.getUserId());
            stmt.setTimestamp(2, cart.getCreatedAt());

            stmt.executeUpdate();
        }
    }

    // Method to update a cart
    public void updateCart(Cart cart) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_CART_QUERY)) {
            stmt.setInt(1, cart.getUserId());
            stmt.setInt(2, cart.getCartId());

            stmt.executeUpdate();
        }
    }

    // Method to delete a cart
    public void deleteCart(int cartId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_CART_QUERY)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }

    // Method to retrieve a cart by ID
    public Cart getCartById(int cartId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GET_CART_BY_ID_QUERY)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cart(
                        rs.getInt("cart_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("created_at")
                );
            }
        }
        return null; // No cart found
    }

    // Method to retrieve all carts
    public List<Cart> getAllCarts() throws SQLException {
        List<Cart> carts = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_ALL_CARTS_QUERY);

            while (rs.next()) {
                carts.add(new Cart(
                        rs.getInt("cart_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return carts;
    }
}
