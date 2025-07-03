<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bhadrakali Temple - Devotee Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .temple-header {
            text-align: center;
            padding: 20px 0;
            background-color: #f8f9fa;
        }
        .menu-icon {
            font-size: 24px;
            cursor: pointer;
            position: fixed;
            left: 20px;  /* Changed from right to left */
            top: 20px;
        }
        .logout-btn {
            position: fixed;
            right: 20px;  /* Kept on right side */
            top: 20px;    /* Changed to same height as menu icon */
        }
        .sidebar {
            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1;
            top: 0;
            left: 0;      /* Changed from right to left */
            background-color: #111;
            overflow-x: hidden;
            transition: 0.5s;
            padding-top: 60px;
        }
        .sidebar a {
            padding: 8px 8px 8px 32px;
            text-decoration: none;
            font-size: 18px;
            color: #818181;
            display: block;
            transition: 0.3s;
        }
        .sidebar a:hover {
            color: #f1f1f1;
        }
        .sidebar .closebtn {
            position: absolute;
            top: 0;
            right: 25px;
            font-size: 36px;
            margin-left: 50px;
        }
        .temple-image {
            width: 100%;
            max-height: 400px;
            object-fit: cover;
        }
        .welcome-text {
            margin-top: 20px;
            text-align: center;
            color: #333;
        } 
        /* Add padding to the content when sidebar is open */
        #main {
            transition: margin-left .5s;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="menu-icon" onclick="openNav()">
        <i class="fas fa-bars"></i>
    </div>

    <a href="login.jsp" class="btn btn-danger logout-btn">Logout</a>

    <div class="temple-header">
        <h1>Bhadrakali Temple</h1>
        <%
            String username = (String) session.getAttribute("username");
            if (username != null) {
        %>
            <p class="welcome-text">Welcome, <%= username %></p>
        <% } %>
    </div>

    <div id="mySidebar" class="sidebar">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <a href="AboutController">About</a>
        <a href="SevaController">Sevas</a>
        <a href="DarshanController">Darshanam</a>
        <a href="PrasadamController">Prasadam</a>
        <a href="EventController">Events</a>
        <a href="MyBookingsController">My Bookings</a>
    </div>

    <div id="main">
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-12">
                    <img src="images/temple.jpg" alt="Bhadrakali Temple" class="temple-image">
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-md-12">
                    <h2>Welcome to Bhadrakali Temple</h2>
                    <p>Welcome to the divine abode of Goddess Bhadrakali. Our temple is a sacred space where devotees can find peace, solace, and spiritual enlightenment.</p>
                    
                    <h3>Temple Timings</h3>
                    <p>Morning: 6:00 AM - 12:00 PM<br>
                    Evening: 4:00 PM - 8:30 PM</p>
                    
                    <h3>Special Prayers</h3>
                    <p>Morning Aarti: 7:00 AM<br>
                    Evening Aarti: 7:00 PM</p>

                    <%
                        String errorMessage = (String) request.getAttribute("errorMessage");
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                    %>
                        <div class="alert alert-danger">
                            <%= errorMessage %>
                        </div>
                    <% } %>

                    <%
                        String successMessage = (String) request.getAttribute("successMessage");
                        if (successMessage != null && !successMessage.isEmpty()) {
                    %>
                        <div class="alert alert-success">
                            <%= successMessage %>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <script>
        function openNav() {
            document.getElementById("mySidebar").style.width = "250px";
            document.getElementById("main").style.marginLeft = "250px";
        }

        function closeNav() {
            document.getElementById("mySidebar").style.width = "0";
            document.getElementById("main").style.marginLeft = "0";
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>