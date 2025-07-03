<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Event" %>
<%@ page import="dao.EventDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Temple Events</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .event-card {
            margin-bottom: 20px;
            transition: transform 0.2s;
        }
        .event-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-calendar-alt"></i> Temple Events</h2>
            <a href="devotee.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <div class="row">
            <%
                try {
                    EventDAO eventDAO = new EventDAO();
                    List<Event> events = eventDAO.getAllActiveEvents();
                    if (events.isEmpty()) {
            %>
                        <div class="col-12">
                            <div class="alert alert-info">
                                No events are currently available.
                            </div>
                        </div>
            <%
                    } else {
                        for (Event event : events) {
            %>
                            <div class="col-md-4">
                                <div class="card event-card">
                                    <div class="card-body">
                                        <h5 class="card-title"><%= event.getEventName() %></h5>
                                        <p class="card-text">
                                            <strong>
                                                <i class="fas fa-rupee-sign"></i> Price: ₹<%= event.getPrice() %>
                                            </strong>
                                        </p>
                                        <a href="book-event.jsp?id=<%= event.getEventId() %>" class="btn btn-primary">
                                            <i class="fas fa-calendar-check"></i> Book Now
                                        </a>
                                    </div>
                                </div>
                            </div>
            <%
                        }
                    }
                } catch (Exception e) {
            %>
                    <div class="col-12">
                        <div class="alert alert-danger">
                            Error loading events: <%= e.getMessage() %>
                        </div>
                    </div>
            <%
                }
            %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>