<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Seva" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Sevas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Available Sevas</h2>
        
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <% 
            List<Seva> sevaList = (List<Seva>) request.getAttribute("sevaList");
            if(sevaList != null && !sevaList.isEmpty()) {
                for(Seva seva : sevaList) {
            %>
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><%= seva.getSevaName() %></h5>
                            <p class="card-text">
                                <strong>Timing:</strong> <%= seva.getTiming() %><br>
                                <strong>Price:</strong> ₹<%= seva.getPrice() %>
                            </p>
                            <a href="SevaBookingController?action=book&sevaId=<%= seva.getSevaId() %>" 
                               class="btn btn-primary">Book Now</a>
                        </div>
                    </div>
                </div>
            <%
                }
            } else {
            %>
                <div class="col-12">
                    <div class="alert alert-info">
                        No sevas are currently available.
                    </div>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>