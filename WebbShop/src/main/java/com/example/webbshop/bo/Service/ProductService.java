package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.dao.ProductDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(){
        productDAO = new ProductDAO();
    }

    public void addProduct(String productName, int price, int stockQuantity){
        Product product = new Product(0, productName, price, stockQuantity, null);
        productDAO.addProduct(product);
    }

    // Method to update product details
    public boolean updatePriceById(int productId, int price){
        Product product = productDAO.getProductById(productId);
        return productDAO.updateProduct(
                new Product(productId, product.getProductName(), price, product.getStockQuantity(), product.getCreatedAt())
        );
    }
    public boolean updateQuantityById(int productId, int quantity){
        Product product = productDAO.getProductById(productId);
        return productDAO.updateProduct(
                new Product(productId, product.getProductName(), product.getPrice(), quantity, product.getCreatedAt())
        );
    }
    public Product getProductById(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }
    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }

    // Method to get all products
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public static void main(String[] args) {
        ProductService productService = new ProductService();
        productService.updatePriceById(2, 130);
    }
}

