<%@ page import="com.example.webbshop.ui.DTO.ProductDTO" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Admin - Manage Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f7;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            max-width: 1000px;
            width: 100%;
            background-color: #fff;
            padding: 30px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        table th, table td {
            padding: 12px 15px;
            border: 1px solid #ddd;
            text-align: center;
        }
        table th {
            background-color: #3498db;
            color: white;
        }
        table td {
            background-color: #f9f9f9;
        }
        form {
            display: inline-block;
        }
        .action-buttons form {
            margin-right: 10px;
        }
        .action-buttons {
            display: flex;
            justify-content: center;
        }
        input[type="submit"] {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #2980b9;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .add-product-form {
            margin-top: 20px;
            padding: 20px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .add-product-form h2 {
            margin-bottom: 15px;
            color: #333;
        }
        input[type="submit"].add-btn {
            width: 100%;
            background-color: #27ae60;
            margin-top: 10px;
        }
        input[type="submit"].add-btn:hover {
            background-color: #2ecc71;
        }

        /* Styling for the back button */
        .back-button {
            display: inline-block;
            margin: 20px auto;
            padding: 10px 15px;
            background-color: #dfe6e9; /* Light gray color */
            color: #2d3436;
            border: none;
            border-radius: 5px;
            text-align: center;
            text-decoration: none; /* Remove underline */
            transition: background-color 0.3s;
        }
        .back-button:hover {
            background-color: #b2bec3; /* Darker gray on hover */
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Manage Products</h1>
    <%
        List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Action</th>
        </tr>
        <%
            for (ProductDTO product : products) {
        %>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getProductName() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getStockQuantity() %></td>
            <td class="action-buttons">
                <form action="edit-product" method="post">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="submit" value="Edit">
                </form>
                <form action="delete-product" method="post">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this product?')">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p>No products found.</p>
    <%
        }
    %>

    <div class="add-product-form">
        <h2>Add New Product</h2>
        <form action="add-product" method="post">
            <label for="productName">Product Name:</label>
            <input type="text" name="productName" id="productName" required><br>
            <label for="price">Price:</label>
            <input type="number" name="price" id="price" required><br>
            <label for="stockQuantity">Stock Quantity:</label>
            <input type="number" name="stockQuantity" id="stockQuantity" required><br>
            <input type="submit" class="add-btn" value="Add Product">
        </form>
    </div>

    <!-- Back to Admin Dashboard Button -->
    <a href="admin-dashboard.jsp" class="back-button">Back to Dashboard</a>
</div>
</body>
</html>
