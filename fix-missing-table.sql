-- Fix for missing teacher_preferred_areas table
-- Run this SQL script in your MySQL database

USE betaApp_db;

-- Create teacher_preferred_areas table if it doesn't exist
CREATE TABLE IF NOT EXISTS teacher_preferred_areas (
    teacher_id BIGINT NOT NULL,
    area VARCHAR(255),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
    INDEX idx_teacher_id (teacher_id)
);

-- Verify the table was created
SHOW TABLES LIKE 'teacher_preferred_areas';

-- Show table structure
DESC teacher_preferred_areas;
