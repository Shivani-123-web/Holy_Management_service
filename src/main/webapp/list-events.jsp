<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event"%>
<%@ page import="dao.EventDAO"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Events - Bhadrakali Temple</title>
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
        }
        .table-header {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px 8px 0 0;
            border-bottom: 2px solid #dee2e6;
            margin: -20px -20px 20px -20px;
        }
        .btn-add-new {
            background-color: #0d6efd;
            color: white;
            padding: 8px 20px;
            border-radius: 5px;
            border: none;
            transition: all 0.3s;
        }
        .btn-add-new:hover {
            background-color: #0b5ed7;
            color: white;
            transform: translateY(-2px);
        }
        .action-buttons .btn {
            margin: 0 3px;
        }
        .table > :not(caption) > * > * {
            padding: 12px 15px;
        }
        .btn-back {
            background-color: #6c757d;
            color: white;
            transition: all 0.3s;
        }
        .btn-back:hover {
            background-color: #5a6268;
            color: white;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Dashboard Header -->
    <div class="dashboard-header">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">
                    <i class="fas fa-calendar-alt me-2"></i> Event Management
                </h2>
                <a href="admin.jsp" class="btn btn-back">
                    <i class="fas fa-arrow-left me-2"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <div class="content-wrapper">
            <div class="table-header d-flex justify-content-between align-items-center">
                <h4 class="mb-0">Temple Events</h4>
                <button type="button" class="btn btn-add-new" data-bs-toggle="modal" data-bs-target="#addEventModal">
                    <i class="fas fa-plus me-2"></i> Add New Event
                </button>
            </div>

            <!-- Events Table -->
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="table-light">
                        <tr>
                            <th width="5%">No.</th>
                            <th>Event Name</th>
                            <th>Price (₹)</th>
                            <th class="text-center" width="20%">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            try {
                                EventDAO eventDAO = new EventDAO();
                                List<Event> events = eventDAO.getAllActiveEvents();
                                int serialNumber = 1; // Initialize counter
                                for (Event event : events) {
                        %>
                        <tr>
                            <td><%= serialNumber++ %></td>  <!-- Display serial number instead of ID -->
                            <td><%= event.getEventName() %></td>
                            <td>₹<%= event.getPrice() %></td>
                            <td class="text-center action-buttons">
                                <button class="btn btn-sm btn-warning" onclick="editEvent(<%= event.getEventId() %>)">
                                    <i class="fas fa-edit"></i> Edit
                                </button>
                                <button class="btn btn-sm btn-danger" onclick="deleteEvent(<%= event.getEventId() %>)">
                                    <i class="fas fa-trash"></i> Delete
                                </button>
                            </td>
                        </tr>
                        <%
                                }
                                if (events.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="4" class="text-center">
                                <i class="fas fa-info-circle text-info"></i> No events found
                            </td>
                        </tr>
                        <%
                                }
                            } catch (Exception e) {
                        %>
                        <tr>
                            <td colspan="4" class="text-center text-danger">
                                <i class="fas fa-exclamation-circle"></i> 
                                Error loading events: <%= e.getMessage() %>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Add Event Modal -->
    <div class="modal fade" id="addEventModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-plus-circle me-2"></i> Add New Event
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="AdminEventController" method="post" class="needs-validation" novalidate>
                    <div class="modal-body">
                        <input type="hidden" name="action" value="add">
                        
                        <div class="mb-3">
                            <label class="form-label">Event Name</label>
                            <input type="text" class="form-control" name="eventName" required>
                            <div class="invalid-feedback">Please enter an event name</div>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Price (₹)</label>
                            <input type="number" class="form-control" name="price" 
                                   step="0.01" min="0" required>
                            <div class="invalid-feedback">Please enter a valid price</div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-2"></i> Cancel
                        </button>
                        <button type="submit" class="btn btn-add-new">
                            <i class="fas fa-save me-2"></i> Save Event
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    
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

        // Edit event function
        function editEvent(eventId) {
            window.location.href = 'edit-event.jsp?id=' + eventId;
        }

        // Delete event function with confirmation
        function deleteEvent(eventId) {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#dc3545',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Show loading state
                    Swal.fire({
                        title: 'Deleting...',
                        text: 'Please wait',
                        allowOutsideClick: false,
                        showConfirmButton: false,
                        willOpen: () => {
                            Swal.showLoading();
                        }
                    });
                    
                    // Redirect to delete action
                    window.location.href = 'AdminEventController?action=delete&id=' + eventId;
                }
            });
        }

        // Show success message if present
        <% if (request.getSession().getAttribute("successMessage") != null) { %>
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: '<%= request.getSession().getAttribute("successMessage") %>',
                timer: 3000
            });
            <% request.getSession().removeAttribute("successMessage"); %>
        <% } %>

        // Show error message if present
        <% if (request.getSession().getAttribute("errorMessage") != null) { %>
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: '<%= request.getSession().getAttribute("errorMessage") %>'
            });
            <% request.getSession().removeAttribute("errorMessage"); %>
        <% } %>
    </script>
</body>
</html> 