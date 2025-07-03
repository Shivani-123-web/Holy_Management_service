package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Seva;
import dao.SevaDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminSevaController")  // Update the path to include /admin/
public class AdminSevaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SevaDAO sevaDAO;

    public void init() throws ServletException {
        try {
            sevaDAO = new SevaDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing SevaDAO: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                listSevas(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteSeva(request, response);
                        break;
                    default:
                        listSevas(request, response);
                        break;
                }
            }
        } catch (Exception e) {  // Changed from SQLException to Exception
            handleError(request, response, "Database error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("doPost received action: " + action); 

        try {
            if (action == null) {
                addSeva(request, response);
            } else {
                switch (action) {
                    case "add":
                        addSeva(request, response);
                        break;
                    case "update":
                        updateSeva(request, response);
                        break;
                    default:
                        listSevas(request, response);
                        break;
                }
            }
        } catch (Exception e) {  // Changed from SQLException to Exception
            handleError(request, response, "Database error: " + e.getMessage());
        }
    }

    private void listSevas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Seva> sevas = sevaDAO.getAllSevas();
            request.setAttribute("sevas", sevas);
            request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error listing sevas: " + e.getMessage(), e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/add-seva.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int sevaId = Integer.parseInt(request.getParameter("id"));
            Seva seva = sevaDAO.getSevaById(sevaId);
            if (seva != null) {
                request.setAttribute("seva", seva);
                request.getRequestDispatcher("/edit-seva.jsp").forward(request, response);
            } else {
                handleError(request, response, "Seva not found");
            }
        } catch (NumberFormatException e) {
            handleError(request, response, "Invalid seva ID format");
        } catch (SQLException e) {
            throw new ServletException("Error retrieving seva: " + e.getMessage(), e);
        }
    }

    private void addSeva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sevaName = request.getParameter("sevaName");
            String timing = request.getParameter("timing");
            BigDecimal price = new BigDecimal(request.getParameter("price"));

            Seva seva = new Seva(sevaName, timing, price);
            
            if (sevaDAO.addSeva(seva)) {
                request.setAttribute("successMessage", "Seva added successfully");
                request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to add seva");
                request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid price format");
            request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error adding seva: " + e.getMessage());
            request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
        }
    }

    private void updateSeva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int sevaId = Integer.parseInt(request.getParameter("sevaId"));
            String sevaName = request.getParameter("sevaName");
            String timing = request.getParameter("timing");
            BigDecimal price = new BigDecimal(request.getParameter("price"));

            Seva seva = new Seva(sevaName, timing, price);
            seva.setSevaId(sevaId);
            
            if (sevaDAO.updateSeva(seva)) {
                response.sendRedirect("list-sevas.jsp");
            } else {
                handleError(request, response, "Failed to update seva");
            }
        } catch (NumberFormatException e) {
            handleError(request, response, "Invalid ID or price format");
        } catch (Exception e) {
            throw new ServletException("Error updating seva: " + e.getMessage(), e);
        }
    }

    private void deleteSeva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Add debug logging
            System.out.println("Delete request received");
            
            int sevaId = Integer.parseInt(request.getParameter("id"));
            System.out.println("Attempting to delete seva with ID: " + sevaId);
            
            if (sevaDAO.deleteSeva(sevaId)) {
                request.setAttribute("successMessage", "Seva deleted successfully");
                response.sendRedirect("list-sevas.jsp");
            } else {
                request.setAttribute("errorMessage", "Failed to delete seva");
                request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid seva ID format");
            request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/list-sevas.jsp").forward(request, response);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        System.err.println("Error in AdminSevaController: " + errorMessage);
        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", errorMessage);
        response.sendRedirect("error.jsp");
    }

    public void destroy() {
        try {
            if (sevaDAO != null) {
                sevaDAO.closeConnection();
            }
        } catch (Exception e) {
            System.err.println("Error closing SevaDAO connection: " + e.getMessage());
        }
    }
}