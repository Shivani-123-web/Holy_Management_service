<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event"%>
<%@ page import="dao.EventDAO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Event</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .booking-form {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .form-header {
            background: #007bff;
            color: white;
            padding: 15px;
            margin: -20px -20px 20px -20px;
            border-radius: 10px 10px 0 0;
            text-align: center;
        }
        .form-row {
            display: flex;
            gap: 15px;
            margin-bottom: 15px;
        }
        .form-col {
            flex: 1;
        }
        .price-tag {
            background: #28a745;
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
            font-size: 1.2em;
            display: inline-block;
        }
        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
        }
        .invalid-feedback {
            display: none;
            color: #dc3545;
            font-size: 0.875em;
            margin-top: 0.25rem;
        }
        .was-validated .form-control:invalid ~ .invalid-feedback {
            display: block;
        }
    </style>
</head>
<body class="bg-light">
    <%
        // Get event details
        int eventId = Integer.parseInt(request.getParameter("id"));
        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(eventId);
        
        // Get user details from session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <div class="container mt-4 mb-4">
        <div class="booking-form">
            <div class="form-header">
                <h2 class="mb-0">Book Event: <%= event.getEventName() %></h2>
            </div>
            
            <form action="EventBookingController" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="eventId" value="<%= event.getEventId() %>">
                <input type="hidden" name="eventPrice" value="<%= event.getPrice() %>">
                
                <div class="form-row">
                    <div class="form-col">
                        <label for="fullName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" 
                               pattern="[A-Za-z\s]{1,50}" required>
                        <div class="invalid-feedback">
                            Please enter a valid name (letters and spaces only, max 50 characters)
                        </div>
                    </div>
                    <div class="form-col">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" 
                               maxlength="100" required>
                        <div class="invalid-feedback">
                            Please enter a valid email address
                        </div>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-col">
                        <label for="phoneNumber" class="form-label">Phone Number</label>
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" 
                               pattern="[0-9]{10}" maxlength="10" required>
                        <div class="invalid-feedback">
                            Please enter a valid 10-digit phone number
                        </div>
                    </div>
                    <div class="form-col">
                        <label for="bookingDate" class="form-label">Booking Date</label>
                        <input type="date" class="form-control" id="bookingDate" name="bookingDate" required>
                        <div class="invalid-feedback">
                            Please select a valid date
                        </div>
                    </div>
                </div>
                
                <div class="text-center mt-3">
                    <label class="form-label d-block">Amount to Pay</label>
                    <div class="price-tag">₹<%= event.getPrice() %></div>
                </div>
                
                <div class="btn-group">
                    <button type="submit" class="btn btn-primary flex-grow-1">
                        <i class="fas fa-credit-card"></i> Proceed to Payment
                    </button>
                    <a href="events.jsp" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Back
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/your-font-awesome-kit.js"></script>
    <script>
        // Phone number validation
        const phoneInput = document.getElementById('phoneNumber');
        phoneInput.addEventListener('input', function(e) {
            // Remove any non-digit characters
            this.value = this.value.replace(/\D/g, '');
            
            // Limit to 10 digits
            if (this.value.length > 10) {
                this.value = this.value.slice(0, 10);
            }
        });

        // Form validation
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms)
                .forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
        })()

        // Set minimum date to today
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('bookingDate').min = today;
    </script>
</body>
</html>