package com.example.webbshop.ui;

import com.example.webbshop.bo.Service.ProductService;
import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

// Map each page separately
@WebServlet({"/login", "/admin", "/customer", "/logout"})
public class Controller extends HttpServlet {
    private UserService userService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        switch (path) {
            case "/admin":
                if (loggedInUser != null && loggedInUser.getRole() == User.Role.admin) {
                    handleAdminPage(request, response);
                } else {
                    response.sendRedirect("login.jsp"); // Redirect if not admin or not logged in
                }
                break;

            case "/customer":
                if (loggedInUser != null && loggedInUser.getRole() == User.Role.customer) {
                    handleCustomerPage(request, response);
                } else {
                    response.sendRedirect("login.jsp"); // Redirect if not customer or not logged in
                }
                break;

            case "/logout":
                if (session != null) {
                    session.invalidate(); // Invalidate session on logout
                }
                response.sendRedirect("login.jsp");
                break;

            // Set the root path or other default paths to go to login.jsp
            case "/":
            default:
                response.sendRedirect("login.jsp");  // Redirect to login.jsp for any unknown or root path
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        // Handle post requests (for login)
        switch (path) {
            case "/login":
                handleLogin(request, response);  // Login action
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // Handle invalid post requests
                break;
        }
    }

    // Method to handle login logic
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.loginUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);

            // Redirect based on role
            if (user.getRole() == User.Role.admin) {
                response.sendRedirect("admin");
            } else if (user.getRole() == User.Role.customer) {
                response.sendRedirect("customer");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);  // Return to login page with error
        }
    }

    // Method to handle admin page functionality
    private void handleAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> allUsers = userService.getAllUsers();  // Get all users for admin
        request.setAttribute("users", allUsers);  // Pass users to JSP
        request.getRequestDispatcher("admin.jsp").forward(request, response);  // Render admin page
    }

    // Method to handle customer page functionality
    private void handleCustomerPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> items = productService.getAllProducts();  // Get all products for customer
        request.setAttribute("items", items);  // Pass products to JSP
        request.getRequestDispatcher("customer.jsp").forward(request, response);  // Render customer page
    } ////// ändra customer till products så ser du produkter??? varför
}