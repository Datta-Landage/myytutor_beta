-- ===========================================================================
-- V9__fix_audit_triggers.sql
-- Purpose: Add BEFORE INSERT triggers to ensure updated_at is populated on creation
-- ===========================================================================

-- 1. Create BEFORE INSERT Triggers

DROP TRIGGER IF EXISTS before_insert_teacher;

DELIMITER //
CREATE TRIGGER before_insert_teacher
BEFORE INSERT ON teacher
FOR EACH ROW
BEGIN
    IF NEW.updated_at IS NULL THEN
        SET NEW.updated_at = NOW(6);
    END IF;
    IF NEW.lock_version IS NULL THEN
        SET NEW.lock_version = 0;
    END IF;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS before_insert_inquiry;

DELIMITER //
CREATE TRIGGER before_insert_inquiry
BEFORE INSERT ON inquiry
FOR EACH ROW
BEGIN
    IF NEW.updated_at IS NULL THEN
        SET NEW.updated_at = NOW(6);
    END IF;
    IF NEW.lock_version IS NULL THEN
        SET NEW.lock_version = 0;
    END IF;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS before_insert_documents;

DELIMITER //
CREATE TRIGGER before_insert_documents
BEFORE INSERT ON documents
FOR EACH ROW
BEGIN
    IF NEW.updated_at IS NULL THEN
        SET NEW.updated_at = NOW(6);
    END IF;
    IF NEW.lock_version IS NULL THEN
        SET NEW.lock_version = 0;
    END IF;
END//
DELIMITER ;
