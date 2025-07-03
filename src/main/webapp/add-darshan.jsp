<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Darshan - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Add New Darshan</h4>
                        <a href="list-darshans.jsp" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
                        </a>
                    </div>
                    <div class="card-body">
                        <form action="AdminDarshanController" method="post">
                            <input type="hidden" name="action" value="add">
                            
                            <div class="mb-3">
                                <label for="darshan_type" class="form-label">Darshan Type</label>
                                <input type="text" class="form-control" id="darshan_type" 
                                       name="darshan_type" required>
                            </div>

                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" 
                                          name="description" rows="3" required></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="time_slot" class="form-label">Time Slot</label>
                                <input type="text" class="form-control" id="time_slot" 
                                       name="time_slot" placeholder="e.g., 05:00 AM - 07:00 AM" required>
                            </div>

                            <div class="mb-3">
                                <label for="price" class="form-label">Price (₹)</label>
                                <input type="number" class="form-control" id="price" 
                                       name="price" min="0" step="0.01" required>
                            </div>

                            <div class="mb-3">
                                <label for="max_persons" class="form-label">Maximum Persons</label>
                                <input type="number" class="form-control" id="max_persons" 
                                       name="max_persons" min="1" required>
                            </div>

                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-control" id="status" name="status" required>
                                    <option value="active">Active</option>
                                    <option value="inactive">Inactive</option>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save"></i> Save Darshan
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 