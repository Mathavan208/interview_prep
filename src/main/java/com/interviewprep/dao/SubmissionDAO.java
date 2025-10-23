package com.interviewprep.dao;

import com.interviewprep.model.Submission;
import com.interviewprep.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDAO {
    
    public List<Submission> getAllSubmissions(int page, int pageSize, String status, int userId) throws SQLException {
        List<Submission> submissions = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT s.*, p.title as problem_title FROM submissions s " +
            "JOIN problems p ON s.problem_id = p.id WHERE s.user_id = ?"
        );
        
        if (status != null && !status.isEmpty()) {
            sql.append(" AND s.status = ?");
        }
        
        sql.append(" ORDER BY s.submission_date DESC LIMIT ? OFFSET ?");
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            pstmt.setInt(paramIndex++, userId);
            
            if (status != null && !status.isEmpty()) {
                pstmt.setString(paramIndex++, status);
            }
            
            pstmt.setInt(paramIndex++, pageSize);
            pstmt.setInt(paramIndex++, (page - 1) * pageSize);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Submission submission = new Submission();
                    submission.setId(rs.getInt("id"));
                    submission.setProblemId(rs.getInt("problem_id"));
                    submission.setProblemTitle(rs.getString("problem_title"));
                    submission.setSubmissionDate(rs.getTimestamp("submission_date"));
                    submission.setStatus(rs.getString("status"));
                    submission.setNotes(rs.getString("notes"));
                    submission.setTimeSpent(rs.getInt("time_spent"));
                    submission.setUserId(rs.getInt("user_id"));
                    submissions.add(submission);
                }
            }
        }
        
        return submissions;
    }
    
    public int getSubmissionsCount(String status, int userId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM submissions WHERE user_id = ?");
        
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            pstmt.setInt(paramIndex++, userId);
            
            if (status != null && !status.isEmpty()) {
                pstmt.setString(paramIndex++, status);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return 0;
    }
    
    public Submission getSubmissionById(int id, int userId) throws SQLException {
        String sql = "SELECT s.*, p.title as problem_title FROM submissions s " +
                     "JOIN problems p ON s.problem_id = p.id WHERE s.id = ? AND s.user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Submission submission = new Submission();
                    submission.setId(rs.getInt("id"));
                    submission.setProblemId(rs.getInt("problem_id"));
                    submission.setProblemTitle(rs.getString("problem_title"));
                    submission.setSubmissionDate(rs.getTimestamp("submission_date"));
                    submission.setStatus(rs.getString("status"));
                    submission.setNotes(rs.getString("notes"));
                    submission.setTimeSpent(rs.getInt("time_spent"));
                    submission.setUserId(rs.getInt("user_id"));
                    return submission;
                }
            }
        }
        
        return null;
    }
    
    public boolean addSubmission(Submission submission) throws SQLException {
        String sql = "INSERT INTO submissions (problem_id, status, notes, time_spent, user_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, submission.getProblemId());
            pstmt.setString(2, submission.getStatus());
            pstmt.setString(3, submission.getNotes());
            pstmt.setInt(4, submission.getTimeSpent());
            pstmt.setInt(5, submission.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        submission.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        
        return false;
    }
    
    public boolean updateSubmission(Submission submission) throws SQLException {
        String sql = "UPDATE submissions SET problem_id = ?, status = ?, notes = ?, time_spent = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, submission.getProblemId());
            pstmt.setString(2, submission.getStatus());
            pstmt.setString(3, submission.getNotes());
            pstmt.setInt(4, submission.getTimeSpent());
            pstmt.setInt(5, submission.getId());
            pstmt.setInt(6, submission.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteSubmission(int id, int userId) throws SQLException {
        String sql = "DELETE FROM submissions WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}