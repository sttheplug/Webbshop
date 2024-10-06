package com.example.webbshop.bo.model;

import com.example.webbshop.bo.Service.OrderService;
import com.example.webbshop.ui.DTO.OrderDTO;
import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Order class represents a customer's order in the webshop system.
 * It holds information about the order's ID, the user who placed it, total price, date,
 * a list of ordered products, and whether the order has been packed.
 */
public class Order {

    /**
     * Unique identifier for the order.
     */
    private int orderId;

    /**
     * ID of the user who placed the order.
     */
    private int userId;

    /**
     * The total price of the order.
     */
    private int totalPrice;

    /**
     * The timestamp indicating when the order was placed.
     */
    private Timestamp orderDate;

    /**
     * List of products included in this order.
     */
    private List<Product> orderItems;

    /**
     * Boolean flag to indicate if the order has been packed.
     */
    private boolean packed;

    /**
     * Default constructor for Order.
     * Creates an empty order object.
     */
    public Order() {
    }

    /**
     * Constructor to initialize all fields of the order.
     *
     * @param orderId    the unique ID of the order
     * @param userId     the ID of the user who placed the order
     * @param totalPrice the total price of the order
     * @param orderDate  the date and time the order was placed
     * @param items      the list of products in the order
     */
    public Order(int orderId, int userId, int totalPrice, Timestamp orderDate, List<Product> items, boolean packed) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderItems = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        this.packed = packed;
    }

    /**
     * Constructor used when creating a new order that doesn't yet have an order ID.
     *
     * @param userId     the ID of the user who placed the order
     * @param totalPrice the total price of the order
     * @param orderDate  the date and time the order was placed
     * @param items      the list of products in the order
     */
    public Order(int userId, int totalPrice, Timestamp orderDate, List<Product> items) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderItems = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        packed = false;
    }


    /**
     * Gets the unique ID of the order.
     *
     * @return the order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique ID of the order.
     *
     * @param orderId the order ID
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the ID of the user who placed the order.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who placed the order.
     *
     * @param userId the user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price of the order
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the order.
     *
     * @param totalPrice the total price of the order
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the date and time when the order was placed.
     *
     * @return the timestamp of the order
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date and time when the order was placed.
     *
     * @param orderDate the timestamp of the order
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the list of products included in this order.
     *
     * @return the list of products in the order
     */
    public List<Product> getOrderItems() {
        return new ArrayList<>(orderItems);
    }
    public void addToOrderItems(Product p) {
        orderItems.add(p);
    }


    /**
     * Gets whether the order has been packed.
     *
     * @return true if the order is packed, false otherwise
     */
    public boolean isPacked() {
        return packed;
    }

    /**
     * Sets whether the order has been packed.
     *
     * @param packed true if the order is packed, false otherwise
     */
    public void setPacked(boolean packed) {
        this.packed = packed;
    }

    /**
     * Converts the Order object to an OrderDTO.
     * The OrderDTO is a simplified version of the Order object used to transfer data.
     *
     * @param order the Order object to convert
     * @return the converted OrderDTO
     */
    public static OrderDTO toDTO(Order order) {
        if(order==null) throw new NullPointerException();
        List<ProductDTO> newDTOList = new ArrayList<>();
        for (Product product : order.getOrderItems()) {
            newDTOList.add(new ProductDTO(product.getProductId(), product.getProductName(), product.getPrice(), product.getStockQuantity()));
        }
        return new OrderDTO(order.getOrderId(), order.getUserId(), order.getTotalPrice(),
                order.getOrderDate(), newDTOList, order.packed);
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orders = orderList.stream()
                .map(Order::toDTO)
                .collect(Collectors.toList());
        System.out.println(orders.get(0).getOrderId());
    }
}
