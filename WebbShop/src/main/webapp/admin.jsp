<%@ page import="com.example.webbshop.ui.DTO.OrderDTO" %>
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
        .no-orders-message {
            color: #e74c3c;
            font-size: 18px;
            font-weight: bold;
            text-align: center;
            margin: 20px 0;
        }
    </style>
</head>
<body>
<h2>Admin Panel - Orders</h2>

<%
    List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
%>
<table>
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Total Price</th>
        <th>Order Date</th>
    </tr>
    <%
        for (OrderDTO order : orders) {  // Change here to use OrderDTO
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
<%
} else {
%>
<div class="no-orders-message">No orders found.</div>
<%
    }
%>

</body>
</html>
