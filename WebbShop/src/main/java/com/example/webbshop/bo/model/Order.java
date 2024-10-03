package com.example.webbshop.bo.model;

import java.sql.Timestamp;

public class Order {
    private int orderId;          // order_id (Primary Key)
    private int userId;           // user_id (Foreign Key from Users table)
    private Timestamp orderDate;  // order_date column for when the order was placed
    private String status;        // status column to represent the current order status (e.g., 'pending', 'completed')
    public Order() {
    }
    public Order(int orderId, int userId, Timestamp orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
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

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
