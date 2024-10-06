package com.example.webbshop.ui.DTO;

import com.example.webbshop.bo.model.Product;

import java.sql.Timestamp;
import java.util.List;

/**
 * Data Transfer Object (DTO) for Order.
 * This class is used to transfer order data between the application layers.
 */
public class OrderDTO {
    private int orderId;
    private int userId;
    private int totalPrice;
    private Timestamp orderDate;
    private List<ProductDTO> orderItems;
    private boolean packed;

    /**
     * Default constructor.
     */
    public OrderDTO() {
    }

    /**
     * Parameterized constructor for creating an OrderDTO instance.
     *
     * @param orderId    the ID of the order
     * @param userId     the ID of the user associated with the order
     * @param totalPrice  the total price of the order
     * @param orderDate   the date the order was placed
     * @param orderItems  the list of products in the order
     */
    public OrderDTO(int orderId, int userId, int totalPrice, Timestamp orderDate, List<ProductDTO> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.packed = false;
    }

    /**
     * Gets the order ID.
     *
     * @return the ID of the order
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID.
     *
     * @param orderId the ID of the order to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the user ID associated with the order.
     *
     * @return the ID of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the order.
     *
     * @param userId the ID of the user to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the order.
     *
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the date the order was placed.
     *
     * @return the order date
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date the order was placed.
     *
     * @param orderDate the order date to set
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the list of products in the order.
     *
     * @return the list of order items
     */
    public List<ProductDTO> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets the list of products in the order.
     *
     * @param orderItems the list of order items to set
     */
    public void setOrderItems(List<ProductDTO> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Checks if the order is packed.
     *
     * @return true if the order is packed, false otherwise
     */
    public boolean isPacked() {
        return packed;
    }
}
