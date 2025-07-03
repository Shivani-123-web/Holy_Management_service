<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Darshan" %>
<%@ page import="dao.DarshanDAO" %>
<%@ page import="java.sql.SQLException" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Darshans - Bhadrakali Temple</title>
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
        .btn-group .btn {
            padding: 0.25rem 0.5rem;
            margin: 0;
            border-radius: 0;
        }
        .btn-group .btn:first-child {
            border-top-left-radius: 4px;
            border-bottom-left-radius: 4px;
        }
        .btn-group .btn:last-child {
            border-top-right-radius: 4px;
            border-bottom-right-radius: 4px;
        }
        .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Dashboard Header -->
    <div class="dashboard-header">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">
                    <i class="fas fa-pray me-2"></i> Darshan Management
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
                <h4 class="mb-0">Temple Darshans</h4>
                <button type="button" class="btn btn-add-new" data-bs-toggle="modal" data-bs-target="#addDarshanModal">
                    <i class="fas fa-plus me-2"></i> Add New Darshan
                </button>
            </div>

            <!-- Darshans Table -->
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Darshan Type</th>
                            <th>Description</th>
                            <th>Time Slot</th>
                            <th>Price (₹)</th>
                            <th>Max Persons</th>
                            <th>Status</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            DarshanDAO dao = null;
                            try {
                                dao = new DarshanDAO();
                                List<Darshan> darshans = dao.getAllDarshans();
                                if(darshans != null && !darshans.isEmpty()) {
                                    for(Darshan darshan : darshans) {
                        %>
                        <tr>
                            <td><%= darshan.getDarshan_id() %></td>
                            <td><%= darshan.getDarshan_type() %></td>
                            <td><%= darshan.getDescription() %></td>
                            <td><%= darshan.getTime_slot() %></td>
                            <td>₹<%= darshan.getPrice() %></td>
                            <td><%= darshan.getMax_persons() %></td>
                            <td>
                                <span class="badge <%= darshan.getStatus().equals("active") ? "bg-success" : "bg-danger" %>">
                                    <%= darshan.getStatus() %>
                                </span>
                            </td>
                            <td class="text-center">
                                <div class="btn-group" role="group">
                                    <a href="AdminDarshanController?action=edit&id=<%= darshan.getDarshan_id() %>" 
                                       class="btn btn-sm btn-warning">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <button onclick="deleteDarshan(<%= darshan.getDarshan_id() %>)" 
                                            class="btn btn-sm btn-danger">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <%
                                    }
                                } else {
                        %>
                        <tr>
                            <td colspan="8" class="text-center">No darshans found</td>
                        </tr>
                        <%
                                }
                            } catch(SQLException e) {
                        %>
                        <tr>
                            <td colspan="8" class="text-center text-danger">
                                <i class="fas fa-exclamation-circle"></i> 
                                Database error: <%= e.getMessage() %>
                            </td>
                        </tr>
                        <%
                            } finally {
                                if (dao != null) {
                                    try {
                                        dao.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Add Darshan Modal -->
    <div class="modal fade" id="addDarshanModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-plus-circle me-2"></i> Add New Darshan
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="AdminDarshanController" method="post" class="needs-validation" novalidate>
                    <div class="modal-body">
                        <input type="hidden" name="action" value="add">
                        
                        <div class="mb-3">
                            <label class="form-label">Darshan Type</label>
                            <input type="text" class="form-control" name="darshan_type" required>
                            <div class="invalid-feedback">Please enter darshan type</div>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea class="form-control" name="description" rows="3" required></textarea>
                            <div class="invalid-feedback">Please enter description</div>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Time Slot</label>
                            <input type="text" class="form-control" name="time_slot" 
                                   placeholder="e.g., 05:00 AM - 07:00 AM" required>
                            <div class="invalid-feedback">Please enter time slot</div>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Price (₹)</label>
                            <input type="number" class="form-control" name="price" 
                                   step="0.01" min="0" required>
                            <div class="invalid-feedback">Please enter a valid price</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Maximum Persons</label>
                            <input type="number" class="form-control" name="max_persons" 
                                   min="1" required>
                            <div class="invalid-feedback">Please enter maximum persons allowed</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Status</label>
                            <select class="form-control" name="status" required>
                                <option value="active">Active</option>
                                <option value="inactive">Inactive</option>
                            </select>
                            <div class="invalid-feedback">Please select a status</div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-2"></i> Cancel
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i> Save Darshan
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

        // Delete darshan function with confirmation
        function deleteDarshan(darshanId) {
            Swal.fire({
                title: 'Delete Darshan?',
                text: 'This action cannot be undone.',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#dc3545',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = 'AdminDarshanController?action=delete&id=' + darshanId;
                }
            });
        }

        // Show success/error messages
        <% if (request.getSession().getAttribute("successMessage") != null) { %>
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: '<%= request.getSession().getAttribute("successMessage") %>',
                timer: 3000
            });
            <% request.getSession().removeAttribute("successMessage"); %>
        <% } %>

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