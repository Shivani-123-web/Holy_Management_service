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
import java.util.Random;

import dao.UserDAO;
import model.User;
import dao.JDConnection;

@WebServlet("/ForgotPasswordController")
public class ForgotPasswordController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        Connection conn = null;

        try {
            conn = JDConnection.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            HttpSession session = request.getSession();

            if ("sendOTP".equals(action)) {
                // Handle OTP sending
                String email = request.getParameter("email");
                
                // Check if email exists
                if (!userDAO.isEmailExists(email)) {
                    request.setAttribute("errorMessage", "Email not found!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Generate OTP
                String otp = generateOTP();
                
                // Store OTP in session
                session.setAttribute("resetOTP", otp);
                session.setAttribute("resetEmail", email);
                session.setAttribute("otpTimestamp", System.currentTimeMillis());

                // In real application, send OTP via email
                // For demo, we'll just show it
                request.setAttribute("successMessage", "OTP sent! For demo purpose, OTP is: " + otp);
                request.setAttribute("otpSent", true);
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
                dispatcher.forward(request, response);

            } else if ("resetPassword".equals(action)) {
                // Handle password reset
                String otp = request.getParameter("otp");
                String newPassword = request.getParameter("newPassword");
                String storedOTP = (String) session.getAttribute("resetOTP");
                String storedEmail = (String) session.getAttribute("resetEmail");
                Long otpTimestamp = (Long) session.getAttribute("otpTimestamp");

                // Validate OTP
                if (storedOTP == null || !storedOTP.equals(otp)) {
                    request.setAttribute("errorMessage", "Invalid OTP!");
                    request.setAttribute("otpSent", true);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Check OTP expiration (15 minutes)
                if (System.currentTimeMillis() - otpTimestamp > 900000) {
                    request.setAttribute("errorMessage", "OTP has expired! Please request a new one.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Update password
                if (userDAO.updatePasswordByEmail(storedEmail, newPassword)) {
                    // Clear session attributes
                    session.removeAttribute("resetOTP");
                    session.removeAttribute("resetEmail");
                    session.removeAttribute("otpTimestamp");

                    request.setAttribute("successMessage", "Password reset successful! Please login with your new password.");
                    response.sendRedirect("login.jsp");
                } else {
                    request.setAttribute("errorMessage", "Failed to reset password. Please try again.");
                    request.setAttribute("otpSent", true);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
                    dispatcher.forward(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
            dispatcher.forward(request, response);
        } finally {
            JDConnection.closeConnection(conn);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("forgot-password.jsp");
        dispatcher.forward(request, response);
    }

    // Utility method to generate 6-digit OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}