-- ===========================================================================
-- V8__add_data_protection.sql
-- Purpose: Add lock_version and updated_at columns and enforce updated_at via triggers
-- ===========================================================================

-- 1. Update TEACHER table
ALTER TABLE teacher
ADD COLUMN updated_at DATETIME(6),
ADD COLUMN lock_version BIGINT DEFAULT 0;

-- Backfill data
UPDATE teacher SET updated_at = created_at WHERE updated_at IS NULL;
UPDATE teacher SET lock_version = 0 WHERE lock_version IS NULL;

-- Add constraints
ALTER TABLE teacher MODIFY COLUMN updated_at DATETIME(6) NOT NULL;

-- 2. Update INQUIRY table
ALTER TABLE inquiry
ADD COLUMN updated_at DATETIME(6),
ADD COLUMN lock_version BIGINT DEFAULT 0;

-- Backfill data
UPDATE inquiry SET updated_at = created_at WHERE updated_at IS NULL;
UPDATE inquiry SET lock_version = 0 WHERE lock_version IS NULL;

-- Add constraints
ALTER TABLE inquiry MODIFY COLUMN updated_at DATETIME(6) NOT NULL;

-- 3. Update DOCUMENTS table (columns exist, just ensure data integrity)
UPDATE documents SET lock_version = 0 WHERE lock_version IS NULL;

-- 4. Create Triggers

DROP TRIGGER IF EXISTS before_update_teacher;

DELIMITER //
CREATE TRIGGER before_update_teacher
BEFORE UPDATE ON teacher
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW(6);
END//
DELIMITER ;

DROP TRIGGER IF EXISTS before_update_inquiry;

DELIMITER //
CREATE TRIGGER before_update_inquiry
BEFORE UPDATE ON inquiry
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW(6);
END//
DELIMITER ;

DROP TRIGGER IF EXISTS before_update_documents;

DELIMITER //
CREATE TRIGGER before_update_documents
BEFORE UPDATE ON documents
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW(6);
END//
DELIMITER ;
