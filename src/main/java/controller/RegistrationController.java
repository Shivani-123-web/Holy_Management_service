package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.RegistrationDAO;
import model.Registration;
import dao.JDConnection;

@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        Connection conn = null;
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        try {
            conn = JDConnection.getConnection();
            RegistrationDAO registrationDAO = new RegistrationDAO(conn);

            switch(action) {
                case "registerEvent":
                    Registration eventReg = new Registration();
                    eventReg.setUserId(userId);
                    eventReg.setEventId(Integer.parseInt(request.getParameter("eventId")));
                    eventReg.setNumberOfPeople(Integer.parseInt(request.getParameter("numberOfPeople")));
                    
                    if(registrationDAO.addRegistration(eventReg)) {
                        response.sendRedirect("user/registrations.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Failed to register for event");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("user/event-registration.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;

                case "registerSeva":
                    Registration sevaReg = new Registration();
                    sevaReg.setUserId(userId);
                    sevaReg.setSevaId(Integer.parseInt(request.getParameter("sevaId")));
                    sevaReg.setPreferredDate(java.sql.Date.valueOf(request.getParameter("preferredDate")));
                    
                    if(registrationDAO.addRegistration(sevaReg)) {
                        response.sendRedirect("user/registrations.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Failed to register for seva");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("user/seva-registration.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;

                case "cancel":
                    int registrationId = Integer.parseInt(request.getParameter("registrationId"));
                    if(registrationDAO.updateRegistrationStatus(registrationId, "CANCELLED")) {
                        response.sendRedirect("user/registrations.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Failed to cancel registration");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("user/registrations.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        } finally {
            JDConnection.closeConnection(conn);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection conn = null;
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        try {
            conn = JDConnection.getConnection();
            RegistrationDAO registrationDAO = new RegistrationDAO(conn);
            
            String action = request.getParameter("action");
            if(action == null) {
                // Show user's registrations
                List<Registration> registrations = registrationDAO.getUserRegistrations(userId);
                request.setAttribute("registrations", registrations);
                RequestDispatcher dispatcher = request.getRequestDispatcher("user/registrations.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        } finally {
            JDConnection.closeConnection(conn);
        }
    }
}