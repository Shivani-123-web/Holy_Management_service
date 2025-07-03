package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Darshan;
import dao.DarshanDAO;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminDarshanController")
public class AdminDarshanController extends HttpServlet {
    private DarshanDAO darshanDAO;

    public void init() throws ServletException {
        try {
            darshanDAO = new DarshanDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing DarshanDAO", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("delete".equals(action)) {
                // Delete darshan
                int id = Integer.parseInt(request.getParameter("id"));
                if (darshanDAO.deleteDarshan(id)) {
                    request.getSession().setAttribute("successMessage", "Darshan deleted successfully");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to delete darshan");
                }
                response.sendRedirect("list-darshans.jsp");
            } else if ("edit".equals(action)) {
                // Forward to edit page with darshan data
                int id = Integer.parseInt(request.getParameter("id"));
                Darshan darshan = darshanDAO.getDarshanById(id);
                if (darshan != null) {
                    request.setAttribute("darshan", darshan);
                    request.getRequestDispatcher("edit-darshan.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("errorMessage", "Darshan not found");
                    response.sendRedirect("list-darshans.jsp");
                }
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("list-darshans.jsp");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid darshan ID");
            response.sendRedirect("list-darshans.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                // Add new darshan
                Darshan darshan = new Darshan();
                setDarshanProperties(darshan, request);

                if (darshanDAO.addDarshan(darshan)) {
                    request.getSession().setAttribute("successMessage", "Darshan added successfully");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to add darshan");
                }
                response.sendRedirect("list-darshans.jsp");
            } else if ("edit".equals(action)) {
                // Update existing darshan
                Darshan darshan = new Darshan();
                darshan.setDarshan_id(Integer.parseInt(request.getParameter("darshan_id")));
                setDarshanProperties(darshan, request);

                if (darshanDAO.updateDarshan(darshan)) {
                    request.getSession().setAttribute("successMessage", "Darshan updated successfully");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to update darshan");
                }
                response.sendRedirect("list-darshans.jsp");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("list-darshans.jsp");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid input values");
            response.sendRedirect("list-darshans.jsp");
        }
    }

    private void setDarshanProperties(Darshan darshan, HttpServletRequest request) {
        darshan.setDarshan_type(request.getParameter("darshan_type"));
        darshan.setDescription(request.getParameter("description"));
        darshan.setTime_slot(request.getParameter("time_slot"));
        darshan.setPrice(Double.parseDouble(request.getParameter("price")));
        darshan.setMax_persons(Integer.parseInt(request.getParameter("max_persons")));
        darshan.setStatus(request.getParameter("status"));
    }

    @Override
    public void destroy() {
        if (darshanDAO != null) {
            try {
                darshanDAO.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 