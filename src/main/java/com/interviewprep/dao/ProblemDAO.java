package com.interviewprep.dao;

import com.interviewprep.model.Problem;
import com.interviewprep.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {
    
    public List<Problem> getAllProblems(int page, int pageSize, String difficulty, int topicId, int userId) throws SQLException {
        List<Problem> problems = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, t.name as topic_name FROM problems p " +
            "LEFT JOIN topics t ON p.topic_id = t.id WHERE p.user_id = ?"
        );
        
        if (difficulty != null && !difficulty.isEmpty()) {
            sql.append(" AND p.difficulty = ?");
        }
        
        if (topicId > 0) {
            sql.append(" AND p.topic_id = ?");
        }
        
        sql.append(" ORDER BY p.created_at DESC LIMIT ? OFFSET ?");
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            pstmt.setInt(paramIndex++, userId);
            
            if (difficulty != null && !difficulty.isEmpty()) {
                pstmt.setString(paramIndex++, difficulty);
            }
            
            if (topicId > 0) {
                pstmt.setInt(paramIndex++, topicId);
            }
            
            pstmt.setInt(paramIndex++, pageSize);
            pstmt.setInt(paramIndex++, (page - 1) * pageSize);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Problem problem = new Problem();
                    problem.setId(rs.getInt("id"));
                    problem.setTitle(rs.getString("title"));
                    problem.setDescription(rs.getString("description"));
                    problem.setDifficulty(rs.getString("difficulty"));
                    problem.setTopicId(rs.getInt("topic_id"));
                    problem.setTopicName(rs.getString("topic_name"));
                    problem.setLink(rs.getString("link"));
                    problem.setUserId(rs.getInt("user_id"));
                    problems.add(problem);
                }
            }
        }
        
        return problems;
    }
    
    public int getProblemsCount(String difficulty, int topicId, int userId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM problems WHERE user_id = ?");
        
        if (difficulty != null && !difficulty.isEmpty()) {
            sql.append(" AND difficulty = ?");
        }
        
        if (topicId > 0) {
            sql.append(" AND topic_id = ?");
        }
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            pstmt.setInt(paramIndex++, userId);
            
            if (difficulty != null && !difficulty.isEmpty()) {
                pstmt.setString(paramIndex++, difficulty);
            }
            
            if (topicId > 0) {
                pstmt.setInt(paramIndex++, topicId);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return 0;
    }
    
    public Problem getProblemById(int id, int userId) throws SQLException {
        String sql = "SELECT p.*, t.name as topic_name FROM problems p " +
                     "LEFT JOIN topics t ON p.topic_id = t.id WHERE p.id = ? AND p.user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Problem problem = new Problem();
                    problem.setId(rs.getInt("id"));
                    problem.setTitle(rs.getString("title"));
                    problem.setDescription(rs.getString("description"));
                    problem.setDifficulty(rs.getString("difficulty"));
                    problem.setTopicId(rs.getInt("topic_id"));
                    problem.setTopicName(rs.getString("topic_name"));
                    problem.setLink(rs.getString("link"));
                    problem.setUserId(rs.getInt("user_id"));
                    return problem;
                }
            }
        }
        
        return null;
    }
    
    public boolean addProblem(Problem problem) throws SQLException {
        String sql = "INSERT INTO problems (title, description, difficulty, topic_id, link, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, problem.getTitle());
            pstmt.setString(2, problem.getDescription());
            pstmt.setString(3, problem.getDifficulty());
            
            if (problem.getTopicId() > 0) {
                pstmt.setInt(4, problem.getTopicId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setString(5, problem.getLink());
            pstmt.setInt(6, problem.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        problem.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        
        return false;
    }
    
    public boolean updateProblem(Problem problem) throws SQLException {
        String sql = "UPDATE problems SET title = ?, description = ?, difficulty = ?, topic_id = ?, link = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, problem.getTitle());
            pstmt.setString(2, problem.getDescription());
            pstmt.setString(3, problem.getDifficulty());
            
            if (problem.getTopicId() > 0) {
                pstmt.setInt(4, problem.getTopicId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setString(5, problem.getLink());
            pstmt.setInt(6, problem.getId());
            pstmt.setInt(7, problem.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteProblem(int id, int userId) throws SQLException {
        String sql = "DELETE FROM problems WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}