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
        }
        .booking-details p {
            margin-bottom: 15px;
            font-size: 16px;
        }
        .booking-details strong {
            display: inline-block;
            width: 120px;
        }
        .action-buttons {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .action-buttons .btn {
            padding: 10px 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-container">
            <i class="fas fa-check-circle success-icon"></i>
            <h2 class="mb-4">Payment Successful!</h2>
            
            <div class="booking-details">
                <h4 class="text-center mb-4">Booking Details</h4>
                <p><strong>Booking ID:</strong> ${sessionScope.successBookingId}</p>
                <p><strong>Transaction ID:</strong> ${sessionScope.successTransactionId}</p>
                <p><strong>Amount Paid:</strong> ₹${sessionScope.successAmount}</p>
            </div>
            
            <div class="action-buttons">
                <a href="devotee.jsp" class="btn btn-primary">
                    <i class="fas fa-home"></i> Back to Home
                </a>
                <a href="my-bookings.jsp" class="btn btn-primary">
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