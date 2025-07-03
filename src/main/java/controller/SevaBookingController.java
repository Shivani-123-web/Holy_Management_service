package controller;

import dao.SevaDAO;
import dao.SevaBookingDAO;
import model.Seva;
import model.SevaBooking;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/SevaBookingController")
public class SevaBookingController extends HttpServlet {
    private SevaDAO sevaDAO;
    private SevaBookingDAO sevaBookingDAO;

    @Override
    public void init() throws ServletException {
        try {
            sevaDAO = new SevaDAO();
            sevaBookingDAO = new SevaBookingDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing DAOs: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("showForm".equals(action)) {
            try {
                int sevaId = Integer.parseInt(request.getParameter("sevaId"));
                Seva seva = sevaDAO.getSevaById(sevaId);
                
                if (seva != null) {
                    request.setAttribute("seva", seva);
                    request.getRequestDispatcher("/book-seva.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Seva not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid seva ID format.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Create booking object from form data
            SevaBooking booking = new SevaBooking();
            booking.setSevaId(Integer.parseInt(request.getParameter("sevaId")));
            booking.setFullName(request.getParameter("fullName"));
            booking.setEmail(request.getParameter("email"));
            booking.setPhoneNumber(request.getParameter("phoneNumber"));
            booking.setTotalAmount(new BigDecimal(request.getParameter("sevaPrice")));
            booking.setBookingDate(Date.valueOf(request.getParameter("bookingDate")));
            booking.setTimeSlot(request.getParameter("timeSlot"));

            // Save booking and get the generated booking ID
            int bookingId = sevaBookingDAO.createBooking(booking);
            
            if (bookingId > 0) {
                // Store booking ID in session for payment
                request.getSession().setAttribute("bookingId", bookingId);
                // Also store seva ID for reference
                request.getSession().setAttribute("sevaId", booking.getSevaId());
                // Redirect to payment controller
                response.sendRedirect("PaymentController?bookingId=" + bookingId);
            } else {
                request.setAttribute("errorMessage", "Failed to create booking. Please try again.");
                request.getRequestDispatcher("/book-seva.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format in form data.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid date format.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        try {
            if (sevaDAO != null) {
                sevaDAO.closeConnection();
            }
        } catch (SQLException e) {
            // Log the error but don't throw it since we're shutting down
            e.printStackTrace();
        }
    }
}