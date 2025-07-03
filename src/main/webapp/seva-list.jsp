<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Seva" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Sevas - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .seva-card {
            transition: transform 0.2s;
        }
        .seva-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Available Sevas</h2>
            <a href="devotee.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <% if(request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>

        <div class="row row-cols-1 row-cols-md-3 g-4">
            <% 
            List<Seva> sevaList = (List<Seva>) request.getAttribute("sevaList");
            if(sevaList != null && !sevaList.isEmpty()) {
                for(Seva seva : sevaList) {
            %>
                <div class="col">
                    <div class="card h-100 seva-card">
                        <div class="card-body">
                            <h5 class="card-title"><%= seva.getSevaName() %></h5>
                            <p class="card-text">
                                <strong><i class="far fa-clock"></i> Timing:</strong><br>
                                <%= seva.getTiming() %><br>
                                <strong><i class="fas fa-rupee-sign"></i> Price:</strong><br>
                                ₹<%= seva.getPrice() %>
                            </p>
                            <a href="SevaBookingController?action=showForm&sevaId=<%= seva.getSevaId() %>" 
                               class="btn btn-primary">
                                <i class="fas fa-book"></i> Book Now
                            </a>
                        </div>
                    </div>
                </div>
            <%
                }
            } else {
            %>
                <div class="col-12">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle"></i> No sevas are currently available.
                    </div>
                </div>
            <% } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>