package com.example.webbshop.ui.DTO;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;

import java.sql.Timestamp;
import java.util.List;


public class OrderDTO {
    private int orderId;
    private int totalPrice;
    private List<ProductDTO> orderItems;
    private boolean packed;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, int totalPrice, List<ProductDTO> orderItems) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
        this.packed = false;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ProductDTO> orderItems) {
        this.orderItems = orderItems;
    }
    public boolean isPacked() {
        return packed;
    }


}
