<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Darshan</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .booking-form {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="booking-form">
                    <h2 class="text-center mb-4">Book ${darshan.darshan_type}</h2>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/DarshanController" method="post">
                        <input type="hidden" name="action" value="proceed_payment">
                        <input type="hidden" name="darshan_id" value="${darshan.darshan_id}">
                        
                        <div class="mb-3">
                            <label class="form-label">Darshan Type</label>
                            <input type="text" class="form-control" value="${darshan.darshan_type}" readonly>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Date</label>
                            <input type="date" name="bookingDate" class="form-control" required 
                                   min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Number of Persons</label>
                            <input type="number" name="persons" class="form-control" 
                                   min="1" max="${darshan.max_persons}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Time Slot</label>
                            <input type="text" class="form-control" value="${darshan.time_slot}" readonly>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Total Amount</label>
                            <input type="text" class="form-control" 
                                   value="₹${darshan.price}" readonly>
                        </div>
                        
                        <button type="submit" class="btn btn-primary w-100">Proceed to Payment</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 