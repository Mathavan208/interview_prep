package com.interviewprep.dao;

import com.interviewprep.model.Topic;
import com.interviewprep.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    
    public List<Topic> getAllTopics(int userId) throws SQLException {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM topics WHERE user_id = ? ORDER BY name";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Topic topic = new Topic();
                    topic.setId(rs.getInt("id"));
                    topic.setName(rs.getString("name"));
                    topic.setDescription(rs.getString("description"));
                    topic.setUserId(rs.getInt("user_id"));
                    topics.add(topic);
                }
            }
        }
        
        return topics;
    }
    
    public Topic getTopicById(int id, int userId) throws SQLException {
        String sql = "SELECT * FROM topics WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setId(rs.getInt("id"));
                    topic.setName(rs.getString("name"));
                    topic.setDescription(rs.getString("description"));
                    topic.setUserId(rs.getInt("user_id"));
                    return topic;
                }
            }
        }
        
        return null;
    }
    
    public boolean addTopic(Topic topic) throws SQLException {
        String sql = "INSERT INTO topics (name, description, user_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, topic.getName());
            pstmt.setString(2, topic.getDescription());
            pstmt.setInt(3, topic.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean updateTopic(Topic topic) throws SQLException {
        String sql = "UPDATE topics SET name = ?, description = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, topic.getName());
            pstmt.setString(2, topic.getDescription());
            pstmt.setInt(3, topic.getId());
            pstmt.setInt(4, topic.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteTopic(int id, int userId) throws SQLException {
        String sql = "DELETE FROM topics WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}