package com.example.webbshop.bo.model;

import com.example.webbshop.ui.DTO.ProductDTO;
import com.example.webbshop.ui.DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a user in the webshop system.
 * A user has a unique ID, a username, a password, and a role (admin, customer, or warehouse staff).
 * Users can also have a shopping cart containing products.
 */
public class User {

    /**
     * Unique identifier for the user.
     */
    private int userID;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * A list representing the user's shopping cart, which contains products.
     */
    private List<Product> cart;

    /**
     * Enum representing the role of the user (admin, customer, warehouse staff).
     */
    public enum Role {
        admin, customer, warehouse_staff,
    }

    /**
     * The role assigned to the user.
     */
    private Role role;

    /**
     * Default constructor for User.
     * Initializes an empty user with no assigned role and an empty cart.
     */
    public User() {
    }

    /**
     * Constructor to initialize all fields of the User object.
     *
     * @param userID   the unique identifier for the user
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role assigned to the user (admin, customer, warehouse staff)
     */
    public User(int userID, String username, String password, Role role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.cart = new ArrayList<>();
    }

    /**
     * Constructor for creating a new user without specifying a userID.
     * This is used for new users where the userID is assigned later.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role assigned to the user (admin, customer, warehouse staff)
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.cart = new ArrayList<>();
    }


    /**
     * Gets the unique identifier for the user.
     *
     * @return the user's ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userID the user's ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the username of the user.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role assigned to the user.
     *
     * @return the user's role (admin, customer, or warehouse staff)
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role assigned to the user.
     *
     * @param role the user's role (admin, customer, or warehouse staff)
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the user's shopping cart as a list of products.
     *
     * @return a new list of the products in the user's cart
     */
    public List<Product> getCart() {
        return new ArrayList<>(cart);
    }

    /**
     * Sets the user's shopping cart.
     *
     * @param cart the list of products to set as the user's cart
     */
    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    /**
     * Adds a product to the user's shopping cart.
     *
     * @param product the product to be added to the cart
     */
    public void addToCart(Product product) {
        this.cart.add(product);
    }

    /**
     * Removes a product from the user's shopping cart.
     *
     * @param product the product to be removed from the cart
     */
    public void removeFromCart(Product product) {
        this.cart.remove(product);
    }

    /**
     * Clears all products from the user's shopping cart.
     */
    public void clearCart() {
        this.cart.clear();
    }

    /**
     * Calculates the total price of all the products in the user's shopping cart.
     *
     * @return the total price of the cart
     */
    public int getCartPrice() {
        int price = 0;
        for (Product product : this.cart) {
            price += product.getPrice();
        }
        return price;
    }

    /**
     * Converts the User object to a UserDTO object.
     * This is used to transfer data between different layers of the application.
     *
     * @param user the User object to convert
     * @return the converted UserDTO object
     */
    public static UserDTO toDTO(User user) {
        List<ProductDTO> cartDTOs = user.getCart().stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());

        return new UserDTO(user.getUserID(), user.getUsername(), user.getRole(), cartDTOs);
    }

    /**
     * Converts a UserDTO object to a User object.
     * This is used when receiving data from the UI or other layers of the application.
     *
     * @param userDTO the UserDTO to convert
     * @return the converted User object
     */
    public static User convertToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUserID(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        if (userDTO.getCart() != null) {
            List<Product> cartItems = userDTO.getCart().stream()
                    .map(Product::convertToProduct)
                    .collect(Collectors.toList());
            user.setCart(cartItems);
        }
        return user;
    }
}
