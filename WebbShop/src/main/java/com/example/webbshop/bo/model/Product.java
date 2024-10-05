package com.example.webbshop.bo.model;

import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Product {
    private  int productId;
    private String productName;
    private int price;
    private int stockQuantity;
    private Timestamp createdAt;

    public Product(){

    }
    public Product(int productId, String productName, int price, int stockQuantity, Timestamp createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    public Product(String productName, int price, int stockQuantity, Timestamp createdAt) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
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

    public static ProductDTO toDTO(Product product){
        return new ProductDTO(product.getProductId(), product.getProductName(),
                product.getPrice(), product.getStockQuantity());
    }
    public static Product convertToProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        return product;
    }
}
