<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event"%>
<%@ page import="dao.EventDAO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Event - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .dashboard-header {
            background: #f8f9fa;
            padding: 15px 0;
            margin-bottom: 30px;
            border-bottom: 1px solid #dee2e6;
        }
        .content-wrapper {
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        .btn-update {
            background-color: #0d6efd;
            color: white;
        }
        .btn-update:hover {
            background-color: #0b5ed7;
            color: white;
        }
    </style>
</head>
<body class="bg-light">
    <%
        int eventId = Integer.parseInt(request.getParameter("id"));
        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(eventId);
        
        if (event == null) {
            response.sendRedirect("list-events.jsp");
            return;
        }
    %>

    <!-- Dashboard Header -->
    <div class="dashboard-header">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">
                    <i class="fas fa-edit me-2"></i> Edit Event
                </h2>
                <a href="list-events.jsp" class="btn btn-secondary">
                    <i class="fas fa-arrow-left me-2"></i> Back to Events
                </a>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <div class="content-wrapper">
            <form action="AdminEventController" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="eventId" value="<%= event.getEventId() %>">

                <div class="mb-3">
                    <label for="eventName" class="form-label">Event Name</label>
                    <input type="text" class="form-control" id="eventName" name="eventName" 
                           value="<%= event.getEventName() %>" required>
                    <div class="invalid-feedback">
                        Please enter an event name
                    </div>
                </div>

                <div class="mb-3">
                    <label for="price" class="form-label">Price (₹)</label>
                    <input type="number" class="form-control" id="price" name="price" 
                           value="<%= event.getPrice() %>" step="0.01" min="0" required>
                    <div class="invalid-feedback">
                        Please enter a valid price
                    </div>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-update">
                        <i class="fas fa-save me-2"></i> Update Event
                    </button>
                    <a href="list-events.jsp" class="btn btn-secondary">
                        <i class="fas fa-times me-2"></i> Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        (function() {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.from(forms).forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
        })()
    </script>
</body>
</html> 