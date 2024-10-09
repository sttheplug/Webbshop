<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.webbshop.ui.DTO.ProductDTO" %>
<%@ page import="com.example.webbshop.ui.DTO.CategoryDTO" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Edit Product</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f7;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            background-color: #fff;
            padding: 30px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        label {
            display: block;
            margin-top: 10px;
            color: #333;
        }
        input[type="text"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Edit Product</h1>

    <%
        // Get the product to be edited and the list of all categories
        ProductDTO product = (ProductDTO) request.getAttribute("product");
        List<CategoryDTO> categoryList = (List<CategoryDTO>) request.getAttribute("categories");

        if (product != null && categoryList != null) {
    %>

    <form action="update-product" method="post">
        <input type="hidden" name="productId" value="<%= product.getProductId() %>">

        <label for="name">Product Name:</label>
        <input type="text" name="name" id="name" value="<%= product.getProductName() %>" required>

        <label for="price">Price:</label>
        <input type="number" name="price" id="price" value="<%= product.getPrice() %>" required step="0.01">

        <label for="stockQuantity">Stock Quantity:</label>
        <input type="number" name="stockQuantity" id="stockQuantity" value="<%= product.getStockQuantity() %>" required>

        <label for="category">Category:</label>
        <select name="category" id="category" required>
            <%
                for (CategoryDTO category : categoryList) {
                    String selected = category.getCategoryName().equals(product.getCategoryName()) ? "selected" : "";
            %>
            <option value="<%= category.getCategoryName() %>" <%= selected %>><%= category.getCategoryName() %></option>
            <%
                }
            %>
        </select>

        <input type="submit" value="Update Product">
    </form>

    <%
        } else {
            System.out.println("<p>Product or category data not found!</p>");
        }
    %>
</div>
</body>
</html>
