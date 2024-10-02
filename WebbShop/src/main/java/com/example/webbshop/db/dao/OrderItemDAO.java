package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private Connection connection;
    private static final String ADD_ORDER_ITEM_QUERY = "INSERT INTO orderitems (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER_ITEM_QUERY = "UPDATE orderitems SET order_id = ?, product_id = ?, quantity = ? WHERE order_item_id = ?";
    private static final String DELETE_ORDER_ITEM_QUERY = "DELETE FROM orderitems WHERE order_item_id = ?";
    private static final String GET_ORDER_ITEM_BY_ID_QUERY = "SELECT * FROM orderitems WHERE order_item_id = ?";
    private static final String GET_ALL_ORDER_ITEMS_QUERY = "SELECT * FROM orderitems";

    // Constructor that initializes the database connection
    public OrderItemDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add an order item
    public void addOrderItem(OrderItem orderItem) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(ADD_ORDER_ITEM_QUERY)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());

            stmt.executeUpdate();
        }
    }

    // Method to update an order item
    public void updateOrderItem(OrderItem orderItem) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ORDER_ITEM_QUERY)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setInt(4, orderItem.getOrderItemId());

            stmt.executeUpdate();
        }
    }

    // Method to delete an order item
    public void deleteOrderItem(int orderItemId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_ORDER_ITEM_QUERY)) {
            stmt.setInt(1, orderItemId);
            stmt.executeUpdate();
        }
    }

    // Method to retrieve an order item by ID
    public OrderItem getOrderItemById(int orderItemId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GET_ORDER_ITEM_BY_ID_QUERY)) {
            stmt.setInt(1, orderItemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
            }
        }
        return null; // No order item found
    }

    // Method to retrieve all order items
    public List<OrderItem> getAllOrderItems() throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_ALL_ORDER_ITEMS_QUERY);

            while (rs.next()) {
                orderItems.add(new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                ));
            }
        }
        return orderItems;
    }
}

