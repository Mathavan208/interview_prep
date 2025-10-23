package com.interviewprep.model;

import java.sql.Timestamp;

public class Submission {
    private int id;
    private int problemId;
    private String problemTitle;
    private Timestamp submissionDate;
    private String status;
    private String notes;
    private int timeSpent;
    private int userId; // Add this field
    
    // Constructors
    public Submission() {}
    
    public Submission(int id, int problemId, Timestamp submissionDate, String status, String notes, int timeSpent, int userId) {
        this.id = id;
        this.problemId = problemId;
        this.submissionDate = submissionDate;
        this.status = status;
        this.notes = notes;
        this.timeSpent = timeSpent;
        this.userId = userId; // Initialize this field
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getProblemId() {
        return problemId;
    }
    
    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }
    
    public String getProblemTitle() {
        return problemTitle;
    }
    
    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }
    
    public Timestamp getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public int getTimeSpent() {
        return timeSpent;
    }
    
    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
}