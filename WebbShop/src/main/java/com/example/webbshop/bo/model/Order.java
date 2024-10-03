package com.example.webbshop.bo.model;

import java.sql.Timestamp;

public class Order {
    private int orderId;          // order_id (Primary Key)
    private int userId;           // user_id (Foreign Key from Users table)
    private int totalPrice;       // total_price column
    private Timestamp orderDate;  // order_date column for when the order was placed
    public Order() {
    }
    public Order(int orderId, int userId, int totalPrice, Timestamp orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
}
