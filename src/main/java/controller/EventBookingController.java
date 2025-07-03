package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;

import dao.EventDAO;
import dao.EventBookingDAO;
import model.Event;
import model.EventBooking;
import model.User;

@WebServlet("/EventBookingController")
public class EventBookingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        // Check if user is logged in
        if (userId == null) {
            // Store the current request URL in session
            session.setAttribute("redirectUrl", request.getRequestURI());
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Get form data
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            String bookingDateStr = request.getParameter("bookingDate");
            BigDecimal amount = new BigDecimal(request.getParameter("eventPrice"));
            
            // Get user details from form
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            
            // Convert string date to SQL Date
            Date bookingDate = Date.valueOf(bookingDateStr);
            
            // Get event details
            EventDAO eventDAO = new EventDAO();
            Event event = eventDAO.getEventById(eventId);
            
            if (event == null) {
                request.setAttribute("errorMessage", "Event not found");
                request.getRequestDispatcher("events.jsp").forward(request, response);
                return;
            }
            
            // Create booking object
            EventBooking booking = new EventBooking();
            booking.setEventId(eventId);
            booking.setBookingDate(bookingDate);
            booking.setAmount(amount);
            booking.setFullName(fullName);
            booking.setEmail(email);
            booking.setPhoneNumber(phoneNumber);
            booking.setUserId(userId);
            
            // Save booking
            EventBookingDAO bookingDAO = new EventBookingDAO();
            int bookingId = bookingDAO.createBooking(booking);
            
            if (bookingId > 0) {
                // Set session attributes for payment page
                session.setAttribute("bookingId", bookingId);
                session.setAttribute("eventName", event.getEventName());
                session.setAttribute("bookingDate", bookingDateStr);
                session.setAttribute("amount", amount.toString());
                session.setAttribute("fullName", fullName);
                session.setAttribute("email", email);
                session.setAttribute("phoneNumber", phoneNumber);
                
                // Redirect to payment page
                response.sendRedirect("eventpayment.jsp");
            } else {
                request.setAttribute("errorMessage", "Failed to create booking");
                request.getRequestDispatcher("book-event.jsp?id=" + eventId).forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}