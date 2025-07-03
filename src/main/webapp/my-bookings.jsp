<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Bookings - Bhadrakali Temple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .booking-section {
            margin-bottom: 30px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .booking-card {
            margin-bottom: 20px;
            border: 1px solid #dee2e6;
            border-radius: 8px;
        }
        .booking-header {
            background-color: #f8f9fa;
            padding: 15px;
            border-bottom: 1px solid #dee2e6;
            border-radius: 8px 8px 0 0;
        }
        .booking-body {
            padding: 20px;
        }
        .status-badge {
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9em;
        }
        .status-PENDING {
            background-color: #ffd700;
            color: #000;
        }
        .status-COMPLETED {
            background-color: #28a745;
            color: #fff;
        }
        .status-CANCELLED {
            background-color: #dc3545;
            color: #fff;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>My Bookings</h2>
            <a href="devotee.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <!-- Seva Bookings Section -->
        <div class="booking-section">
            <h3 class="mb-4">
                <i class="fas fa-pray text-primary"></i> Seva Bookings
            </h3>
            <c:choose>
                <c:when test="${empty sevaBookings}">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle"></i> No seva bookings found.
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${sevaBookings}" var="booking">
                        <div class="booking-card">
                            <div class="booking-header">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <h5 class="mb-0">Booking #${booking.bookingId}</h5>
                                    </div>
                                    <div class="col-md-6 text-md-end">
                                        <span class="badge status-badge status-${fn:toLowerCase(booking.bookingStatus)}">
                                            ${booking.bookingStatus}
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="booking-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Full Name:</strong> ${booking.fullName}</p>
                                        <p><strong>Booking Date:</strong> ${booking.bookingDate}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Time Slot:</strong> ${booking.timeSlot}</p>
                                        <p><strong>Amount:</strong> ₹${booking.totalAmount}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Darshan Bookings Section -->
        <div class="booking-section">
            <h3 class="mb-4">
                <i class="fas fa-om text-warning"></i> Darshan Bookings
            </h3>
            <c:choose>
                <c:when test="${empty darshanBookings}">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle"></i> No darshan bookings found.
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${darshanBookings}" var="booking">
                        <div class="booking-card">
                            <div class="booking-header">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <h5 class="mb-0">Booking #${booking.bookingId}</h5>
                                    </div>
                                    <div class="col-md-6 text-md-end">
                                        <span class="badge status-badge status-${booking.status}">
                                            ${booking.status}
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="booking-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Date:</strong> ${booking.bookingDate}</p>
                                        <p><strong>Number of Persons:</strong> ${booking.persons}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Amount:</strong> ₹${booking.totalAmount}</p>
                                        <p><strong>Transaction ID:</strong> ${booking.transactionId}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Event Bookings Section -->
        <div class="booking-section">
            <h3 class="mb-4">
                <i class="fas fa-calendar-alt text-success"></i> Event Bookings
            </h3>
            <c:choose>
                <c:when test="${empty eventBookings}">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle"></i> No event bookings found.
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${eventBookings}" var="booking">
                        <div class="booking-card">
                            <div class="booking-header">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <h5 class="mb-0">Booking #${booking.bookingId}</h5>
                                    </div>
                                    <div class="col-md-6 text-md-end">
                                        <span class="badge status-badge status-${booking.paymentStatus}">
                                            ${booking.paymentStatus}
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="booking-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Event Name:</strong> ${booking.eventName}</p>
                                        <p><strong>Full Name:</strong> ${booking.fullName}</p>
                                        <p><strong>Date:</strong> ${booking.bookingDate}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Amount:</strong> ₹${booking.amount}</p>
                                        <p><strong>Payment ID:</strong> ${booking.paymentId}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 