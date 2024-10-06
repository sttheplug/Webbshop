<%@ page import="com.example.webbshop.ui.DTO.OrderDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Orders</title>
    <style>
        /* Base Styles */
        body {
            font-family: 'Arial', sans-serif;
            padding: 0;
            margin: 0;
            background-color: #f4f6f9;
        }

        .container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
            font-size: 28px;
        }

        /* Table Styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #3498db;
            color: white;
            text-transform: uppercase;
            letter-spacing: 0.1em;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        /* No Orders Message */
        .no-orders-message {
            color: #e74c3c;
            font-size: 18px;
            font-weight: bold;
            text-align: center;
            margin: 20px 0;
        }

        /* Button Styles */
        .back-button {
            display: inline-block;
            padding: 12px 25px;
            margin-top: 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            font-size: 16px;
            border-radius: 5px;
            text-align: center;
            transition: background-color 0.3s ease;
        }

        .back-button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Admin Panel - Orders</h2>

    <%
        List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
        if (orders == null || orders.isEmpty()) {
    %>
    <div class="no-orders-message">No orders found.</div>
    <%
    } else {
    %>
    <table>
        <tr>
            <th>Order ID</th>
            <th>User ID</th>
            <th>Total Price</th>
            <th>Order Date</th>
        </tr>
        <%
            for (OrderDTO order : orders) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getUserId() %></td>
            <td>$<%= order.getTotalPrice() %></td>
            <td><%= order.getOrderDate() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>

    <a href="admin-dashboard.jsp" class="back-button">Back to Dashboard</a>
</div>

</body>
</html>
