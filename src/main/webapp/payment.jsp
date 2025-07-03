<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment - Bhadrakali Temple</title>
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
    <div class="container">
        <div class="payment-container">
            <h2 class="text-center mb-4">QR Code Payment</h2>

            <!-- Booking Details -->
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Booking Summary</h5>
                    <p class="card-text">
                        <strong>Booking ID:</strong> ${sessionScope.bookingId}<br>
                        <strong>Seva Name:</strong> ${sevaName}<br>
                        <strong>Amount:</strong> ₹${amount}
                    </p>
                </div>
            </div>

            <!-- QR Code Section -->
            <div class="qr-code">
                <h5><i class="fas fa-qrcode"></i> Scan QR Code to Pay</h5>
                <div class="qr-code-image">
                    <!-- Inline SVG QR Code -->
                    <img src="${pageContext.request.contextPath}/images/QR Code.png" alt="Payment QR Code" style="width: 100%; height: 100%;">
                        <!-- QR Code for UPI -->
                        <path fill="#000000" d="M0 0h7v7h-7zM8 0h1v1h-1zM10 0h2v1h-2zM13 0h1v1h-1zM15 0h1v1h-1zM17 0h1v1h-1zM22 0h7v7h-7zM0 1h1v5h-1zM6 1h1v5h-1zM8 1h1v1h-1zM10 1h1v2h-1zM12 1h3v1h-3zM16 1h3v2h-3zM20 1h1v1h-1zM22 1h1v5h-1zM28 1h1v5h-1zM8 2h1v3h-1zM11 2h1v1h-1zM13 2h2v2h-2zM19 2h1v2h-1zM10 3h1v1h-1zM15 3h1v2h-1zM17 3h2v1h-2zM20 3h1v2h-1zM9 4h1v1h-1zM11 4h2v1h-2zM16 4h1v1h-1zM18 4h1v1h-1zM0 6h1v1h-1zM6 6h1v1h-1zM8 6h4v1h-4zM13 6h1v1h-1zM15 6h2v1h-2zM19 6h2v1h-2zM22 6h1v1h-1zM28 6h1v1h-1zM0 8h2v2h-2zM3 8h3v1h-3zM7 8h2v1h-2zM10 8h1v1h-1zM12 8h2v3h-2zM15 8h1v1h-1zM19 8h2v1h-2zM22 8h2v1h-2zM25 8h2v1h-2zM0 9h1v1h-1zM4 9h2v1h-2zM9 9h1v2h-1zM14 9h1v1h-1zM16 9h2v2h-2zM20 9h2v1h-2zM23 9h5v1h-5zM2 10h4v1h-4zM7 10h1v1h-1zM10 10h2v1h-2zM14 10h2v3h-2zM19 10h1v1h-1zM21 10h1v1h-1zM24 10h1v2h-1zM26 10h2v1h-2zM0 11h1v2h-1zM2 11h1v1h-1zM4 11h2v1h-2zM8 11h1v2h-1zM10 11h1v1h-1zM18 11h1v2h-1zM20 11h1v2h-1zM22 11h1v1h-1zM26 11h2v1h-2zM7 12h1v1h-1zM11 12h1v2h-1zM13 12h1v1h-1zM16 12h1v2h-1zM22 12h2v2h-2zM25 12h1v1h-1zM27 12h2v1h-2zM1 13h1v1h-1zM3 13h3v1h-3zM7 13h1v1h-1zM9 13h1v2h-1zM12 13h1v3h-1zM19 13h1v1h-1zM24 13h1v2h-1zM26 13h1v1h-1zM0 14h1v1h-1zM2 14h1v1h-1zM4 14h2v1h-2zM7 14h1v1h-1zM13 14h3v1h-3zM17 14h2v1h-2zM20 14h2v2h-2zM26 14h2v1h-2zM0 15h2v1h-2zM6 15h2v2h-2zM10 15h2v2h-2zM14 15h1v2h-1zM16 15h1v1h-1zM18 15h1v2h-1zM22 15h2v2h-2zM25 15h1v1h-1zM27 15h2v1h-2zM3 16h2v1h-2zM8 16h1v1h-1zM15 16h1v1h-1zM17 16h1v1h-1zM24 16h1v1h-1zM26 16h1v1h-1zM0 17h1v1h-1zM2 17h5v1h-5zM9 17h1v1h-1zM13 17h4v1h-4zM19 17h3v1h-3zM23 17h1v1h-1zM25 17h1v1h-1zM0 18h1v1h-1zM8 18h3v1h-3zM12 18h1v1h-1zM14 18h1v1h-1zM17 18h2v1h-2zM21 18h1v1h-1zM24 18h1v1h-1zM26 18h2v1h-2zM0 19h1v2h-1zM2 19h1v3h-1zM4 19h2v1h-2zM8 19h1v1h-1zM11 19h1v2h-1zM13 19h6v1h-6zM20 19h1v1h-1zM22 19h6v1h-6zM7 20h1v1h-1zM9 20h1v1h-1zM14 20h2v1h-2zM17 20h2v1h-2zM20 20h2v1h-2zM23 20h1v1h-1zM25 20h1v2h-1zM27 20h2v1h-2zM4 21h1v1h-1zM6 21h1v1h-1zM8 21h2v1h-2zM12 21h2v1h-2zM15 21h4v1h-4zM21 21h2v1h-2zM24 21h1v1h-1zM27 21h1v1h-1zM0 22h7v7h-7zM8 22h2v1h-2zM11 22h1v2h-1zM13 22h1v2h-1zM15 22h2v1h-2zM18 22h3v1h-3zM22 22h7v7h-7zM0 23h1v5h-1zM6 23h1v5h-1zM8 23h1v1h-1zM10 23h1v2h-1zM14 23h2v2h-2zM17 23h1v1h-1zM19 23h2v1h-2zM22 23h1v5h-1zM28 23h1v5h-1zM8 24h1v1h-1zM12 24h1v1h-1zM16 24h1v2h-1zM18 24h1v2h-1zM20 24h1v1h-1zM8 25h3v1h-3zM13 25h2v2h-2zM19 25h2v1h-2zM8 26h2v1h-2zM11 26h1v1h-1zM15 26h1v2h-1zM17 26h1v1h-1zM19 26h2v1h-2zM0 28h7v1h-7zM8 28h3v1h-3zM12 28h2v1h-2zM16 28h5v1h-5zM22 28h7v1h-7z"/>
                    </svg>
                </div>
                <div class="upi-info">
                    <p class="mb-1"><strong>UPI ID:</strong> temple@upi</p>
                    <p class="mb-0"><strong>Amount:</strong> ₹${amount}</p>
                </div>
            </div>

            <!-- Payment Instructions -->
            <div class="payment-instructions">
                <h5><i class="fas fa-info-circle"></i> Payment Instructions</h5>
                <ol class="mb-0">
                    <li>Open any UPI app (Google Pay, PhonePe, Paytm, etc.)</li>
                    <li>Scan the QR code shown above</li>
                    <li>Enter amount: ₹${amount}</li>
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
            <form action="PaymentController" method="post" id="paymentForm">
                <input type="hidden" name="bookingId" value="${sessionScope.bookingId}">
                <input type="hidden" name="transactionId" value="${sessionScope.transactionId}">
                <input type="hidden" name="amount" value="${amount}">
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg" id="confirmButton">
                        <i class="fas fa-check-circle"></i> I have made the payment
                    </button>
                    <a href="SevaController" class="btn btn-secondary" id="cancelButton">
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
                        window.location.href = "SevaController";
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