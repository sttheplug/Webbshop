package com.example.webbshop.ui;

import com.example.webbshop.bo.Service.CategoryService;
import com.example.webbshop.bo.Service.ProductService;
import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.Service.OrderService;
import com.example.webbshop.bo.model.Category;
import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import com.example.webbshop.ui.DTO.CategoryDTO;
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

@WebServlet({
        "/login", "/logout", "/products", "/cart", "/add-to-cart", "/checkout", "/admin",
        "/warehouse", "/pack-order", "/delete-order", "/remove-from-cart", "/adminOrders", "/adminProducts",
        "/adminUsers", "/add-product", "/edit-product", "/delete-product",
        "/add-user", "/edit-user", "/delete-user", "/update-product", "/update-user", "/adminCategories", "/add-category", "/delete-category"
})
public class Controller extends HttpServlet {
    private UserService userService;
    private ProductService productService;
    private OrderService orderService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        this.productService = new ProductService();
        this.orderService = new OrderService();
        this.categoryService = new CategoryService();
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
                handleProductsPage(request, response);
                break;
            case "/cart":
                handleCartPage(request, response);
                break;
            case "/checkout":
                handleCheckout(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            case "/adminOrders":
                if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.admin)) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                handleAdminOrdersPage(request, response);
                break;
            case "/adminProducts":
                if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.admin)) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                handleAdminProductsPage(request, response);
                break;
            case "/adminUsers":
                if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.admin)) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                handleAdminUsersPage(request, response);
                break;
            case "/adminCategories":
                if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.admin)) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                handleAdminCategoryPage(request, response);
                break;
            case "/warehouse":
                if (loggedInUser == null || !loggedInUser.getRole().equals(User.Role.warehouse_staff)) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                handleWarehousePage(request, response);
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
            case "/delete-order":
                handleDeleteOrder(request, response);
                break;
            case "/add-product":
                handleAddProduct(request, response);
                break;
            case "/edit-product":
                handleEditProduct(request, response);
                break;
            case "/delete-product":
                handleDeleteProduct(request, response);
                break;
            case "/add-user":
                handleAddUser(request, response);
                break;
            case "/edit-user":
                handleEditUser(request, response);
                break;
            case "/delete-user":
                handleDeleteUser(request, response);
                break;
            case "/update-product":
                handleUpdateProduct(request, response);
                break;
            case "/update-user":
                handleUpdateUser(request, response);
                break;
            case "/add-category":
                addCategory(request,response);
                break;
            case "/delete-category":
                deleteCategory(request,response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleAdminOrdersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orders = orderList.stream()
                .map(Order::toDTO)
                .collect(Collectors.toList());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("admin-order.jsp").forward(request, response);
    }

    private void handleAdminProductsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
        request.setAttribute("products", productDTOs);
        request.getRequestDispatcher("admin-products.jsp").forward(request, response);
    }

    private void handleAdminUsersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(User::toDTO)
                .collect(Collectors.toList());
        request.setAttribute("users", userDTOs);
        request.getRequestDispatcher("admin-users.jsp").forward(request, response);
    }

    private void handleAdminCategoryPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(Category::toDTO)
                .collect(Collectors.toList());
        request.setAttribute("categories", categoryDTOS);
        request.getRequestDispatcher("admin-category.jsp").forward(request, response);
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productName = request.getParameter("productName");
        int price = Integer.parseInt(request.getParameter("price"));
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
        String name = request.getParameter("categoryName");
        Category category = categoryService.getCategoryByName(name);
        productService.addProduct(productName, price,stockQuantity,category.getCategoryId());
        response.sendRedirect("adminProducts");
    }
    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required.");
            return;
        }
        try {
            int productId = Integer.parseInt(productIdStr);
            Product product = productService.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                return;
            }
            ProductDTO productDTO = Product.toDTO(product);
            List<Category> categoryList = categoryService.getAllCategories();
            List<CategoryDTO> categoryDTOS = categoryList.stream()
                    .map(Category::toDTO)
                    .collect(Collectors.toList());
            request.setAttribute("product", productDTO);
            request.setAttribute("categories", categoryDTOS);
            request.getRequestDispatcher("edit-product.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID.");
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
            String categoryName = request.getParameter("category");
            Category category = categoryService.getCategoryByName(categoryName);
            Product product = productService.getProductById(productId);
            product.setProductName(name);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            product.setCategoryId(category.getCategoryId());
            boolean isUpdated = productService.updateProduct(product);
            if (isUpdated) {
                response.sendRedirect("adminProducts");
            } else {
                request.setAttribute("errorMessage", "Failed to update the product.");
                request.getRequestDispatcher("edit-product.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the product.");
            try {
                request.getRequestDispatcher("edit-product.jsp").forward(request, response);
            } catch (ServletException se) {
                se.printStackTrace();
            }
        }
    }

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        productService.deleteProduct(productId);
        response.sendRedirect("adminProducts");
    }

    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        userService.registerUser(username,password, User.Role.valueOf(role));
        response.sendRedirect("adminUsers");
    }

    private void handleEditUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.searchUserById(userId);
        if (user != null) {
            UserDTO userDTO = User.toDTO(user);
            request.setAttribute("user", userDTO);
            try {
                request.getRequestDispatcher("edit-user.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect("admin-dashboard.jsp?error=Product not found");
        }
    }
    private void handleUpdateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String username = request.getParameter("username");
            String role = request.getParameter("role");
            userService.updateUserNameById(userId, username);
            userService.updateRoleById(userId, User.Role.valueOf(role));
            response.sendRedirect("adminUsers");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid user ID format.");
            request.getRequestDispatcher("admin-users.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid role provided.");
            request.getRequestDispatcher("admin-users.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the user.");
            request.getRequestDispatcher("admin-users.jsp").forward(request, response);
        }
    }


    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userService.deleteUserById(userId);
        response.sendRedirect("adminUsers");
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
                    response.sendRedirect("admin_staff.jsp");
                    break;
                case warehouse_staff:
                    response.sendRedirect("warehouse_staff.jsp");
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
                .map(product -> new ProductDTO(product.getProductId(), product.getProductName(),
                        product.getPrice(), product.getStockQuantity(), product.getCategoryName()))
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

    private void handleWarehousePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orders = orderList.stream()
                .map(Order::toDTO)
                .collect(Collectors.toList());

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    private void handlePackOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrderById(orderId);
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        if (order != null) {
            order.setPacked(true);
            orderService.updateOrder(order);
        }
        if(loggedInUser.getRole()==User.Role.admin) response.sendRedirect("adminOrders");
        else response.sendRedirect("warehouse");
    }

    private void handleDeleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        try {
            boolean isDeleted = orderService.deleteOrder(orderId);
            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Order deleted successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Order not found or couldn't be deleted.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while deleting the order.");
            e.printStackTrace();
        }
        response.sendRedirect("adminOrders");
    }
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoryName = request.getParameter("categoryName");
        try {
            categoryService.addCategory(categoryName);
            response.sendRedirect("adminCategories");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("adminCategories").forward(request, response);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        if (categoryService.deleteCategory(categoryId)) {
            response.sendRedirect("adminCategories");
        } else {
            request.setAttribute("errorMessage", "Failed to delete category.");
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}
