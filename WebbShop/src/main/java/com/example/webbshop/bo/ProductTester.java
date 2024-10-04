package com.example.webbshop.bo;

import com.example.webbshop.bo.Service.ProductService;
import com.example.webbshop.bo.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductTester {
    public static void main(String[] args) {
        ProductService productService = new ProductService();

        // Test Case 1: Add a new product
        System.out.println("Test Case 1: Adding a new product...");
        Product newProduct = new Product("Test Product", 100, 10, null);
        Product CreatedProduct = productService.addProduct(newProduct.getProductName(), newProduct.getPrice(), newProduct.getStockQuantity());
        System.out.println(CreatedProduct.getProductId());
    }
}
