<%@ page import="com.example.webbshop.ui.DTO.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Warehouse Staff</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background: linear-gradient(135deg, #0984e3, #6c5ce7);
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
        .redirect-message {
            margin-top: 20px;
            font-weight: bold;
        }
        .button-container {
            margin-top: 20px;
        }
        .navigate-button {
            background-color: #00b894;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .navigate-button:hover {
            background-color: #00966e;
        }
    </style>
    <script>
        // Automatically redirect to the warehouse dashboard after 3 seconds
        setTimeout(function() {
            window.location.href = "warehouse";
        }, 3000);
    </script>
</head>
<body>
<div class="welcome-container">
    <h1>Welcome, <%= ((UserDTO) session.getAttribute("loggedInUser")).getUsername() %>!</h1>
    <p>Thank you for logging in. You will be redirected to the warehouse dashboard shortly...</p>
    <div class="redirect-message">
        <p>If you are not redirected, click the button below:</p>
    </div>
    <div class="button-container">
        <button class="navigate-button" onclick="window.location.href='warehouse'">Go to Warehouse Dashboard</button>
    </div>
</div>
</body>
</html>
