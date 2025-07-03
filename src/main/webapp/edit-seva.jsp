<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Seva" %>
<%@ page import="dao.SevaDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Seva - Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <%
        int sevaId = Integer.parseInt(request.getParameter("id"));
        SevaDAO sevaDAO = new SevaDAO();
        Seva seva = sevaDAO.getSevaById(sevaId);
    %>

    <div class="container">
        <div class="form-container">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Edit Seva</h2>
                <a href="list-sevas.jsp" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back to List
                </a>
            </div>

            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <form action="AdminSevaController" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="sevaId" value="<%= seva.getSevaId() %>">

                <div class="mb-3">
                    <label for="sevaName" class="form-label">Seva Name</label>
                    <input type="text" class="form-control" id="sevaName" name="sevaName" 
                           value="<%= seva.getSevaName() %>" required>
                </div>

                <div class="mb-3">
                    <label for="timing" class="form-label">Timing</label>
                    <input type="text" class="form-control" id="timing" name="timing" 
                           value="<%= seva.getTiming() %>" required>
                </div>

                <div class="mb-3">
                    <label for="price" class="form-label">Price (₹)</label>
                    <input type="number" class="form-control" id="price" name="price" 
                           value="<%= seva.getPrice() %>" step="0.01" min="0" required>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Save Changes
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function validateForm() {
            const sevaName = document.getElementById('sevaName').value.trim();
            const timing = document.getElementById('timing').value.trim();
            const price = document.getElementById('price').value;

            if (!sevaName || !timing || !price) {
                alert('Please fill in all required fields');
                return false;
            }

            if (parseFloat(price) < 0) {
                alert('Price cannot be negative');
                return false;
            }

            return true;
        }
    </script>
</body>
</html>