package controller;

import dao.SevaDAO;
import model.Seva;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/SevaController")
public class SevaController extends HttpServlet {
    private SevaDAO sevaDAO;

    @Override
    public void init() throws ServletException {
        try {
            sevaDAO = new SevaDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing SevaDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get all sevas from database
            List<Seva> sevaList = sevaDAO.getAllSevas();
            
            // Set the list as request attribute
            request.setAttribute("sevaList", sevaList);
            
            // Forward to seva-list.jsp
            request.getRequestDispatcher("/seva-list.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving sevas: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle POST requests if needed
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    
    }