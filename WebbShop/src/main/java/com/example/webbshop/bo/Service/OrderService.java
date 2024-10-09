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
     * Deletes an order by its ID.
     * This method handles the business logic related to the deletion of an order, such as checking if the order exists.
     *
     * @param orderId the ID of the order to be deleted
     * @return true if the order was successfully deleted, false otherwise
     */
    public boolean deleteOrder(int orderId) {
        if (orderDAO.getOrderById(orderId) == null) {
            return false;
        }
        return orderDAO.deleteOrder(orderId);
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        System.out.println(orderService.getAllOrders().get(0).getOrderId());
    }
}
