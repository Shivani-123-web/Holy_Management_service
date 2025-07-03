package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.SevaBooking;
import model.DarshanBooking;
import model.EventBooking;
import dao.SevaBookingDAO;
import dao.DarshanBookingDAO;
import dao.SevaDAO;
import dao.DarshanDAO;
import dao.EventBookingDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/MyBookingsController")
public class MyBookingsController extends HttpServlet {
    private SevaBookingDAO sevaBookingDAO;
    private DarshanBookingDAO darshanBookingDAO;
    private EventBookingDAO eventBookingDAO;

    @Override
    public void init() throws ServletException {
        try {
            sevaBookingDAO = new SevaBookingDAO();
            darshanBookingDAO = new DarshanBookingDAO();
            eventBookingDAO = new EventBookingDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get Seva Bookings
            List<SevaBooking> sevaBookings = sevaBookingDAO.getBookingsByUserId(userId);
            request.setAttribute("sevaBookings", sevaBookings);
            System.out.println("Seva Bookings found: " + sevaBookings.size());

            // Get Darshan Bookings
            List<DarshanBooking> darshanBookings = darshanBookingDAO.getBookingsByDevoteeId(userId);
            request.setAttribute("darshanBookings", darshanBookings);
            System.out.println("Darshan Bookings found: " + darshanBookings.size());

            // Get Event Bookings
            List<EventBooking> eventBookings = eventBookingDAO.getBookingsByUserId(userId);
            request.setAttribute("eventBookings", eventBookings);
            System.out.println("Event Bookings found: " + eventBookings.size());

            // Forward to the my-bookings.jsp page
            request.getRequestDispatcher("/my-bookings.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving bookings: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
} 