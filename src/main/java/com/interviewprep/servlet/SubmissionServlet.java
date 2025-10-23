package com.interviewprep.servlet;

import com.interviewprep.dao.ProblemDAO;
import com.interviewprep.dao.SubmissionDAO;
import com.interviewprep.model.Problem;
import com.interviewprep.model.Submission;
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

@WebServlet(name = "SubmissionServlet", urlPatterns = {"/submissions", "/submissions/*"})
public class SubmissionServlet extends HttpServlet {
    
    private SubmissionDAO submissionDAO;
    private ProblemDAO problemDAO;
    
    public void init() {
        submissionDAO = new SubmissionDAO();
        problemDAO = new ProblemDAO();
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
                    insertSubmission(request, response);
                    break;
                case "/delete":
                    deleteSubmission(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateSubmission(request, response);
                    break;
                default:
                    listSubmission(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listSubmission(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int page = 1;
        int pageSize = 10;
        String status = request.getParameter("status");
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        
        List<Submission> submissions = submissionDAO.getAllSubmissions(page, pageSize, status, user.getId());
        int totalSubmissions = submissionDAO.getSubmissionsCount(status, user.getId());
        int totalPages = (int) Math.ceil((double) totalSubmissions / pageSize);
        
        request.setAttribute("submissions", submissions);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalSubmissions", totalSubmissions);
        request.setAttribute("selectedStatus", status);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/submissions.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        List<Problem> problems = problemDAO.getAllProblems(1, 1000, null, 0, user.getId());
        request.setAttribute("problems", problems);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/submission-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        Submission existingSubmission = submissionDAO.getSubmissionById(id, user.getId());
        List<Problem> problems = problemDAO.getAllProblems(1, 1000, null, 0, user.getId());
        
        request.setAttribute("submission", existingSubmission);
        request.setAttribute("problems", problems);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/submission-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertSubmission(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int problemId = Integer.parseInt(request.getParameter("problemId"));
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");
        int timeSpent = 0;
        
        if (request.getParameter("timeSpent") != null && !request.getParameter("timeSpent").isEmpty()) {
            timeSpent = Integer.parseInt(request.getParameter("timeSpent"));
        }
        
        Submission newSubmission = new Submission();
        newSubmission.setProblemId(problemId);
        newSubmission.setStatus(status);
        newSubmission.setNotes(notes);
        newSubmission.setTimeSpent(timeSpent);
        newSubmission.setUserId(user.getId());
        
        submissionDAO.addSubmission(newSubmission);
        response.sendRedirect("submissions");
    }
    
    private void updateSubmission(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        int problemId = Integer.parseInt(request.getParameter("problemId"));
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");
        int timeSpent = 0;
        
        if (request.getParameter("timeSpent") != null && !request.getParameter("timeSpent").isEmpty()) {
            timeSpent = Integer.parseInt(request.getParameter("timeSpent"));
        }
        
        Submission submission = new Submission();
        submission.setId(id);
        submission.setProblemId(problemId);
        submission.setStatus(status);
        submission.setNotes(notes);
        submission.setTimeSpent(timeSpent);
        submission.setUserId(user.getId());
        
        submissionDAO.updateSubmission(submission);
        response.sendRedirect("submissions");
    }
    
    private void deleteSubmission(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        // Get the current user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        submissionDAO.deleteSubmission(id, user.getId());
        response.sendRedirect("submissions");
    }
}