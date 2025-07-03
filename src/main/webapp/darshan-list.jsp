<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Darshan" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Darshans - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .darshan-card {
            transition: transform 0.2s;
        }
        .darshan-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Available Darshans</h2>
            <a href="${pageContext.request.contextPath}/devotee.jsp" class="btn btn-secondary">
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
            List<Darshan> darshans = (List<Darshan>) request.getAttribute("darshans");
            if(darshans != null && !darshans.isEmpty()) {
                for(Darshan darshan : darshans) {
                    if("active".equals(darshan.getStatus())) {
            %>
                <div class="col">
                    <div class="card h-100 darshan-card">
                        <div class="card-body">
                            <h5 class="card-title"><%= darshan.getDarshan_type() %></h5>
                            <p class="card-text">
                                <strong><i class="far fa-clock"></i> Time Slot:</strong><br>
                                <%= darshan.getTime_slot() %><br>
                                <strong><i class="fas fa-users"></i> Maximum Persons:</strong><br>
                                <%= darshan.getMax_persons() %><br>
                                <strong><i class="fas fa-rupee-sign"></i> Price:</strong><br>
                                ₹<%= darshan.getPrice() %>
                            </p>
                            <div class="card-text mb-3">
                                <small class="text-muted"><%= darshan.getDescription() %></small>
                            </div>
                            <a href="${pageContext.request.contextPath}/DarshanController?action=book&id=<%= darshan.getDarshan_id() %>" 
                               class="btn btn-primary w-100">
                                <i class="fas fa-book"></i> Book Now
                            </a>
                        </div>
                    </div>
                </div>
            <%
                    }
                }
            } else {
            %>
                <div class="col-12">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle"></i> No darshans are currently available.
                    </div>
                </div>
            <% } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 