package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

import dao.UserDAO;
import model.User;
import dao.JDConnection;

@WebServlet("/UserLoginController")
public class UserLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Static admin credentials
    private static final String ADMIN_USERNAME = "Nikhila123";
    private static final String ADMIN_PASSWORD = "Niks1234";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        System.out.println("Login attempt - Username: " + username + ", Role: " + role);

        // Admin login
        if ("admin".equals(role)) {
            if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("isAdmin", true);
                System.out.println("Admin login successful");
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
                return;
            } else {
                request.setAttribute("errorMessage", "Invalid admin credentials");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
        }
        
        // Devotee login
        if ("devotee".equals(role)) {
            Connection conn = null;
            try {
                conn = JDConnection.getConnection();
                if (conn == null) {
                    throw new ServletException("Database connection failed");
                }
                
                UserDAO userDAO = new UserDAO(conn);
                User user = userDAO.validateLogin(username, password);
                
                if (user != null) {
                    System.out.println("Devotee login successful for: " + username);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("isDevotee", true);
                    
                    // Check if there's a redirect URL stored in session
                    String redirectUrl = (String) session.getAttribute("redirectUrl");
                    if (redirectUrl != null) {
                        session.removeAttribute("redirectUrl"); // Clear the stored URL
                        response.sendRedirect(redirectUrl);
                    } else {
                        // Default redirects if no stored URL
                        response.sendRedirect(request.getContextPath() + "/devotee.jsp");
                    }
                    return;
                } else {
                    System.out.println("Invalid devotee credentials");
                    request.setAttribute("errorMessage", "Invalid username or password");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error during devotee login: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("errorMessage", "Login error: " + e.getMessage());
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            } finally {
                if (conn != null) {
                    JDConnection.closeConnection(conn);
                }
            }
        }
        
        // If we get here, no valid role was selected
        request.setAttribute("errorMessage", "Please select a valid role");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}