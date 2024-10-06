<%@ page import="com.example.webbshop.ui.DTO.UserDTO" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Admin - Manage Users</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #2d3436;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #00b894;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .action-form {
            display: inline;
        }

        input[type="submit"] {
            background-color: #00b894;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #00966e;
        }

        h2 {
            margin-top: 40px;
            color: #2d3436;
        }

        form {
            margin: 20px auto;
            display: flex;
            flex-direction: column;
            max-width: 400px;
        }

        label {
            margin: 5px 0;
            color: #636e72;
        }

        input[type="text"],
        input[type="password"],
        select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        .no-users {
            text-align: center;
            color: #636e72;
        }

        /* Styling for the back button */
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
        }

        .back-button:hover {
            background-color: #b2bec3; /* Darker gray on hover */
        }
    </style>
</head>
<body>
<h1>Manage Users</h1>
<%
    List<UserDTO> users = (List<UserDTO>) request.getAttribute("users");
    if (users != null && !users.isEmpty()) {
%>
<table>
    <tr>
        <th>User ID</th>
        <th>Username</th>
        <th>Password</th>
        <th>Role</th>
        <th>Action</th>
    </tr>
    <%
        for (UserDTO user : users) {
    %>
    <tr>
        <td><%= user.getUserId() %></td>
        <td><%= user.getUsername() %></td>
        <td>********</td>
        <td><%= user.getRole().name().toLowerCase() %></td>
        <td>
            <form class="action-form" action="edit-user" method="post">
                <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                <input type="submit" value="Edit">
            </form>
            <form class="action-form" action="delete-user" method="post">
                <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this user?')">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<p class="no-users">No users found.</p>
<%
    }
%>

<!-- Form to Add New User -->
<h2>Add New User</h2>
<form action="add-user" method="post">
    <label for="username">Username:</label>
    <input type="text" name="username" id="username" required>

    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required>

    <label for="role">Role:</label>
    <select name="role" id="role" required>
        <option value="customer">Customer</option>
        <option value="warehouse_staff">Staff</option>
        <option value="admin">Admin</option>
    </select>

    <input type="submit" value="Add User">
</form>

<!-- Back to Admin Dashboard Button -->
<a href="admin-dashboard.jsp" class="back-button">Back to Dashboard</a>

</body>
</html>
