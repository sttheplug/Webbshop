package com.example.webbshop.ui;

import com.example.webbshop.bo.Service.ProductService;
import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.Service.OrderService;
import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

// Servlet mapped to multiple URL patterns
@WebServlet({"/login", "/logout", "/products", "/cart", "/add-to-cart", "/checkout", "/admin", "/warehouse", "/pack-order", "/remove-from-cart"})
public class Controller extends HttpServlet {
    private UserService userService;
    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        // Initialize service layers
        this.userService = new UserService();
        this.productService = new ProductService();
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        HttpSession session = request.getSession(false);
        User loggedInUser = session != null ? (User) session.getAttribute("loggedInUser") : null;

        switch (path) {
            case "/login":
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;

            case "/products":
                if (loggedInUser != null) {
                    handleProductsPage(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            case "/cart":
                if (loggedInUser != null) {
                    handleCartPage(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            case "/checkout":
                if (loggedInUser != null) {
                    handleCheckout(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            case "/logout":
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("login.jsp");
                break;

            case "/admin":
                if (loggedInUser != null && loggedInUser.getRole() == User.Role.admin) {
                    handleAdminPage(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            case "/warehouse":
                if (loggedInUser != null && loggedInUser.getRole() == User.Role.warehouse_staff) {
                    handleWarehousePage(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            default:
                response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;

            case "/add-to-cart":
                handleAddToCart(request, response);
                break;

            case "/checkout":
                handleCheckout(request, response);
                break;

            case "/remove-from-cart":  // Added case for removing items from the cart
                handleRemoveFromCart(request, response);
                break;

            case "/pack-order":
                handlePackOrder(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Handle user login
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.loginUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);

            if (user.getRole() == User.Role.admin) {
                response.sendRedirect("admin.jsp");
            } else if (user.getRole() == User.Role.warehouse_staff) {
                response.sendRedirect("warehouse");
            } else {
                response.sendRedirect("customer.jsp");  // Redirect to the welcome page for customers
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Display the products page
    private void handleProductsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    // Handle displaying the shopping cart
    private void handleCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Product> cartItems = loggedInUser.getCart();
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    // Add product to shopping cart
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = productService.getProductById(productId);
        if (product != null && product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productService.updateQuantityById(productId, product.getStockQuantity());
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("loggedInUser");
            if (user != null) {
                for (int i = 0; i < quantity; i++) {
                    user.addToCart(product);
                }
                response.sendRedirect("cart");
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.getWriter().println("Product is out of stock or quantity exceeds available stock.");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));

        HttpSession session = request.getSession(false);
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<Product> cart = loggedInUser.getCart();

            // Find the product to remove
            Product productToRemove = null;
            for (Product product : cart) {
                if (product.getProductId() == productId) {
                    productToRemove = product;
                    break;
                }
            }
            if (productToRemove != null) {
                loggedInUser.removeFromCart(productToRemove);
                productToRemove.setStockQuantity(productToRemove.getStockQuantity() + 1);
                productService.updateQuantityById(productId, productToRemove.getStockQuantity());
            }
        }
        response.sendRedirect("products");
    }


    // Handle checkout
    private void handleCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        Order success = orderService.addOrder(loggedInUser, new Timestamp(System.currentTimeMillis()));

        if (success != null) {
            loggedInUser.getCart().clear();  // Clear the cart after successful checkout
            request.setAttribute("message", "Order placed successfully!");
        } else {
            request.setAttribute("errorMessage", "Failed to place the order.");
        }

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    // Handle the admin page (for product management, etc.)
    private void handleAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> allOrders = orderService.getOrders();
        request.setAttribute("orders", allOrders);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    // Handle the warehouse page where orders can be viewed and packed by warehouse staff
    private void handleWarehousePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderService.getOrders();  // Retrieve all orders
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    // Handle packing an order
    private void handlePackOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            order.setPacked(true);  // Set the order as packed
            orderService.updateOrder(order);  // Update the order in the database
            response.sendRedirect("warehouse");  // Refresh the warehouse page
        } else {
            response.sendRedirect("warehouse");
        }
    }
}
