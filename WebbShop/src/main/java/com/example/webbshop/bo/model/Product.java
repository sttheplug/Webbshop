package com.example.webbshop.bo.model;

import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.Timestamp;

/**
 * The Product class represents an item available for purchase in the webshop.
 * It holds details about the product's ID, name, price, stock quantity, and when the product was created.
 */
public class Product {

    /**
     * Unique identifier for the product.
     */
    private int productId;

    /**
     * The name of the product.
     */
    private String productName;

    /**
     * The price of the product.
     */
    private int price;

    /**
     * The quantity of the product available in stock.
     */
    private int stockQuantity;

    /**
     * Timestamp representing when the product was added to the system.
     */
    private Timestamp createdAt;

    /**
     * Default constructor for Product.
     * Creates an empty product object.
     */
    public Product() {
    }

    /**
     * Constructor to initialize all fields of the product.
     *
     * @param productId     the unique ID of the product
     * @param productName   the name of the product
     * @param price         the price of the product
     * @param stockQuantity the available stock quantity of the product
     * @param createdAt     the timestamp when the product was added
     */
    public Product(int productId, String productName, int price, int stockQuantity, Timestamp createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    /**
     * Constructor to create a new product without specifying an ID (used for adding new products).
     *
     * @param productName   the name of the product
     * @param price         the price of the product
     * @param stockQuantity the available stock quantity of the product
     * @param createdAt     the timestamp when the product was added
     */
    public Product(String productName, int price, int stockQuantity, Timestamp createdAt) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    /**
     * Gets the unique ID of the product.
     *
     * @return the product ID
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the unique ID of the product.
     *
     * @param productId the product ID
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the name of the product.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price of the product
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the quantity of the product available in stock.
     *
     * @return the stock quantity of the product
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the quantity of the product available in stock.
     *
     * @param stockQuantity the stock quantity of the product
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the timestamp when the product was added to the system.
     *
     * @return the creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp for when the product was added to the system.
     *
     * @param createdAt the creation timestamp
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Converts the Product object to a ProductDTO.
     * The ProductDTO is a simplified version of the Product object used to transfer data.
     *
     * @param product the Product object to convert
     * @return the converted ProductDTO
     */
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    /**
     * Converts a ProductDTO object back into a Product object.
     *
     * @param productDTO the ProductDTO to convert
     * @return the converted Product object or null if productDTO is null
     */
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
