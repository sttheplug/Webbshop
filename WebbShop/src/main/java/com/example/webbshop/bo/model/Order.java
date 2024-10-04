package com.example.webbshop.bo.model;

import java.io.LineNumberInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;          // order_id (Primary Key)
    private int userId;           // user_id (Foreign Key from Users table)
    private int totalPrice;       // total_price column
    private Timestamp orderDate;  // order_date column for when the order was placed
    private List<Product> orderItems;
    public Order() {
    }
    public Order(int orderId, int userId, int totalPrice, Timestamp orderDate, List<Product> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        orderItems = new ArrayList<>(items);
    }

    public Order(int userId, int totalPrice, Timestamp orderDate, List<Product> items) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        orderItems = new ArrayList<>(items);
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
    public List<Product> getOrderItems(){
        return new ArrayList<>(orderItems);
    }
}
