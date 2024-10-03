package com.example.webbshop.db.dao;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO Products (product_name, price, stock_quantity, created_at) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE Products SET product_name = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM Products WHERE product_id = ?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM Products WHERE product_id = ?";
    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM Products";

    public ProductDAO(){
        try {
            this.connection = DatabaseConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addProduct(Product product) {
        try (PreparedStatement stmt = connection.prepareStatement(ADD_PRODUCT_QUERY)) {
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getPrice());
            stmt.setInt(3, product.getStockQuantity());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a product
    public boolean updateProduct(Product product){
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getPrice());
            stmt.setInt(3, product.getStockQuantity());
            stmt.setInt(4, product.getProductId());

            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a product
    public boolean deleteProduct(int productId){
        boolean rowDeleted = false;
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            rowDeleted = stmt.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    // Method to retrieve a product by ID
    public Product getProductById(int productId){
        try (PreparedStatement stmt = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getInt("stock_quantity"),
                        rs.getTimestamp("created_at")
                );
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No product found
    }

    // Method to retrieve all products
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_ALL_PRODUCTS_QUERY);

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getInt("stock_quantity"),
                        rs.getTimestamp("created_at")
                ));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

