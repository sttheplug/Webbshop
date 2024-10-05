<%@ page import="com.example.webbshop.bo.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Cart</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #74b9ff, #a29bfe);
        }

        .cart-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 800px;
            text-align: center;
        }

        h2 {
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
            color: white;
            border-bottom: 3px solid #ddd;
        }

        td {
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .cart-actions {
            margin-top: 20px;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #6c5ce7;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        a:hover {
            background-color: #5a4fcf;
        }

        .empty-cart-message {
            color: #e74c3c;
            font-size: 18px;
            font-weight: bold;
        }

        .total-price {
            margin-top: 20px;
            font-size: 18px;
            font-weight: bold;
        }

        .remove-button {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 5px;
        }

        .remove-button:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>

<div class="cart-container">
    <h2>Your Shopping Cart</h2>

    <%
        // Fetch the cart items from the session (or request attribute)
        List<Product> cartItems = (List<Product>) request.getAttribute("cartItems");

        // Initialize the total price variable
        double totalPrice = 0;

        // Check if the cart is not empty
        if (cartItems != null && !cartItems.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Product Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th> <!-- Column to remove items -->
        </tr>
        <%
            // Loop through the cart items and display each one in the table
            for (Product product : cartItems) {
                totalPrice += product.getPrice(); // Calculate total price by summing up the product prices
        %>
        <tr>
            <td><%= product.getProductName() %></td>
            <td>$<%= (int) product.getPrice() %></td> <!-- Casting price to integer to remove decimals -->
            <td>1</td> <!-- Displaying quantity as 1 for now, can be adjusted later -->
            <td>
                <!-- Form to remove product from cart -->
                <form action="remove-from-cart" method="post" style="display:inline;">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" class="remove-button">Remove</button>
                </form>
            </td>
        </tr>
        <% } %> <!-- End of loop through cart items -->
    </table>

    <!-- Display the calculated total price -->
    <div class="total-price">
        Total Price: $<%= String.format("%.2f", totalPrice) %>
    </div>

    <!-- Cart action buttons -->
    <div class="cart-actions">
        <a href="checkout">Proceed to Checkout</a>
        <a href="products" style="background-color: #74b9ff;">Back to Products</a>
    </div>

    <%
    } else {  // If the cart is empty
    %>
    <p class="empty-cart-message">Your cart is empty.</p>
    <a href="products">Start Shopping</a>
    <% } %> <!-- End of cart empty check -->
</div>

</body>
</html>
