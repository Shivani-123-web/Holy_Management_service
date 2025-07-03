<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Darshan Payment - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .payment-container {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
            background-color: #fff;
        }
        .qr-code {
            text-align: center;
            margin: 20px 0;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            border: 2px dashed #ddd;
        }
        .qr-code-image {
            width: 200px;
            height: 200px;
            margin: 20px auto;
            background-color: #fff;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .payment-instructions {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .payment-instructions ol {
            padding-left: 20px;
        }
        .payment-instructions li {
            margin-bottom: 10px;
        }
        .timer {
            font-size: 24px;
            color: #dc3545;
            text-align: center;
            margin: 20px 0;
            font-weight: bold;
        }
        .upi-info {
            text-align: center;
            margin: 15px 0;
            padding: 10px;
            background-color: #e9ecef;
            border-radius: 4px;
        }
        .btn-primary {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-primary:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
            border-color: #545b62;
        }
        .card {
            border: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .card-title {
            color: #333;
            font-size: 1.2rem;
            margin-bottom: 15px;
        }
        .card-text {
            color: #666;
            line-height: 1.6;
        }
        .expired {
            color: #dc3545;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <%
        // Check if user is logged in
        if(session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // Check if booking details exist in session
        if(session.getAttribute("bookingId") == null || 
           session.getAttribute("darshan") == null || 
           session.getAttribute("totalAmount") == null) {
            response.sendRedirect(request.getContextPath() + "/darshan-list.jsp");
            return;
        }
    %>
    <div class="container">
        <div class="payment-container">
            <h2 class="text-center mb-4">QR Code Payment</h2>

            <!-- Booking Details -->
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Darshan Booking Summary</h5>
                    <p class="card-text">
                        <strong>Booking ID:</strong> ${sessionScope.bookingId}<br>
                        <strong>Darshan Type:</strong> ${darshan.darshan_type}<br>
                        <strong>Date:</strong> ${bookingDate}<br>
                        <strong>Time Slot:</strong> ${darshan.time_slot}<br>
                        <strong>Number of Persons:</strong> ${persons}<br>
                        <strong>Amount:</strong> ₹${totalAmount}
                    </p>
                </div>
            </div>

            <!-- QR Code Section -->
            <div class="qr-code">
                <h5><i class="fas fa-qrcode"></i> Scan QR Code to Pay</h5>
                <div class="qr-code-image">
                    <img src="${pageContext.request.contextPath}/images/QR Code.png" alt="Payment QR Code" style="width: 100%; height: 100%;">
                </div>
                <div class="upi-info">
                    <p class="mb-1"><strong>UPI ID:</strong> temple@upi</p>
                    <p class="mb-0"><strong>Amount:</strong> ₹${totalAmount}</p>
                </div>
            </div>

            <!-- Payment Instructions -->
            <div class="payment-instructions">
                <h5><i class="fas fa-info-circle"></i> Payment Instructions</h5>
                <ol class="mb-0">
                    <li>Open any UPI app (Google Pay, PhonePe, Paytm, etc.)</li>
                    <li>Scan the QR code shown above</li>
                    <li>Enter amount: ₹${totalAmount}</li>
                    <li>Add reference: Booking ID ${sessionScope.bookingId}</li>
                    <li>Complete the payment</li>
                    <li>Click on "I have made the payment" button below</li>
                </ol>
            </div>

            <!-- Timer -->
            <div class="timer" id="timer">
                10:00
            </div>

            <!-- Payment Verification Form -->
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <form action="${pageContext.request.contextPath}/DarshanController" method="post" id="paymentForm">
                <input type="hidden" name="action" value="verify_payment">
                <input type="hidden" name="bookingId" value="${sessionScope.bookingId}">
                <input type="hidden" name="darshan_id" value="${darshan.darshan_id}">
                <input type="hidden" name="bookingDate" value="${bookingDate}">
                <input type="hidden" name="persons" value="${persons}">
                <input type="hidden" name="totalAmount" value="${totalAmount}">
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg" id="confirmButton">
                        <i class="fas fa-check-circle"></i> I have made the payment
                    </button>
                    <a href="DarshanController" class="btn btn-secondary" id="cancelButton">
                        <i class="fas fa-times-circle"></i> Cancel Payment
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Timer functionality
        function startTimer(duration, display) {
            var timer = duration, minutes, seconds;
            var interval = setInterval(function () {
                minutes = parseInt(timer / 60, 10);
                seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                display.textContent = minutes + ":" + seconds;

                if (--timer < 0) {
                    clearInterval(interval);
                    display.textContent = "Time Expired!";
                    display.classList.add("expired");
                    
                    // Disable the confirm button
                    document.getElementById("confirmButton").disabled = true;
                    
                    // Redirect after 3 seconds
                    setTimeout(function() {
                        window.location.href = "DarshanController";
                    }, 3000);
                }
            }, 1000);
        }

        // Form submission confirmation
        document.getElementById("paymentForm").onsubmit = function(e) {
            var confirmed = confirm("Have you completed the payment? Click OK to confirm.");
            if (!confirmed) {
                e.preventDefault();
                return false;
            }
            return true;
        };

        // Cancel button confirmation
        document.getElementById("cancelButton").onclick = function(e) {
            var confirmed = confirm("Are you sure you want to cancel this payment?");
            if (!confirmed) {
                e.preventDefault();
                return false;
            }
            return true;
        };

        // Start timer when page loads
        window.onload = function () {
            var tenMinutes = 60 * 10,
                display = document.querySelector('#timer');
            startTimer(tenMinutes, display);
        };
    </script>
</body>
</html> 