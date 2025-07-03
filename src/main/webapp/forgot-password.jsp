<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Temple Management - Forgot Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .forgot-password-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .forgot-password-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .forgot-password-header h2 {
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
        .btn-submit {
            width: 100%;
            padding: 10px;
            font-size: 16px;
        }
        .back-to-login {
            text-align: center;
            margin-top: 20px;
        }
        #resetForm {
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="forgot-password-container">
            <div class="forgot-password-header">
                <h2>Password Recovery</h2>
                <p>Please enter your registered email</p>
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

            <!-- Email Verification Form -->
            <form id="emailForm" action="ForgotPasswordController" method="post">
                <input type="hidden" name="action" value="sendOTP">
                
                <div class="form-group">
                    <label for="email" class="form-label">Email Address</label>
                    <input type="email" 
                           class="form-control" 
                           id="email" 
                           name="email" 
                           required 
                           placeholder="Enter your registered email">
                </div>

                <button type="submit" class="btn btn-primary btn-submit">Send OTP</button>
            </form>

            <!-- OTP Verification and Password Reset Form -->
            <form id="resetForm" action="ForgotPasswordController" method="post">
                <input type="hidden" name="action" value="resetPassword">
                
                <div class="form-group">
                    <label for="otp" class="form-label">Enter OTP</label>
                    <input type="text" 
                           class="form-control" 
                           id="otp" 
                           name="otp" 
                           required 
                           pattern="[0-9]{6}"
                           title="Please enter the 6-digit OTP"
                           placeholder="Enter 6-digit OTP">
                </div>

                <div class="form-group">
                    <label for="newPassword" class="form-label">New Password</label>
                    <input type="password" 
                           class="form-control" 
                           id="newPassword" 
                           name="newPassword" 
                           required 
                           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                           title="Password must be at least 8 characters long and contain both letters and numbers"
                           placeholder="Enter new password">
                </div>

                <div class="form-group">
                    <label for="confirmPassword" class="form-label">Confirm Password</label>
                    <input type="password" 
                           class="form-control" 
                           id="confirmPassword" 
                           name="confirmPassword" 
                           required 
                           placeholder="Confirm new password">
                </div>

                <button type="submit" class="btn btn-primary btn-submit">Reset Password</button>
            </form>

            <div class="back-to-login">
                <a href="login.jsp">Back to Login</a>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Form validation and handling -->
    <script>
        // Show reset form after OTP is sent
        <% if(request.getAttribute("otpSent") != null) { %>
            document.getElementById('emailForm').style.display = 'none';
            document.getElementById('resetForm').style.display = 'block';
        <% } %>

        // Password validation
        document.getElementById('resetForm').addEventListener('submit', function(e) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if(newPassword !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match!');
                return false;
            }

            // Password strength validation
            const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
            if(!passwordPattern.test(newPassword)) {
                e.preventDefault();
                alert('Password must be at least 8 characters long and contain both letters and numbers!');
                return false;
            }

            return true;
        });

        // Email validation
        document.getElementById('emailForm').addEventListener('submit', function(e) {
            const email = document.getElementById('email').value;
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            
            if(!emailPattern.test(email)) {
                e.preventDefault();
                alert('Please enter a valid email address!');
                return false;
            }

            return true;
        });

        // OTP validation
        document.getElementById('otp').addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '').slice(0, 6);
        });
    </script>
</body>
</html>