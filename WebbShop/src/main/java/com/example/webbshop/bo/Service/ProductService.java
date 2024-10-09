package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.dao.ProductDAO;
import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Service class responsible for handling operations related to products.
 */
public class ProductService {

    private final ProductDAO productDAO;

    /**
     * Constructs a ProductService instance and initializes the ProductDAO.
     */
    public ProductService() {
        productDAO = new ProductDAO();
    }

    /**
     * Adds a new product to the system.
     *
     * @param productName   the name of the product
     * @param price         the price of the product
     * @param stockQuantity the available quantity of the product in stock
     * @return the added Product object
     */
    public boolean addProduct(String productName, int price, int stockQuantity, int categoryId) {
        Product product = new Product(productName, price, stockQuantity,categoryId, null);
        return productDAO.addProduct(product);
    }

    /**
     * Updates the price of a product identified by its ID.
     *
     * @param productId the ID of the product to update
     * @param price     the new price for the product
     * @return true if the update was successful, false otherwise
     */
    public boolean updatePriceById(int productId, int price) {
        Product product = productDAO.getProductById(productId);
        return productDAO.updateProduct(
                new Product(productId, product.getProductName(), price, product.getStockQuantity(), product.getCategoryId(), product.getCreatedAt())
        );
    }

    /**
     * Updates the stock quantity of a product identified by its ID.
     *
     * @param productId the ID of the product to update
     * @param quantity  the new quantity for the product
     * @return true if the update was successful, false otherwise
     */
    public boolean updateQuantityById(int productId, int quantity) {
        Product product = productDAO.getProductById(productId);
        return productDAO.updateProduct(
                new Product(productId, product.getProductName(), product.getPrice(), quantity,product.getCategoryId(), product.getCreatedAt())
        );
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the Product object associated with the given ID, or null if not found
     */
    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    /**
     * Deletes a product identified by its ID.
     *
     * @param productId the ID of the product to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }

    /**
     * Retrieves all products from the system.
     *
     * @return a List of all Product objects
     */
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public boolean updateProduct(Product product){
        return productDAO.updateProduct(product);
    }
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        productService.deleteProduct(3);
    }
}
