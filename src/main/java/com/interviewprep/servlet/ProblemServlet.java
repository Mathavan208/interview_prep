package com.interviewprep.servlet;

import com.interviewprep.dao.ProblemDAO;
import com.interviewprep.dao.TopicDAO;
import com.interviewprep.model.Problem;
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

@WebServlet(name = "ProblemServlet", urlPatterns = {"/problems", "/problems/*"})
public class ProblemServlet extends HttpServlet {
    
    private ProblemDAO problemDAO;
    private TopicDAO topicDAO;
    
    public void init() {
        problemDAO = new ProblemDAO();
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
                    insertProblem(request, response);
                    break;
                case "/delete":
                    deleteProblem(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateProblem(request, response);
                    break;
                default:
                    listProblem(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listProblem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int page = 1;
        int pageSize = 10;
        String difficulty = request.getParameter("difficulty");
        int topicId = 0;
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        
        if (request.getParameter("topicId") != null) {
            topicId = Integer.parseInt(request.getParameter("topicId"));
        }
        
        List<Problem> problems = problemDAO.getAllProblems(page, pageSize, difficulty, topicId, user.getId());
        int totalProblems = problemDAO.getProblemsCount(difficulty, topicId, user.getId());
        int totalPages = (int) Math.ceil((double) totalProblems / pageSize);
        
        List<Topic> topics = topicDAO.getAllTopics(user.getId());
        
        request.setAttribute("problems", problems);
        request.setAttribute("topics", topics);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalProblems", totalProblems);
        request.setAttribute("selectedDifficulty", difficulty);
        request.setAttribute("selectedTopicId", topicId);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/problems.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        List<Topic> topics = topicDAO.getAllTopics(user.getId());
        request.setAttribute("topics", topics);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/problem-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        Problem existingProblem = problemDAO.getProblemById(id, user.getId());
        List<Topic> topics = topicDAO.getAllTopics(user.getId());
        
        request.setAttribute("problem", existingProblem);
        request.setAttribute("topics", topics);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/problem-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertProblem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String difficulty = request.getParameter("difficulty");
        int topicId = 0;
        
        if (request.getParameter("topicId") != null && !request.getParameter("topicId").isEmpty()) {
            topicId = Integer.parseInt(request.getParameter("topicId"));
        }
        
        String link = request.getParameter("link");
        
        Problem newProblem = new Problem();
        newProblem.setTitle(title);
        newProblem.setDescription(description);
        newProblem.setDifficulty(difficulty);
        newProblem.setTopicId(topicId);
        newProblem.setLink(link);
        newProblem.setUserId(user.getId());
        
        problemDAO.addProblem(newProblem);
        response.sendRedirect("problems");
    }
    
    private void updateProblem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String difficulty = request.getParameter("difficulty");
        int topicId = 0;
        
        if (request.getParameter("topicId") != null && !request.getParameter("topicId").isEmpty()) {
            topicId = Integer.parseInt(request.getParameter("topicId"));
        }
        
        String link = request.getParameter("link");
        
        Problem problem = new Problem();
        problem.setId(id);
        problem.setTitle(title);
        problem.setDescription(description);
        problem.setDifficulty(difficulty);
        problem.setTopicId(topicId);
        problem.setLink(link);
        problem.setUserId(user.getId());
        
        problemDAO.updateProblem(problem);
        response.sendRedirect("problems");
    }
    
    private void deleteProblem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        problemDAO.deleteProblem(id, user.getId());
        response.sendRedirect("problems");
    }
}