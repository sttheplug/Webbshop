<%@ page import="com.example.webbshop.bo.model.User" %>
<%@ page import="com.example.webbshop.ui.DTO.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Customer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background: linear-gradient(135deg, #74b9ff, #a29bfe);
        }
        .welcome-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #2d3436;
        }
        p {
            color: #636e72;
        }
    </style>
    <script>
        // Automatically redirect to the products page after 3 seconds
        setTimeout(function() {
            window.location.href = "products";
        }, 3000);
    </script>
</head>
<body>
<div class="welcome-container">
    <h1>Welcome, <%= ((UserDTO) session.getAttribute("loggedInUser")).getUsername() %>!</h1>
    <p>Thank you for logging in. You will be redirected to the products page shortly...</p>
</div>
</body>
</html>
