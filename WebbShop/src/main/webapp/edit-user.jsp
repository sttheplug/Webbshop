<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.webbshop.ui.DTO.UserDTO" %>
<html>
<head>
    <title>Edit User</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px;
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
            color: #2d3436;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            color: #636e72;
        }

        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        input[type="submit"] {
            background-color: #00b894;
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 20px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #00966e;
        }

        .back-button {
            background-color: #dfe6e9; /* Light gray color */
            color: #2d3436;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            display: inline-block;
            margin: 20px auto;
            text-decoration: none; /* Remove underline */
            transition: background-color 0.3s;
            width: 100%;
        }

        .back-button:hover {
            background-color: #b2bec3; /* Darker gray on hover */
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Edit User</h1>

    <%
        UserDTO user = (UserDTO) request.getAttribute("user");
        if (user != null) {
    %>

    <form action="update-user" method="post">
        <input type="hidden" name="userId" value="<%= user.getUserId() %>">

        <label for="username">Username:</label>
        <input type="text" name="username" id="username" value="<%= user.getUsername() %>" required>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>

        <label for="role">Role:</label>
        <select name="role" id="role" required>
            <option value="customer" <%= user.getRole().name().equalsIgnoreCase("customer") ? "selected" : "" %>>Customer</option>
            <option value="warehouse_staff" <%= user.getRole().name().equalsIgnoreCase("warehouse_staff") ? "selected" : "" %>>Staff</option>
            <option value="admin" <%= user.getRole().name().equalsIgnoreCase("admin") ? "selected" : "" %>>Admin</option>
        </select>

        <input type="submit" value="Update User">
    </form>

    <%
    } else {
    %>
    <p class="no-users">User not found.</p>
    <%
        }
    %>
    <a href="adminUsers.jsp" class="back-button">Back to Manage Users</a>
</div>
</body>
</html>
