package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (user_id, total_price, order_date) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_ITEM_SQL = "INSERT INTO orderitems (order_id, product_id, quantity) VALUES (?, ?, ?)";

    // Method to retrieve an order by ID
    public Order getOrderById(int orderId) {
        Order order = null;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {

            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Get order details
                int userId = rs.getInt("user_id");
                int totalPrice = rs.getInt("total_price");
                Timestamp orderDate = rs.getTimestamp("order_date");

                // Retrieve order items for this order
                List<Product> orderItems = getOrderItemsByOrderId(orderId);

                // Create Order object
                order = new Order(orderId, userId, totalPrice, orderDate, orderItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // Method to retrieve all orders
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // Get order details
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                int totalPrice = rs.getInt("total_price");
                Timestamp orderDate = rs.getTimestamp("order_date");

                // Retrieve order items for this order
                List<Product> orderItems = getOrderItemsByOrderId(orderId);

                // Create Order object
                Order order = new Order(orderId, userId, totalPrice, orderDate, orderItems);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Method to insert a new order
    public Order addOrder(Order order) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getTotalPrice());
            preparedStatement.setTimestamp(3, order.getOrderDate());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedOrderId = generatedKeys.getInt(1);
                        order.setOrderId(generatedOrderId); // Set the generated order ID in the Order object
                        // Insert order items
                        for (Product product : order.getOrderItems()) {
                            insertOrderItem(generatedOrderId, product);
                        }
                    }
                }
                return order; // Return the inserted Order object with generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if insertion failed
    }

    // Helper method to insert order items into the orderitems table
    private void insertOrderItem(int orderId, Product product) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, product.getProductId()); // Assuming Product has a method getProductId()
            preparedStatement.setInt(3, product.getStockQuantity()); // Assuming Product has a method getQuantity()
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve order items for a given order ID
    private List<Product> getOrderItemsByOrderId(int orderId) {
        List<Product> orderItems = new ArrayList<>();
        String SELECT_ORDER_ITEMS_BY_ORDER_ID = "SELECT * FROM orderitems WHERE order_id = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER_ID)) {

            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int price = rs.getInt("price");
                int quantity = rs.getInt("stock_quantity");
                Timestamp createdAt = rs.getTimestamp("created_at");

                // Create Product object, you might need to adjust this part according to your Product class
                Product product = new Product(productId, productName, price, quantity, createdAt); // Assuming Product constructor with ID and quantity
                orderItems.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}
