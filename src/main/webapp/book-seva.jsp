<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Seva" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Seva - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .booking-form {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        .required-field::after {
            content: "*";
            color: red;
            margin-left: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="booking-form">
            <h2 class="text-center mb-4">Book Seva</h2>
            
            <%
                Seva seva = (Seva) request.getAttribute("seva");
                if (seva != null) {
            %>
            
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Selected Seva Details</h5>
                    <p class="card-text">
                        <strong>Seva Name:</strong> <%= seva.getSevaName() %><br>
                        <strong>Timing:</strong> <%= seva.getTiming() %><br>
                        <strong>Price:</strong> ₹<%= seva.getPrice() %>
                    </p>
                </div>
            </div>

            <form action="SevaBookingController" method="post">
                <input type="hidden" name="sevaId" value="<%= seva.getSevaId() %>">
                <input type="hidden" name="sevaPrice" value="<%= seva.getPrice() %>">

                <!-- Personal Information -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">Personal Information</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="fullName" class="form-label required-field">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="phoneNumber" class="form-label required-field">Phone Number</label>
                                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" 
                                       pattern="[0-9]{10}" title="Please enter a valid 10-digit phone number" required>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Booking Details -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">Booking Details</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="bookingDate" class="form-label required-field">Booking Date</label>
                                <input type="date" class="form-control" id="bookingDate" name="bookingDate" 
                                       min="<%= java.time.LocalDate.now() %>" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="timeSlot" class="form-label required-field">Preferred Time</label>
                                <input type="time" class="form-control" id="timeSlot" name="timeSlot" required>
                                <small class="text-muted">Please enter your preferred time for the seva</small>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Payment Information -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">Payment Details</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong>Total Amount:</strong> ₹<%= seva.getPrice() %></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <a href="SevaController" class="btn btn-secondary me-md-2">
                        <i class="fas fa-arrow-left"></i> Back
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-check"></i> Confirm Booking
                    </button>
                </div>
            </form>
            <% } else { %>
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-circle"></i> Seva details not found.
                    <br>
                    <a href="SevaController" class="btn btn-primary mt-3">Return to Seva List</a>
                </div>
            <% } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Set minimum date to today
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('bookingDate').setAttribute('min', today);
    </script>
</body>
</html>