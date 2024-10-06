package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.dao.OrderDAO;
import com.example.webbshop.ui.DTO.OrderDTO;
import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling operations related to orders.
 */
public class OrderService {
    private final OrderDAO orderDAO;

    /**
     * Constructs an OrderService instance and initializes the OrderDAO.
     */
    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return the Order object associated with the given ID, or null if not found
     */
    public Order getOrderById(int orderId) {
        return this.orderDAO.getOrderById(orderId);
    }

    /**
     * Adds a new order for the given user with the specified order date.
     *
     * @param user      the user placing the order
     * @param orderDate the date of the order
     * @return the added Order object if successful; throws RuntimeException if the cart is empty
     */
    public Order addOrder(User user, Timestamp orderDate) {
        List<Product> items = user.getCart();
        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot place an order.");
        } else {
            int totalPrice = items.stream().mapToInt(Product::getPrice).sum();
            Order newOrder = new Order(user.getUserID(), totalPrice, orderDate, items);
            Order addedOrder = orderDAO.addOrder(newOrder);
            if (addedOrder != null) {
                user.clearCart();
            }
            return addedOrder;
        }
    }

    /**
     * Retrieves all orders.
     *
     * @return a List of all Order objects
     */
    public List<Order> getAllOrders() {
        return this.orderDAO.getAllOrders();
    }

    /**
     * Updates the given order.
     *
     * @param order the order to update
     * @return true if the order was successfully updated, false otherwise
     */
    public boolean updateOrder(Order order) {
        return this.orderDAO.updateOrder(order);
    }

    /**
     * Main method for testing the OrderService.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        List<Order> orders = orderService.getAllOrders();
        Product product = orders.get(0).getOrderItems().get(0);
        System.out.println(product.getProductId());
        System.out.println(product.getProductName());
        System.out.println(product.getStockQuantity());
        System.out.println(product.getPrice());

    }
}
