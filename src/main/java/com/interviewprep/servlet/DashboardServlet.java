package com.interviewprep.servlet;

import com.interviewprep.model.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// IMPORTANT: This servlet now ONLY handles the /dashboard URL
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("DashboardServlet: No valid session found. Redirecting to login.");
            response.sendRedirect("login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        System.out.println("DashboardServlet: User found in session: " + user.getUsername());
        
        // Set hardcoded attributes to avoid database calls for now
        request.setAttribute("totalProblems", 0);
        request.setAttribute("totalTopics", 0);
        request.setAttribute("completedSubmissions", 0);
        request.setAttribute("inProgressSubmissions", 0);
        request.setAttribute("recentSubmissions", new java.util.ArrayList<>());
        
        // Forward to the JSP view
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}