<%@ page import="java.util.List" %>
<%@ page import="com.example.webbshop.ui.DTO.ProductDTO" %>
<html>
<head>
    <title>Admin - Manage Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #2d3436;
            margin-bottom: 30px;
        }

        h2 {
            margin-top: 40px;
            color: #2d3436;
        }

        /* Container for the product management section */
        .container {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        /* Form styles */
        form {
            margin: 20px 0;
            display: flex;
            flex-direction: column;
            max-width: 400px;
        }

        label {
            margin: 5px 0;
            color: #636e72;
        }

        input[type="text"],
        input[type="number"] {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        input[type="submit"] {
            background-color: #00b894;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #00966e;
        }

        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #00b894;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        /* Delete button styles */
        .delete-btn {
            background-color: #dc3545;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .delete-btn:hover {
            background-color: #c82333;
        }

        /* Edit button styles */
        .edit-btn {
            background-color: #ffc107; /* Yellow background for edit button */
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-right: 5px; /* Space between buttons */
        }

        .edit-btn:hover {
            background-color: #e0a800; /* Darker yellow on hover */
        }

        /* Back button styles */
        .back-button {
            background-color: #dfe6e9;
            color: #2d3436;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            display: inline-block;
            margin: 20px auto;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #b2bec3;
        }

        /* No products message */
        .no-products {
            text-align: center;
            color: #636e72;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Manage Products</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <p style="color: red; text-align: center;"><%= errorMessage %></p>
    <% } %>

    <h2>Add New Product</h2>
    <form action="add-product" method="post">
        <label for="productName">Product Name:</label>
        <input type="text" name="productName" id="productName" required>

        <label for="price">Price:</label>
        <input type="number" name="price" id="price" required>

        <label for="stockQuantity">Stock Quantity:</label>
        <input type="number" name="stockQuantity" id="stockQuantity" required>

        <label for="categoryName">Category Name:</label>
        <input type="text" name="categoryName" id="categoryName" required>

        <input type="submit" value="Add Product">
    </form>

    <!-- Table to display all products -->
    <h2>Existing Products</h2>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Stock Quantity</th>
            <th>Category Name</th>
            <th>Actions</th>
        </tr>
        <%
            // Fetch the list of products from the request
            List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products");

            if (products != null && !products.isEmpty()) {
                for (ProductDTO product : products) {
        %>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getProductName() %></td>
            <td>$<%= product.getPrice() %></td>
            <td><%= product.getStockQuantity() %></td>
            <td><%= product.getCategoryName() %></td> <!-- Displaying Category Name -->
            <td>
                <form action="edit-product" method="post" style="display:inline;">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="submit" class="edit-btn" value="Edit">
                </form>
                <form action="delete-product" method="post" style="display:inline;">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="submit" class="delete-btn" value="Delete" onclick="return confirm('Are you sure you want to delete this product?')">
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6" class="no-products">No products available.</td>
        </tr>
        <% } %>
    </table>

    <!-- Back to Admin Dashboard Button -->
    <a href="admin-dashboard.jsp" class="back-button">Back to Dashboard</a>
</div>
</body>
</html>
