package com.example.webbshop.ui;

import com.example.webbshop.bo.Service.ProductService;
import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.Service.OrderService;
import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import com.example.webbshop.ui.DTO.OrderDTO;
import com.example.webbshop.ui.DTO.ProductDTO;
import com.example.webbshop.ui.DTO.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

// Servlet mapped to multiple URL patterns
@WebServlet({"/login", "/logout", "/products", "/cart", "/add-to-cart", "/checkout", "/admin", "/warehouse", "/pack-order", "/remove-from-cart"})
public class Controller extends HttpServlet {
    private UserService userService;
    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        this.productService = new ProductService();
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        UserDTO loggedInUser = session != null ? (UserDTO) session.getAttribute("loggedInUser") : null;

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
            case "/remove-from-cart":
                handleRemoveFromCart(request, response);
                break;
            case "/pack-order":
                handlePackOrder(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.loginUser(username, password);
        if (user != null) {
            UserDTO userDTO = User.toDTO(user);
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", userDTO);
            switch (user.getRole()) {
                case admin:
                    response.sendRedirect("admin.jsp");
                    break;
                case warehouse_staff:
                    response.sendRedirect("warehouse.jsp");
                    break;
                default:
                    response.sendRedirect("customer.jsp");
                    break;
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handleProductsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
        request.setAttribute("products", productDTOs);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    private void handleCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        List<ProductDTO> cartItems = loggedInUser.getCart().stream()
                .map(product -> new ProductDTO(product.getProductId(), product.getProductName(), product.getPrice(), product.getStockQuantity()))
                .collect(Collectors.toList());
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product = productService.getProductById(productId);

        if (product != null && product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productService.updateQuantityById(productId, product.getStockQuantity());

            HttpSession session = request.getSession(false);
            UserDTO userDTO = (UserDTO) session.getAttribute("loggedInUser");

            if (userDTO != null) {
                ProductDTO productDTO = Product.toDTO(product);
                for (int i = 0; i < quantity; i++) {
                    userDTO.addToCart(productDTO);
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
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<ProductDTO> cart = loggedInUser.getCart();
            ProductDTO productToRemove = cart.stream()
                    .filter(productDTO -> productDTO.getProductId() == productId)
                    .findFirst()
                    .orElse(null);
            if (productToRemove != null) {
                loggedInUser.removeFromCart(productToRemove);
                Product productEntity = productService.getProductById(productId);
                if (productEntity != null) {
                    productEntity.setStockQuantity(productEntity.getStockQuantity() + 1);
                    productService.updateQuantityById(productId, productEntity.getStockQuantity());
                }
            }
        }
        response.sendRedirect("products");
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO dto = (UserDTO) session.getAttribute("loggedInUser");
        User user = User.convertToUser(dto);
        Order order = orderService.addOrder(user, new Timestamp(System.currentTimeMillis()));
        if (order != null) {
            OrderDTO orderDTO = Order.toDTO(order);
            user.getCart().clear();
            request.setAttribute("order", orderDTO);
            request.setAttribute("message", "Order placed successfully!");
        } else {
            request.setAttribute("errorMessage", "Failed to place the order.");
        }
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    private void handleAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orders = orderList.stream()
                .map(Order::toDTO)
                .collect(Collectors.toList());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    private void handleWarehousePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orders = orderList.stream()
                .map(Order::toDTO)
                .collect(Collectors.toList());

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    private void handlePackOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            order.setPacked(true);
            orderService.updateOrder(order);
        }
        response.sendRedirect("warehouse");
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}
