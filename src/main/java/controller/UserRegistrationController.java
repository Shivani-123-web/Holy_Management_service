package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import model.User;
import dao.JDConnection;

@WebServlet("/UserRegistrationController")
public class UserRegistrationController extends HttpServlet {
    
   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String fullName = request.getParameter("fullName");  // Added this line
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");

    Connection conn = null;
    try {
        conn = JDConnection.getConnection();
        UserDAO userDAO = new UserDAO(conn);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFullName(fullName);  // Added this line
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setAddress(address);

        if(userDAO.addUser(newUser)) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "Registration failed! Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        request.getRequestDispatcher("register.jsp").forward(request, response);
    } finally {
        JDConnection.closeConnection(conn);
    }
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show registration form
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }
}