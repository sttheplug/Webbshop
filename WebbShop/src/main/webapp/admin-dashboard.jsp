<%@ page import="com.example.webbshop.ui.DTO.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        /* Base Styles */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        .dashboard-container {
            width: 90%;
            max-width: 1200px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            padding: 40px;
            text-align: center;
        }

        h1 {
            font-size: 36px;
            margin-bottom: 30px;
            color: #3498db;
            font-weight: 600;
        }

        .welcome-message {
            margin-bottom: 40px;
            font-size: 20px;
            color: #636e72;
        }

        .dashboard-buttons {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 20px;
        }

        .dashboard-button {
            background-color: #3498db;
            color: white;
            text-decoration: none;
            padding: 15px 30px;
            border-radius: 5px;
            font-size: 18px;
            transition: all 0.3s ease;
            width: 250px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .dashboard-button:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }

        .logout-button {
            background-color: #e74c3c;
            color: white;
            text-decoration: none;
            padding: 12px 25px;
            margin-top: 40px;
            border-radius: 5px;
            font-size: 16px;
            display: inline-block;
            transition: all 0.3s ease;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .logout-button:hover {
            background-color: #c0392b;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .dashboard-button {
                width: 100%;
                font-size: 16px;
                padding: 12px;
            }

            .dashboard-buttons {
                gap: 15px;
            }

            h1 {
                font-size: 32px;
            }

            .welcome-message {
                font-size: 18px;
            }
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <h1>Admin Dashboard</h1>
    <div class="welcome-message">
        Welcome, <%= ((UserDTO) session.getAttribute("loggedInUser")).getUsername() %>!
    </div>

    <div class="dashboard-buttons">
        <a href="adminOrders" class="dashboard-button">Manage Orders</a>
        <a href="adminProducts" class="dashboard-button">Manage Products</a>
        <a href="adminUsers" class="dashboard-button">Manage Users</a>
        <a href="adminCategories" class="dashboard-button">Manage Categories</a>
    </div>

    <a href="logout" class="logout-button">Logout</a>
</div>
</body>
</html>
