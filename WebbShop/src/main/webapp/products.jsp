<%@ page import="com.example.webbshop.bo.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product List</title>
    <style>
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        h1 {
            text-align: center;
        }
    </style>
</head>
<body>
<h1>Available Products</h1>

<table>
    <tr>
        <th>Product ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <%
        // Retrieve the list of products from the request attribute
        List<Product> items = (List<Product>) request.getAttribute("items");

        // Check if there are any products to display
        if (items != null && !items.isEmpty()) {
            for (Product item : items) {
    %>
    <tr>
        <td><%= item.getProductId() %></td>
        <td><%= item.getProductName() %></td>
        <td><%= item.getPrice() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3" style="text-align: center;">No products available.</td>
    </tr>
    <%
        }
    %>
</table>

<br>
<div style="text-align: center;">
    <a href="logout">Logout</a>
</div>
</body>
</html>
