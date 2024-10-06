package com.example.webbshop.ui.DTO;

/**
 * Data Transfer Object (DTO) for Product.
 * This class is used to transfer product data between the application layers.
 */
public class ProductDTO {
    private int productId;
    private String productName;
    private int price;
    private int stockQuantity;

    /**
     * Parameterized constructor for creating a ProductDTO instance.
     *
     * @param productId      the ID of the product
     * @param productName    the name of the product
     * @param price          the price of the product
     * @param stockQuantity  the quantity of the product in stock
     */
    public ProductDTO(int productId, String productName, int price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the product ID.
     *
     * @return the ID of the product
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId the ID of the product to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the product name.
     *
     * @return the name of the product
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName the name of the product to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the product price.
     *
     * @return the price of the product
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the price of the product to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the stock quantity of the product.
     *
     * @return the quantity of the product in stock
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the stock quantity of the product.
     *
     * @param stockQuantity the quantity of the product to set
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
