<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Success - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .success-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            text-align: center;
        }
        .success-icon {
            color: #28a745;
            font-size: 64px;
            margin-bottom: 20px;
        }
        .booking-details {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 0;
            text-align: left;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .booking-details p {
            margin-bottom: 15px;
            font-size: 16px;
            border-bottom: 1px solid #dee2e6;
            padding-bottom: 10px;
        }
        .booking-details p:last-child {
            border-bottom: none;
        }
        .booking-details strong {
            display: inline-block;
            width: 120px;
            color: #495057;
        }
        .action-buttons {
            margin-top: 30px;
        }
        .action-buttons .btn {
            margin: 0 10px;
            padding: 10px 25px;
        }
        .temple-name {
            color: #6c757d;
            font-size: 1.2em;
            margin-bottom: 20px;
        }
        .amount {
            color: #28a745;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-container">
            <i class="fas fa-check-circle success-icon"></i>
            <h2 class="mb-3">Payment Successful!</h2>
            <p class="temple-name">Sri Bhadrakali Temple</p>
            
            <div class="booking-details">
                <h4 class="text-center mb-4">Booking Details</h4>
                <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
                <p><strong>Event Name:</strong> ${booking.eventName}</p>
                <p><strong>Booking Date:</strong> ${booking.bookingDate}</p>
                <p><strong>Amount:</strong> <span class="amount">₹${booking.amount}</span></p>
            </div>
            
            <div class="action-buttons">
                <a href="devotee.jsp" class="btn btn-primary">
                    <i class="fas fa-home"></i> Back to Home
                </a>
                <a href="mybookings.jsp" class="btn btn-secondary">
                    <i class="fas fa-list"></i> My Bookings
                </a>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
</body>
</html>