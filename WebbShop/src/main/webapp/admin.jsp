<%@ page import="com.example.webbshop.bo.model.Order" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background-color: #f9f9f9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h2>Admin Panel - Orders</h2>

<table>
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Total Price</th>
        <th>Order Date</th>
    </tr>
    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        for (Order order : orders) {
    %>
    <tr>
        <td><%= order.getOrderId() %></td>
        <td><%= order.getUserId() %></td>
        <td><%= order.getTotalPrice() %></td>
        <td><%= order.getOrderDate() %></td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>
