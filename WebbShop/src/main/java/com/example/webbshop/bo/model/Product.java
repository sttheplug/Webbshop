package com.example.webbshop.bo.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Product {
    private final int productId;
    private final String productName;
    private int price;
    private int stockQuantity;
    private Timestamp createdAt;

    public Product(int productId, String productName, int price, int stockQuantity, Timestamp createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
