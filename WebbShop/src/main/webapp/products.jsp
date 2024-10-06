<%@ page import="com.example.webbshop.ui.DTO.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #74b9ff, #a29bfe);
        }

        .products-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 1200px;
            margin-top: 20px;
        }

        h2 {
            text-align: center;
            color: #2d3436;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 15px;
            text-align: left;
        }

        th {
            background-color: #6c5ce7;
            color: #fff;
            border-bottom: 3px solid #ddd;
        }

        td {
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        form {
            display: inline-block;
        }

        input[type="number"] {
            width: 60px;
            padding: 8px;
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #6c5ce7;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #5a4fcf;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #74b9ff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        a:hover {
            background-color: #4a9ff3;
        }

        /* Logout Button Styling */
        .logout-container {
            display: flex;
            justify-content: flex-end;
            width: 100%;
            margin-bottom: 20px;
        }

        .logout-container form {
            display: inline;
        }

        .logout-container input[type="submit"] {
            background-color: #ff7675;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .logout-container input[type="submit"]:hover {
            background-color: #d63031;
        }

    </style>
</head>
<body>

<!-- Logout Button -->
<div class="logout-container">
    <form action="logout" method="post">
        <input type="submit" value="Logout">
    </form>
</div>

<div class="products-container">
    <h2>Available Products</h2>

    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Stock Quantity</th>
            <th>Action</th>
        </tr>
        <%
            List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products"); // Changed to ProductDTO
            for (ProductDTO product : products) { // Changed to ProductDTO
        %>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getProductName() %></td>
            <td>$<%= product.getPrice() %></td>
            <td><%= product.getStockQuantity() %></td>
            <td>
                <form action="add-to-cart" method="post">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="number" name="quantity" min="1" value="1" />
                    <input type="submit" value="Add to Cart" />
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <a href="cart">View Cart</a>
</div>

</body>
</html>
