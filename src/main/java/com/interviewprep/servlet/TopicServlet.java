package com.interviewprep.servlet;

import com.interviewprep.dao.TopicDAO;
import com.interviewprep.model.Topic;
import com.interviewprep.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "TopicServlet", urlPatterns = {"/topics", "/topics/*"})
public class TopicServlet extends HttpServlet {
    
    private TopicDAO topicDAO;
    
    public void init() {
        topicDAO = new TopicDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null) {
            action = "/";
        }
        
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertTopic(request, response);
                    break;
                case "/delete":
                    deleteTopic(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTopic(request, response);
                    break;
                default:
                    listTopic(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listTopic(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        List<Topic> topics = topicDAO.getAllTopics(user.getId());
        request.setAttribute("topics", topics);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/topics.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/topic-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        Topic existingTopic = topicDAO.getTopicById(id, user.getId());
        
        request.setAttribute("topic", existingTopic);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/topic-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertTopic(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        Topic newTopic = new Topic();
        newTopic.setName(name);
        newTopic.setDescription(description);
        newTopic.setUserId(user.getId());
        
        topicDAO.addTopic(newTopic);
        response.sendRedirect("topics");
    }
    
    private void updateTopic(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        Topic topic = new Topic();
        topic.setId(id);
        topic.setName(name);
        topic.setDescription(description);
        topic.setUserId(user.getId());
        
        topicDAO.updateTopic(topic);
        response.sendRedirect("topics");
    }
    
    private void deleteTopic(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        topicDAO.deleteTopic(id, user.getId());
        response.sendRedirect("topics");
    }
}