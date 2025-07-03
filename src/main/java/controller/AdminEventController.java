package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;

import dao.EventDAO;
import model.Event;

@WebServlet("/AdminEventController")
public class AdminEventController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EventDAO eventDAO;

    public void init() {
        eventDAO = new EventDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addEvent(request, response);
                    break;
                case "update":
                    updateEvent(request, response);
                    break;
                default:
                    response.sendRedirect("list-events.jsp");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                deleteEvent(request, response);
            } else {
                response.sendRedirect("list-events.jsp");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void addEvent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String eventName = request.getParameter("eventName");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Event event = new Event();
        event.setEventName(eventName);
        event.setPrice(price);

        if (eventDAO.addEvent(event)) {
            request.getSession().setAttribute("successMessage", "Event added successfully!");
        } else {
            request.getSession().setAttribute("errorMessage", "Failed to add event.");
        }
        response.sendRedirect("list-events.jsp");
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String eventName = request.getParameter("eventName");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Event event = new Event();
        event.setEventId(eventId);
        event.setEventName(eventName);
        event.setPrice(price);

        if (eventDAO.updateEvent(event)) {
            request.getSession().setAttribute("successMessage", "Event updated successfully!");
        } else {
            request.getSession().setAttribute("errorMessage", "Failed to update event.");
        }
        response.sendRedirect("list-events.jsp");
    }

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            
            // Check if the event exists before deleting
            Event event = eventDAO.getEventById(eventId);
            if (event == null) {
                request.getSession().setAttribute("errorMessage", "Event not found.");
                response.sendRedirect("list-events.jsp");
                return;
            }

            // Attempt to delete the event
            if (eventDAO.deleteEvent(eventId)) {
                request.getSession().setAttribute("successMessage", 
                    "Event '" + event.getEventName() + "' was successfully deleted.");
            } else {
                request.getSession().setAttribute("errorMessage", 
                    "Failed to delete event. Please try again.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", 
                "Invalid event ID format.");
        } catch (SQLException e) {
            request.getSession().setAttribute("errorMessage", 
                "Database error: " + e.getMessage());
        }
        
        response.sendRedirect("list-events.jsp");
    }
}