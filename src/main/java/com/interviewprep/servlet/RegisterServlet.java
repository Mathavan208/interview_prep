package com.interviewprep.servlet;

import com.interviewprep.dao.UserDAO;
import com.interviewprep.model.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    public void init() {
        userDAO = new UserDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        
        // Basic validation
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty() || 
            fullName == null || fullName.trim().isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        try {
            // Check if username already exists
            if (userDAO.isUsernameExists(username)) {
                request.setAttribute("error", "Username already exists");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
            // Create new user
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setFullName(fullName);
            
            // Register the user
            boolean registered = userDAO.registerUser(newUser);
            
            if (registered) {
                // Redirect to login page with success message
                request.setAttribute("success", "Registration successful! Please login with your credentials.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
    }
}