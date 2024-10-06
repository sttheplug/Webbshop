package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing orders in the database.
 * Provides methods to create, read, update, and delete orders.
 */
public class OrderDAO {
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (user_id, total_price, order_date) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_ITEM_SQL = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER_SQL = "UPDATE orders SET packed = ? WHERE order_id = ?";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER_ID =
            "SELECT p.product_id, p.product_name, p.price, oi.quantity, p.created_at " +
                    "FROM order_items oi " +
                    "JOIN products p ON oi.product_id = p.product_id " +
                    "WHERE oi.order_id = ?";

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to be retrieved
     * @return the Order object if found, or null if not found
     */
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

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of Order objects, or an empty list if no orders are found
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS)) {

            ResultSet rs = preparedStatement.executeQuery(SELECT_ALL_ORDERS);

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                int totalPrice = rs.getInt("total_price");
                Timestamp orderDate = rs.getTimestamp("order_date");
                List<Product> orderItems = getOrderItemsByOrderId(orderId);
                Order order = new Order(orderId, userId, totalPrice, orderDate, orderItems);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Inserts a new order into the database.
     *
     * @param order the Order object to be added
     * @return the added Order object with its generated ID, or null if insertion failed
     */
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
                        order.setOrderId(generatedOrderId);

                        for (Product product : order.getOrderItems()) {
                            insertOrderItem(generatedOrderId, product, product.getStockQuantity());
                        }
                    }
                }
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper method to insert order items into the order_items table.
     *
     * @param orderId the ID of the order to which the items belong
     * @param product the Product object to be inserted
     * @param quantity the quantity of the product ordered
     */
    private void insertOrderItem(int orderId, Product product, int quantity) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, product.getProductId());
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves order items for a given order ID.
     *
     * @param orderId the ID of the order whose items are to be retrieved
     * @return a list of Product objects associated with the given order ID
     */
    private List<Product> getOrderItemsByOrderId(int orderId) {
        List<Product> orderItems = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER_ID)) {

            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Product product = new Product(productId, productName, price, quantity, createdAt);
                orderItems.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    /**
     * Updates the details of an existing order.
     *
     * @param order the Order object containing updated information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateOrder(Order order) {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_ORDER_SQL)) {
            stmt.setBoolean(1, order.isPacked());
            stmt.setInt(2, order.getOrderId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
