package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Darshan;
import model.DarshanBooking;
import dao.DarshanDAO;
import dao.DarshanBookingDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/DarshanController")
public class DarshanController extends HttpServlet {
    private DarshanDAO darshanDAO;
    private DarshanBookingDAO bookingDAO;

    public void init() throws ServletException {
        try {
            darshanDAO = new DarshanDAO();
            bookingDAO = new DarshanBookingDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing DAOs", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                listDarshans(request, response);
            } else if (action.equals("book")) {
                showBookingForm(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listDarshans(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            List<Darshan> darshans = darshanDAO.getAllDarshans();
            request.setAttribute("darshans", darshans);
            request.getRequestDispatcher("/darshan-list.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error fetching darshans: " + e.getMessage());
            request.getRequestDispatcher("/darshan-list.jsp").forward(request, response);
        }
    }

    private void showBookingForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            int darshan_id = Integer.parseInt(request.getParameter("id"));
            Darshan darshan = darshanDAO.getDarshanById(darshan_id);
            
            if (darshan != null) {
                request.setAttribute("darshan", darshan);
                request.getRequestDispatcher("/darshan-booking.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Darshan not found");
                request.getRequestDispatcher("/darshan-list.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid darshan ID");
            request.getRequestDispatcher("/darshan-list.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("proceed_payment".equals(action)) {
                processDarshanBooking(request, response);
            } else if ("verify_payment".equals(action)) {
                verifyPayment(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/darshan-list.jsp").forward(request, response);
        }
    }

    private void processDarshanBooking(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            // Get form data
            int darshanId = Integer.parseInt(request.getParameter("darshan_id"));
            String bookingDate = request.getParameter("bookingDate");
            int persons = Integer.parseInt(request.getParameter("persons"));
            
            // Get darshan details from database
            Darshan darshan = darshanDAO.getDarshanById(darshanId);
            
            if (darshan != null) {
                // Calculate total amount
                double totalAmount = darshan.getPrice() * persons;
                
                // Generate booking ID with proper format
                String bookingId = "DARSHAN" + String.format("%010d", System.currentTimeMillis() % 10000000000L);
                
                // Store booking details in session
                session.setAttribute("bookingId", bookingId);
                session.setAttribute("darshan", darshan);
                session.setAttribute("bookingDate", bookingDate);
                session.setAttribute("persons", persons);
                session.setAttribute("totalAmount", totalAmount);
                
                // Forward to payment page
                request.getRequestDispatcher("/darshan-payment.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid darshan selection");
                request.getRequestDispatcher("/darshan-list.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input data");
            request.getRequestDispatcher("/darshan-booking.jsp").forward(request, response);
        }
    }

    private void verifyPayment(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            // Get booking details from request
            String bookingId = request.getParameter("bookingId");
            if (bookingId == null || bookingId.trim().isEmpty()) {
                bookingId = "DARSHAN" + String.format("%010d", System.currentTimeMillis() % 10000000000L);
            }
            
            int darshanId = Integer.parseInt(request.getParameter("darshan_id"));
            String bookingDate = request.getParameter("bookingDate");
            int persons = Integer.parseInt(request.getParameter("persons"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            
            // Get user ID from session
            int userId = ((Integer) userIdObj).intValue();
            
            // Generate transaction ID
            String transactionId = "TXN" + String.format("%010d", System.currentTimeMillis() % 10000000000L);
            
            // Create booking record
            DarshanBooking booking = new DarshanBooking();
            booking.setBookingId(bookingId);
            booking.setDarshanId(darshanId);
            booking.setDevoteeId(userId);
            booking.setBookingDate(bookingDate);
            booking.setPersons(persons);
            booking.setTotalAmount(totalAmount);
            booking.setTransactionId(transactionId);
            booking.setStatus("CONFIRMED");
            
            // Save booking to database
            bookingDAO.saveBooking(booking);
            
            // Store success details in session
            session.setAttribute("successBookingId", bookingId);
            session.setAttribute("successTransactionId", transactionId);
            session.setAttribute("successAmount", totalAmount);
            
            // Redirect to success page
            response.sendRedirect(request.getContextPath() + "/payment-success.jsp");
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input data");
            request.getRequestDispatcher("/darshan-payment.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/darshan-payment.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        try {
            if (darshanDAO != null) {
                darshanDAO.close();
            }
            if (bookingDAO != null) {
                bookingDAO.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 