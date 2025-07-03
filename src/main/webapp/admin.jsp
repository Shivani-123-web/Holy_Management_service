<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Event" %>
<%@ page import="dao.EventDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .admin-header {
            background: #f8f9fa;
            padding: 20px 0;
            margin-bottom: 30px;
            border-bottom: 1px solid #dee2e6;
        }
        .dashboard-content {
            padding-left: 50px; /* Move content to the left */
        }
        .card {
            transition: transform 0.2s, box-shadow 0.2s;
            border: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            background: #fff;
            height: 100%;
            width: 300px; /* Fixed width for both cards */
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .card-icon {
            font-size: 3em;
            margin-bottom: 20px;
            color: #0d6efd; /* Bootstrap primary blue */
            transition: transform 0.3s;
        }
        .card:hover .card-icon {
            transform: scale(1.1);
        }
        .card-body {
            padding: 25px;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .card-title {
            font-weight: 600;
            margin-bottom: 15px;
            color: #333;
            font-size: 1.25rem; /* Consistent font size */
        }
        .card-text {
            color: #6c757d;
            margin-bottom: 25px;
            font-size: 0.9rem; /* Consistent font size */
            height: 40px; /* Fixed height for description */
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .btn-manage {
            width: 100%;
            padding: 10px 20px;
            font-weight: 500;
            border-radius: 5px;
            background-color: #0d6efd; /* Bootstrap primary blue */
            border-color: #0d6efd;
            color: white;
            transition: all 0.3s;
            font-size: 0.95rem; /* Consistent font size */
        }
        .btn-manage:hover {
            background-color: #0b5ed7; /* Darker blue on hover */
            border-color: #0b5ed7;
            color: white;
        }
        .welcome-text {
            font-size: 1.1rem;
            color: #495057;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Admin Header -->
    <div class="admin-header">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">Admin Dashboard</h2>
                <div>
                    <span class="welcome-text me-3">Welcome, <%= session.getAttribute("username") %></span>
                    <a href="logout.jsp" class="nav-link">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <div class="dashboard-content">
            <div class="row">
                <!-- Manage Sevas Card -->
                <div class="col-auto mb-4 me-4">
                    <div class="card">
                        <div class="card-body">
                            <i class="fas fa-cog card-icon"></i>
                            <h5 class="card-title">Manage Sevas</h5>
                            <p class="card-text">Manage temple sevas</p>
                            <a href="list-sevas.jsp" class="btn btn-manage">
                                <i class="fas fa-arrow-right"></i> Manage Sevas
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Manage Events Card -->
                <div class="col-auto mb-4">
                    <div class="card">
                        <div class="card-body">
                            <i class="fas fa-cog card-icon"></i>
                            <h5 class="card-title">Manage Events</h5>
                            <p class="card-text">Manage temple events</p>
                            <a href="list-events.jsp" class="btn btn-manage">
                                <i class="fas fa-arrow-right"></i> Manage Events
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Manage Darshans Card -->
                <div class="col-auto mb-4">
                    <div class="card">
                        <div class="card-body">
                            <i class="fas fa-pray card-icon"></i>
                            <h5 class="card-title">Manage Darshans</h5>
                            <p class="card-text">Manage temple darshans</p>
                            <a href="list-darshans.jsp" class="btn btn-manage">
                                <i class="fas fa-arrow-right"></i> Manage Darshans
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>