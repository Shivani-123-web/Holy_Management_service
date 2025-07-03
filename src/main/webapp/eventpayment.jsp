<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Event Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .payment-card {
            max-width: 700px;
            margin: 30px auto;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            background-color: #fff;
        }
        .qr-section {
            text-align: center;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
            margin: 20px 0;
        }
        .qr-code-image {
            width: 200px;
            height: 200px;
            margin: 20px auto;
            background-color: #fff;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .booking-summary {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
        }
        .booking-summary h4 {
            color: #0d6efd;
            margin-bottom: 15px;
        }
        .amount-display {
            font-size: 24px;
            color: #198754;
            font-weight: bold;
            padding: 10px 20px;
            background-color: #e8f5e9;
            border-radius: 8px;
            display: inline-block;
            margin: 10px 0;
        }
        .btn-lg {
            padding: 15px 25px;
            font-size: 18px;
        }
        .btn i {
            margin-right: 8px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container">
        <div class="payment-card">
            <h3 class="text-center mb-4">Complete Your Payment</h3>
            
            <div class="booking-summary">
                <h4><i class="fas fa-info-circle"></i> Booking Summary</h4>
                <div class="row">
                    <div class="col-12">
                        <p><strong>Booking ID:</strong> <%= session.getAttribute("bookingId") %></p>
                        <p><strong>Event Name:</strong> <%= session.getAttribute("eventName") %></p>
                        <p><strong>Amount:</strong> <span class="amount-display">₹<%= session.getAttribute("amount") %></span></p>
                    </div>
                </div>
            </div>

            <div class="qr-section">
                <h5 class="mb-3">Scan QR Code to Pay</h5>
                <div class="qr-code-image">
                    <img src="images/QR Code.png" alt="Payment QR Code" style="width: 200px; height: 200px; object-fit: contain;">
                </div>
                <p class="text-muted">Please scan this QR code using any UPI payment app</p>
            </div>

            <form id="paymentForm" action="EventPaymentController" method="post">
                <input type="hidden" name="bookingId" value="<%= session.getAttribute("bookingId") %>">
                <input type="hidden" name="paymentStatus" value="SUCCESS">
                
                <div class="d-grid gap-3">
                    <button type="submit" class="btn btn-primary btn-lg" id="confirmButton">
                        <i class="fas fa-check-circle"></i> I have made the payment
                    </button>
                    <a href="events.jsp" class="btn btn-secondary btn-lg" id="cancelButton">
                        <i class="fas fa-times-circle"></i> Cancel Payment
                    </a>
                </div>
            </form>

            <div class="text-center mt-4">
                <p class="text-muted">
                    <i class="fas fa-lock"></i> 
                    Your payment is secure and encrypted
                </p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form submission confirmation
        document.getElementById("paymentForm").onsubmit = function(e) {
            e.preventDefault(); // Prevent immediate form submission
            
            Swal.fire({
                title: 'Confirm Payment',
                text: 'Have you completed the payment?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, I have paid',
                cancelButtonText: 'No, not yet'
            }).then((result) => {
                if (result.isConfirmed) {
                    this.submit(); // Submit the form if confirmed
                }
            });
        };

        // Cancel button confirmation
        document.getElementById("cancelButton").onclick = function(e) {
            e.preventDefault(); // Prevent immediate navigation
            
            Swal.fire({
                title: 'Cancel Payment',
                text: 'Are you sure you want to cancel this payment?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, cancel payment',
                cancelButtonText: 'No, continue payment'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = this.href; // Navigate if confirmed
                }
            });
        };
    </script>

    <!-- Add SweetAlert2 for better-looking alerts -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>