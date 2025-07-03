package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import dao.EventBookingDAO;
import model.EventBooking;

@WebServlet("/EventPaymentController")
public class EventPaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            String paymentStatus = request.getParameter("paymentStatus");
            
            EventBookingDAO bookingDAO = new EventBookingDAO();
            
            // Update payment status
            boolean updated = bookingDAO.updatePaymentStatus(bookingId, paymentStatus);
            
            if (updated) {
                // Get updated booking details with event name
                EventBooking booking = bookingDAO.getBookingWithEventDetails(bookingId);
                
                if (booking != null) {
                    if ("SUCCESS".equals(paymentStatus)) {
                        // Set success attributes
                        request.setAttribute("booking", booking);
                        request.setAttribute("successMessage", 
                            String.format("Payment successful for %s. Your booking ID is %d", 
                                booking.getEventName(), booking.getBookingId()));
                        request.getRequestDispatcher("event-paymentsuccess.jsp").forward(request, response);
                    } else {
                        // Handle failed payment
                        request.setAttribute("booking", booking);
                        request.setAttribute("errorMessage", 
                            String.format("Payment failed for %s. Please try again.", 
                                booking.getEventName()));
                        request.getRequestDispatcher("payment-failed.jsp").forward(request, response);
                    }
                } else {
                    throw new SQLException("Booking not found after update");
                }
            } else {
                throw new SQLException("Failed to update payment status");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid booking ID");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}