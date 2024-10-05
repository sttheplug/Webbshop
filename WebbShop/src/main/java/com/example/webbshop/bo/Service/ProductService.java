package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.dao.ProductDAO;
import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(){
        productDAO = new ProductDAO();
    }

    public Product addProduct(String productName, int price, int stockQuantity){
        Product product = new Product(productName, price, stockQuantity, null);
        return productDAO.addProduct(product);
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
    public Product getProductById(int productId){
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

