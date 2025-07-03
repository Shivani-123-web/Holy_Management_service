package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

import dao.EventDAO;
import model.Event;

@WebServlet("/EventController")
public class EventController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        try {
            EventDAO eventDAO = new EventDAO();

            switch(action) {
                case "add":
                    // Handle add event
                    Event newEvent = new Event();
                    newEvent.setEventName(request.getParameter("eventName"));
                    newEvent.setPrice(new BigDecimal(request.getParameter("price")));
                    
                    if(eventDAO.addEvent(newEvent)) {
                        response.sendRedirect("events.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Failed to add event");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("add-event.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;

                default:
                    response.sendRedirect("events.jsp");
                    break;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            EventDAO eventDAO = new EventDAO();
            
            String action = request.getParameter("action");
            if(action == null) {
                // List all active events
                List<Event> events = eventDAO.getAllActiveEvents();
                request.setAttribute("events", events);
                RequestDispatcher dispatcher = request.getRequestDispatcher("events.jsp");
                dispatcher.forward(request, response);
            } else if(action.equals("view")) {
                // Show event details
                int eventId = Integer.parseInt(request.getParameter("eventId"));
                Event event = eventDAO.getEventById(eventId);
                if (event != null) {
                    request.setAttribute("event", event);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("view-event.jsp");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Event not found");
                    response.sendRedirect("events.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}