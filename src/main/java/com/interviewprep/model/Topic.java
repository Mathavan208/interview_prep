package com.interviewprep.model;

public class Topic {
    private int id;
    private String name;
    private String description;
    private int userId; // Add this field
    
    // Constructors
    public Topic() {}
    
    public Topic(int id, String name, String description, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId; // Initialize this field
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
}