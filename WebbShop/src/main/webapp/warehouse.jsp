<%@ page import="com.example.webbshop.ui.DTO.OrderDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Warehouse Dashboard</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            padding: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            width: 80%;
            max-width: 1200px;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #2d3436;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #00b894;
            color: #fff;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        form {
            display: inline-block;
        }

        input[type="submit"] {
            background-color: #0984e3;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #065db7;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Warehouse Orders</h2>

    <table>
        <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders"); // Changed to OrderDTO
            for (OrderDTO order : orders) { // Changed to OrderDTO
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getUserId() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.isPacked() ? "Packed" : "Pending" %></td>
            <td>
                <% if (!order.isPacked()) { %>
                <form action="pack-order" method="post">
                    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                    <input type="submit" value="Pack Order">
                </form>
                <% } else { %>
                Packed
                <% } %>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
