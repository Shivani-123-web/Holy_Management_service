package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Payment;
import dao.PaymentDAO;
import dao.SevaDAO;
import dao.SevaBookingDAO;
import model.Seva;
import java.sql.SQLException;
import java.util.UUID;
import java.math.BigDecimal;

@WebServlet("/PaymentController")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SevaDAO sevaDAO;
    private PaymentDAO paymentDAO;
    private SevaBookingDAO sevaBookingDAO;

    @Override
    public void init() throws ServletException {
        try {
            sevaDAO = new SevaDAO();
            paymentDAO = new PaymentDAO();
            sevaBookingDAO = new SevaBookingDAO();
        } catch (SQLException e) {
            throw new ServletException("Error initializing DAOs: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        try {
            String bookingIdStr = request.getParameter("bookingId");
            
            if (bookingIdStr == null || bookingIdStr.isEmpty()) {
                session.setAttribute("errorMessage", "Booking ID is required");
                response.sendRedirect("error.jsp");
                return;
            }

            int bookingId = Integer.parseInt(bookingIdStr);
            
            Integer sevaId = (Integer) session.getAttribute("sevaId");
            if (sevaId == null || sevaId <= 0) {
                session.setAttribute("errorMessage", "Invalid seva ID");
                response.sendRedirect("error.jsp");
                return;
            }
            
            Seva seva = sevaDAO.getSevaById(sevaId);
            if (seva == null) {
                session.setAttribute("errorMessage", "Seva not found");
                response.sendRedirect("error.jsp");
                return;
            }
            
            BigDecimal amount = seva.getPrice();
            String transactionId = UUID.randomUUID().toString();
            
            Payment payment = new Payment(bookingId, transactionId);
            boolean created = paymentDAO.createPayment(payment);
            
            if (created) {
                session.setAttribute("pendingPaymentId", payment.getPaymentId());
                session.setAttribute("bookingId", bookingId);
                session.setAttribute("transactionId", transactionId);
                session.setAttribute("sevaAmount", amount);
                
                request.setAttribute("amount", amount);
                request.setAttribute("sevaName", seva.getSevaName());
                
                request.getRequestDispatcher("payment.jsp").forward(request, response);
            } else {
                session.setAttribute("errorMessage", "Failed to initialize payment");
                response.sendRedirect("error.jsp");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid booking ID format");
            response.sendRedirect("error.jsp");
        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        try {
            String bookingIdStr = request.getParameter("bookingId");
            String transactionId = request.getParameter("transactionId");
            String amount = request.getParameter("amount");
            
            if (bookingIdStr == null || transactionId == null) {
                session.setAttribute("errorMessage", "Missing payment verification details");
                response.sendRedirect("error.jsp");
                return;
            }

            int bookingId = Integer.parseInt(bookingIdStr);
            
            boolean updated = paymentDAO.updatePaymentStatus(bookingId, "Success");
            
            if (updated) {
                // Store success details in session
                session.setAttribute("successBookingId", bookingId);
                session.setAttribute("successTransactionId", transactionId);
                session.setAttribute("successAmount", amount);
                
                // Clear payment processing session attributes
                session.removeAttribute("pendingPaymentId");
                session.removeAttribute("bookingId");
                session.removeAttribute("transactionId");
                session.removeAttribute("sevaAmount");
                
                response.sendRedirect("payment-success.jsp");
            } else {
                session.setAttribute("errorMessage", "Failed to update payment status");
                response.sendRedirect("error.jsp");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid booking ID format");
            response.sendRedirect("error.jsp");
        } 
    }
    
    @Override
    public void destroy() {
        try {
            if (sevaDAO != null) {
                sevaDAO.closeConnection();
            }
            if (paymentDAO != null) {
                paymentDAO.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}