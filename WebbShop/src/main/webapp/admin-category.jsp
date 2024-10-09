<%@ page import="com.example.webbshop.ui.DTO.CategoryDTO" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Admin - Manage Categories</title>
    <style>
        /* Basic Styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f7;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 700px;
            background-color: #fff;
            padding: 30px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .action-buttons {
            display: flex;
            justify-content: space-around;
        }
        .btn {
            padding: 5px 10px;
            cursor: pointer;
            border: none;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .delete-btn {
            background-color: #dc3545;
        }
        .delete-btn:hover {
            background-color: #c82333;
        }
        .add-btn {
            background-color: #28a745;
        }
        .add-btn:hover {
            background-color: #218838;
        }
        .back-button {
            display: block;
            margin-top: 20px;
            text-align: center;
            text-decoration: none;
            color: #007bff;
        }
        .back-button:hover {
            text-decoration: underline;
        }
        form {
            margin: 20px 0;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Manage Categories</h1>

    <%
        // Fetch categories and possible error messages from the request
        List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
        if (errorMessage != null) {
    %>
    <p style="color: red;"><%= errorMessage %></p>
    <% } else if (successMessage != null) { %>
    <p style="color: green;"><%= successMessage %></p>
    <% } %>

    <h2>Existing Categories</h2>
    <table>
        <tr>
            <th>Category ID</th>
            <th>Category Name</th>
            <th>Action</th>
        </tr>
        <%
            if (categories != null && !categories.isEmpty()) {
                for (CategoryDTO category : categories) {
        %>
        <tr>
            <td><%= category.getCategoryId() %></td>
            <td><%= category.getCategoryName() %></td>
            <td class="action-buttons">
                <!-- Delete category form -->
                <form action="delete-category" method="post">
                    <input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
                    <input type="submit" class="btn delete-btn" value="Delete"
                           onclick="return confirm('Are you sure you want to delete this category?')">
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="3">No categories found.</td>
        </tr>
        <% } %>
    </table>

    <!-- Form to Add New Category -->
    <h2>Add New Category</h2>
    <form action="add-category" method="post">
        <label for="newCategoryName">Category Name:</label>
        <input type="text" name="categoryName" id="newCategoryName" placeholder="Enter new category name" required>
        <input type="submit" class="btn add-btn" value="Add Category">
    </form>

    <a href="admin-dashboard.jsp" class="back-button">Back to Dashboard</a>
</div>
</body>
</html>
