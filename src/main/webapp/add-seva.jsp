<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Seva</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .form-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .card {
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #f8f9fa;
        }
        .required-field::after {
            content: "*";
            color: red;
            margin-left: 4px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="form-container">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Add New Seva</h3>
                </div>
                <div class="card-body">
                    <!-- Display Messages -->
                    <% if (session.getAttribute("successMessage") != null) { %>
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <%= session.getAttribute("successMessage") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                        <% session.removeAttribute("successMessage"); %>
                    <% } %>

                    <% if (session.getAttribute("errorMessage") != null) { %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <%= session.getAttribute("errorMessage") %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                        <% session.removeAttribute("errorMessage"); %>
                    <% } %>

                    <!-- Seva Form -->
                    <form action="AdminSevaController" method="post">
                        <div class="mb-3">
                            <label for="sevaName" class="form-label required-field">Seva Name</label>
                            <input type="text" class="form-control" id="sevaName" name="sevaName" 
                                   placeholder="Enter seva name" required>
                        </div>

                        <div class="mb-3">
                            <label for="timing" class="form-label required-field">Timing</label>
                            <input type="text" class="form-control" id="timing" name="timing" 
                                   placeholder="Enter seva timing" required>
                            <small class="text-muted">Example: Morning 6:00 AM - 12:00 PM</small>
                        </div>

                        <div class="mb-3">
                            <label for="price" class="form-label required-field">Price (₹)</label>
                            <input type="number" class="form-control" id="price" name="price" 
                                   min="0" step="0.01" placeholder="Enter seva price" required>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-plus"></i> Add Seva
                            </button>
                            <a href="dashboard.jsp" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i> Back to Dashboard
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>