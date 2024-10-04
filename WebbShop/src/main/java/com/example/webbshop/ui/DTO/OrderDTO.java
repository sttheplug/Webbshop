package com.example.webbshop.ui.DTO;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;

import java.sql.Timestamp;
import java.util.List;


public class OrderDTO {
    private int orderId;
    private int userId;
    private int totalPrice;
    private Timestamp orderDate;
    private List<ProductDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, int userId, int totalPrice, Timestamp orderDate, List<ProductDTO> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
    }

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

    public List<ProductDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ProductDTO> orderItems) {
        this.orderItems = orderItems;
    }

}
