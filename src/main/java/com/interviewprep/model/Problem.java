package com.interviewprep.model;

public class Problem {
    private int id;
    private String title;
    private String description;
    private String difficulty;
    private int topicId;
    private String topicName;
    private String link;
    private int userId; // Add this field
    
    // Constructors
    public Problem() {}
    
    public Problem(int id, String title, String description, String difficulty, int topicId, String link, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.topicId = topicId;
        this.link = link;
        this.userId = userId; // Initialize this field
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public int getTopicId() {
        return topicId;
    }
    
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
    
    public String getTopicName() {
        return topicName;
    }
    
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
}