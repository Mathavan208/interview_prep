package com.interviewprep.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    
    static {
        // Use environment variables with fallback values
        URL =  "postgresql://interview_prep_db_rwf1_user:S7RMrNxQlSRSMh3dGWJybUCs12CuqzCO@dpg-d3t09kndiees73ctm6ag-a/interview_prep_db_rwf1";
        USERNAME ="interview_prep_db_rwf1_user";
        PASSWORD = "S7RMrNxQlSRSMh3dGWJybUCs12CuqzCO";
        
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load PostgreSQL JDBC Driver");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("Attempting to connect to database: " + URL);
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection successful");
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed:");
            e.printStackTrace();
            throw e;
        }
    }
}