<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Darshan - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Edit Darshan</h4>
                        <a href="list-darshans.jsp" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
                        </a>
                    </div>
                    <div class="card-body">
                        <form action="AdminDarshanController" method="post" class="needs-validation" novalidate>
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="darshan_id" value="${darshan.darshan_id}">
                            
                            <div class="mb-3">
                                <label class="form-label">Darshan Type</label>
                                <input type="text" class="form-control" name="darshan_type" 
                                       value="${darshan.darshan_type}" required>
                                <div class="invalid-feedback">Please enter darshan type</div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Description</label>
                                <textarea class="form-control" name="description" rows="3" required>${darshan.description}</textarea>
                                <div class="invalid-feedback">Please enter description</div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Time Slot</label>
                                <input type="text" class="form-control" name="time_slot" 
                                       value="${darshan.time_slot}" required>
                                <div class="invalid-feedback">Please enter time slot</div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Price (₹)</label>
                                <input type="number" class="form-control" name="price" 
                                       value="${darshan.price}" step="0.01" min="0" required>
                                <div class="invalid-feedback">Please enter a valid price</div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Maximum Persons</label>
                                <input type="number" class="form-control" name="max_persons" 
                                       value="${darshan.max_persons}" min="1" required>
                                <div class="invalid-feedback">Please enter maximum persons allowed</div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Status</label>
                                <select class="form-control" name="status" required>
                                    <option value="active" ${darshan.status == 'active' ? 'selected' : ''}>Active</option>
                                    <option value="inactive" ${darshan.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                                </select>
                                <div class="invalid-feedback">Please select a status</div>
                            </div>

                            <div class="text-end">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Update Darshan
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
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
    </script>
</body>
</html> 