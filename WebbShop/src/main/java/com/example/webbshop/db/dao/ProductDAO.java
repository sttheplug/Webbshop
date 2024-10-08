package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing products in the database.
 * Provides methods to create, read, update, and delete products.
 */
public class ProductDAO {
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (product_name, price, stock_quantity, created_at, category_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET product_name = ?, price = ?, stock_quantity = ?, category_id = ? WHERE product_id = ?";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE product_id = ?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE product_id = ?";
    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products";

    /**
     * Adds a new product to the database.
     *
     * @param product the Product object to be added
     * @return the added Product object with its generated ID, or null if insertion failed
     */
    public boolean addProduct(Product product) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(ADD_PRODUCT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStockQuantity());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(5, product.getCategoryId());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Updates an existing product in the database.
     *
     * @param product the Product object containing updated information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateProduct(Product product) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getPrice());
            stmt.setInt(3, product.getStockQuantity());
            stmt.setInt(4, product.getCategoryId());
            stmt.setInt(5, product.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a product from the database by its ID.
     *
     * @param productId the ID of the product to be deleted
     * @return true if the product was deleted, false otherwise
     */
    public boolean deleteProduct(int productId) {
        boolean rowDeleted = false;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            stmt.setInt(1, productId);
            rowDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    /**
     * Retrieves a product from the database by its ID.
     *
     * @param productId the ID of the product to be retrieved
     * @return the Product object if found, or null if not found
     */
    public Product getProductById(int productId) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getInt("stock_quantity"),
                        rs.getInt("category_id"),
                        rs.getTimestamp("created_at")
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a list of Product objects, or an empty list if no products are found
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_ALL_PRODUCTS_QUERY)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getInt("stock_quantity"),
                        rs.getInt("category_id"),
                        rs.getTimestamp("created_at")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        System.out.println(productDAO.getAllProducts());
    }
}
