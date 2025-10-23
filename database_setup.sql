CREATE DATABASE interview_prep_tracker;
USE interview_prep_tracker;

-- Create topics table
CREATE TABLE topics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create problems table
CREATE TABLE problems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    topic_id INT,
    link VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE SET NULL
);

-- Create submissions table
CREATE TABLE submissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Not Started', 'In Progress', 'Completed', 'Reviewed') NOT NULL,
    notes TEXT,
    time_spent INT, -- in minutes
    FOREIGN KEY (problem_id) REFERENCES problems(id) ON DELETE CASCADE
);

-- Add indexes for better performance
CREATE INDEX idx_problems_topic ON problems(topic_id);
CREATE INDEX idx_problems_difficulty ON problems(difficulty);
CREATE INDEX idx_submissions_problem ON submissions(problem_id);
CREATE INDEX idx_submissions_status ON submissions(status);

-- Insert sample data
INSERT INTO topics (name, description) VALUES 
('Arrays', 'Problems related to arrays and array manipulation'),
('Strings', 'String manipulation and pattern matching problems'),
('Linked Lists', 'Singly and doubly linked list problems'),
('Trees', 'Binary trees, BST, and tree traversal problems'),
('Dynamic Programming', 'DP problems and optimization techniques'),
('Graphs', 'Graph algorithms and traversal problems');

INSERT INTO problems (title, description, difficulty, topic_id, link) VALUES 
('Two Sum', 'Given an array of integers, return indices of the two numbers such that they add up to a specific target.', 'Easy', 1, 'https://leetcode.com/problems/two-sum/'),
('Longest Substring Without Repeating Characters', 'Given a string, find the length of the longest substring without repeating characters.', 'Medium', 2, 'https://leetcode.com/problems/longest-substring-without-repeating-characters/'),
('Add Two Numbers', 'You are given two non-empty linked lists representing two non-negative integers.', 'Medium', 3, 'https://leetcode.com/problems/add-two-numbers/'),
('Binary Tree Inorder Traversal', 'Given the root of a binary tree, return the inorder traversal of its nodes values.', 'Easy', 4, 'https://leetcode.com/problems/binary-tree-inorder-traversal/'),
('Climbing Stairs', 'You are climbing a staircase. It takes n steps to reach the top.', 'Easy', 5, 'https://leetcode.com/problems/climbing-stairs/'),
('Number of Islands', 'Given an m x n 2D binary grid which represents a map of 1s (land) and 0s (water), return the number of islands.', 'Medium', 6, 'https://leetcode.com/problems/number-of-islands/');