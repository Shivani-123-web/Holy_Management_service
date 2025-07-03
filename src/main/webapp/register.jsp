<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Temple Management - Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .register-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .register-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .register-header h2 {
            color: #333;
            margin-bottom: 10px;
        }
        .error-message {
            color: #dc3545;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .success-message {
            color: #28a745;
            background-color: #d4edda;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .required-field::after {
            content: " *";
            color: red;
        }
        .btn-register {
            width: 100%;
            padding: 10px;
            font-size: 16px;
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="register-container">
            <div class="register-header">
                <h2>User Registration</h2>
                <p>Please fill in all required fields</p>
            </div>

            <!-- Error/Success Message Display -->
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            <% if(request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            <!-- Registration Form -->
<form action="UserRegistrationController" method="post" id="registrationForm">
    <div class="form-group">
        <label for="username" class="form-label required-field">Username</label>
        <input type="text" 
               class="form-control" 
               id="username" 
               name="username" 
               required 
               pattern="[a-zA-Z0-9]{5,}"
               title="Username must be at least 5 characters long and contain only letters and numbers"
               placeholder="Choose a username">
    </div>

    <div class="form-group">
        <label for="password" class="form-label required-field">Password</label>
        <input type="password" 
               class="form-control" 
               id="password" 
               name="password" 
               required 
               pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
               title="Password must be at least 8 characters long and contain both letters and numbers"
               placeholder="Create a password">
    </div>

    <div class="form-group">
        <label for="confirmPassword" class="form-label required-field">Confirm Password</label>
        <input type="password" 
               class="form-control" 
               id="confirmPassword" 
               name="confirmPassword" 
               required 
               placeholder="Confirm your password">
    </div>

    <div class="form-group">
        <label for="fullName" class="form-label required-field">Full Name</label>
        <input type="text" 
               class="form-control" 
               id="fullName" 
               name="fullName" 
               required 
               placeholder="Enter your full name">
    </div>

    <div class="form-group">
        <label for="email" class="form-label required-field">Email</label>
        <input type="email" 
               class="form-control" 
               id="email" 
               name="email" 
               required 
               placeholder="Enter your email">
    </div>

    <div class="form-group">
        <label for="phone" class="form-label">Phone Number</label>
        <input type="tel" 
               class="form-control" 
               id="phone" 
               name="phone" 
               pattern="[0-9]{10}"
               title="Please enter a valid 10-digit phone number"
               placeholder="Enter your phone number">
    </div>

    <div class="form-group">
        <label for="address" class="form-label">Address</label>
        <textarea class="form-control" 
                  id="address" 
                  name="address" 
                  rows="3" 
                  placeholder="Enter your address"></textarea>
    </div>
    <button type="submit" class="btn btn-primary btn-register">Register</button>
</form>