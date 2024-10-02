package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
    private Connection connection;

    // SQL Queries as static final strings
    private static final String ADD_CART_ITEM_QUERY = "INSERT INTO cartitems (cart_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_ITEM_QUERY = "UPDATE cartitems SET cart_id = ?, product_id = ?, quantity = ? WHERE cart_item_id = ?";
    private static final String DELETE_CART_ITEM_QUERY = "DELETE FROM cartitems WHERE cart_item_id = ?";
    private static final String GET_CART_ITEM_BY_ID_QUERY = "SELECT * FROM cartitems WHERE cart_item_id = ?";
    private static final String GET_ALL_CART_ITEMS_QUERY = "SELECT * FROM cartitems";

    // Constructor that initializes the database connection
    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a cart item
    public void addCartItem(CartItem cartItem) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(ADD_CART_ITEM_QUERY)) {
            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getProductId());
            stmt.setInt(3, cartItem.getQuantity());

            stmt.executeUpdate();
        }
    }

    // Method to update a cart item
    public void updateCartItem(CartItem cartItem) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_CART_ITEM_QUERY)) {
            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getProductId());
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setInt(4, cartItem.getCartItemId());

            stmt.executeUpdate();
        }
    }

    // Method to delete a cart item
    public void deleteCartItem(int cartItemId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_CART_ITEM_QUERY)) {
            stmt.setInt(1, cartItemId);
            stmt.executeUpdate();
        }
    }

    // Method to retrieve a cart item by ID
    public CartItem getCartItemById(int cartItemId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GET_CART_ITEM_BY_ID_QUERY)) {
            stmt.setInt(1, cartItemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CartItem(
                        rs.getInt("cart_item_id"),
                        rs.getInt("cart_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
            }
        }
        return null; // No cart item found
    }

    // Method to retrieve all cart items
    public List<CartItem> getAllCartItems() throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_ALL_CART_ITEMS_QUERY);

            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getInt("cart_item_id"),
                        rs.getInt("cart_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                ));
            }
        }
        return cartItems;
    }
}
