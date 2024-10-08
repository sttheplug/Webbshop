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
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        header {
            background-color: #2d3436;
            color: white;
            padding: 10px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logout-button {
            background-color: #e17055;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .logout-button:hover {
            background-color: #d35400;
        }

        .container {
            flex: 1;
            width: 80%;
            max-width: 1200px;
            background-color: #fff;
            margin: 20px auto;
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
            margin-top: 20px;
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

        tr {
            transition: background-color 0.3s;
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

        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            header {
                flex-direction: column;
                align-items: flex-start;
            }

            .logout-button {
                width: 100%;
                margin-top: 10px;
            }
        }
    </style>
</head>
<body>
<header>
    <h1>Warehouse Management</h1>
    <form action="logout" method="get">
        <input type="submit" class="logout-button" value="Logout">
    </form>
</header>
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
            List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders"); // Retrieve orders
            if (orders != null && !orders.isEmpty()) { // Check if orders is not null or empty
                for (OrderDTO order : orders) { // Iterate over the orders
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getUserId() %></td> <!-- Assuming user ID is what you want to display -->
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
            } // End of orders loop
        } else {
        %>
        <tr>
            <td colspan="5" style="text-align:center;">No orders found.</td> <!-- Display message if no orders -->
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
