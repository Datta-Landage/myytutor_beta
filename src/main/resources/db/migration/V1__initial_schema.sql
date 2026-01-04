-- ===========================================================================
-- V1__initial_schema.sql
-- MyyTutor Database Schema - Complete Initial Migration
-- Created: 2026-01-01
-- ===========================================================================

-- ===========================================================================
-- REFERENCE TABLES (No Foreign Key Dependencies)
-- ===========================================================================

-- Subject and Class combinations (e.g., Class 10 - Mathematics)
CREATE TABLE IF NOT EXISTS subject_class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_id INT NOT NULL,
    subject_name VARCHAR(255) NOT NULL,
    INDEX idx_subject_class_class_id (class_id),
    INDEX idx_subject_class_subject_name (subject_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Extra subjects (Music, Dance, Sports, etc.)
CREATE TABLE IF NOT EXISTS extra_subject (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    extra_subject_name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- DOCUMENTS TABLE (Legal Documents - Privacy Policy, Terms, etc.)
-- ===========================================================================

CREATE TABLE IF NOT EXISTS documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    version VARCHAR(20) NOT NULL,
    published_at DATETIME(6) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    lock_version BIGINT,
    CONSTRAINT uc_document_type_version UNIQUE (type, version),
    INDEX idx_document_type (type),
    INDEX idx_document_type_publishedAt (type, published_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- TEACHER AGREEMENTS TABLE
-- ===========================================================================

CREATE TABLE IF NOT EXISTS teacher_agreements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    privacy_policy_id BIGINT NOT NULL,
    privacy_policy_accepted_at DATETIME(6) NOT NULL,
    terms_of_use_id BIGINT NOT NULL,
    terms_of_use_accepted_at DATETIME(6) NOT NULL,
    teacher_agreement_id BIGINT NOT NULL,
    teacher_agreement_accepted_at DATETIME(6) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    CONSTRAINT fk_ta_privacy_policy FOREIGN KEY (privacy_policy_id) REFERENCES documents(id),
    CONSTRAINT fk_ta_terms_of_use FOREIGN KEY (terms_of_use_id) REFERENCES documents(id),
    CONSTRAINT fk_ta_teacher_agreement FOREIGN KEY (teacher_agreement_id) REFERENCES documents(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- TEACHER TABLE (Main Entity)
-- ===========================================================================

CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- Basic Information
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(255),
    whatsapp_number VARCHAR(255),
    
    -- Authentication
    password VARCHAR(255),
    
    -- Email Verification
    email_verified BIT(1) NOT NULL DEFAULT 0,
    email_otp VARCHAR(255),
    email_otp_generated_at DATETIME(6),
    email_verified_at DATETIME(6),
    
    -- OTP Brute Force Protection
    otp_attempts INT DEFAULT 0,
    otp_locked_until DATETIME(6),
    
    -- Profile Information
    about_me VARCHAR(2000),
    date_of_birth DATETIME(6),
    experience INT,
    gender VARCHAR(255),
    
    -- Location and Transport
    has_vehicle VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    pin VARCHAR(255),
    state VARCHAR(255),
    country VARCHAR(255),
    
    -- Teaching Preferences
    mode VARCHAR(255),
    expected_fee_per_hour INT,
    
    -- Qualifications
    qualifications VARCHAR(1000),
    certifications VARCHAR(1000),
    
    -- Agreement Reference (One-to-One)
    teacher_agreement_id BIGINT UNIQUE,
    
    -- Audit
    created_at DATETIME(6) NOT NULL,
    
    -- Indexes
    INDEX idx_teacher_email (email),
    INDEX idx_teacher_email_verified (email_verified),
    INDEX idx_teacher_created_at (created_at),
    INDEX idx_teacher_email_verified_created (email_verified, created_at),
    INDEX idx_teacher_full_name_created (full_name, created_at),
    
    -- Foreign Key
    CONSTRAINT fk_teacher_agreement FOREIGN KEY (teacher_agreement_id) REFERENCES teacher_agreements(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- TEACHER RELATED TABLES
-- ===========================================================================

-- Teacher Availability (Time Slots)
CREATE TABLE IF NOT EXISTS teacher_availability (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    start_time INT NOT NULL,
    end_time INT NOT NULL,
    available_time_for_slot INT NOT NULL,
    total_day_availability_for_slot INT NOT NULL,
    monday BIT(1) NOT NULL,
    tuesday BIT(1) NOT NULL,
    wednesday BIT(1) NOT NULL,
    thursday BIT(1) NOT NULL,
    friday BIT(1) NOT NULL,
    saturday BIT(1) NOT NULL,
    sunday BIT(1) NOT NULL,
    INDEX idx_teacher_availability_teacher_id (teacher_id),
    CONSTRAINT fk_availability_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Teacher Education
CREATE TABLE IF NOT EXISTS teacher_education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    degree VARCHAR(255) NOT NULL,
    institution VARCHAR(255) NOT NULL,
    passing_year INT NOT NULL,
    grade VARCHAR(255) NOT NULL,
    INDEX idx_teacher_education_teacher_id (teacher_id),
    CONSTRAINT fk_education_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Teacher Subject Mapping (Junction Table)
CREATE TABLE IF NOT EXISTS teacher_subject_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    subject_class_id BIGINT NOT NULL,
    INDEX idx_tsm_teacher_id (teacher_id),
    INDEX idx_tsm_subject_class_id (subject_class_id),
    CONSTRAINT fk_tsm_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
    CONSTRAINT fk_tsm_subject_class FOREIGN KEY (subject_class_id) REFERENCES subject_class(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Teacher Extra Subject Mapping (Junction Table)
CREATE TABLE IF NOT EXISTS teacher_extra_subject_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    extra_subject_id BIGINT NOT NULL,
    INDEX idx_tesm_teacher_id (teacher_id),
    INDEX idx_tesm_extra_subject_id (extra_subject_id),
    CONSTRAINT fk_tesm_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
    CONSTRAINT fk_tesm_extra_subject FOREIGN KEY (extra_subject_id) REFERENCES extra_subject(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Teacher Preferred Area Mapping
CREATE TABLE IF NOT EXISTS teacher_preferred_area_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    area VARCHAR(255) NOT NULL,
    INDEX idx_tpam_teacher_id (teacher_id),
    CONSTRAINT fk_tpam_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- INQUIRY TABLE (Student/Parent Inquiries)
-- ===========================================================================

CREATE TABLE IF NOT EXISTS inquiry (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    class_standard VARCHAR(255),
    board VARCHAR(255),
    address VARCHAR(500),
    message VARCHAR(500),
    selected_start_date DATE,
    selected_end_date DATE,
    selected_start_time INT,
    selected_end_time INT,
    privacy_accepted BIT(1) NOT NULL,
    privacy_version VARCHAR(255),
    privacy_accepted_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    INDEX idx_inquiry_phone (phone),
    INDEX idx_inquiry_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inquiry Subject Class Mapping (Junction Table)
CREATE TABLE IF NOT EXISTS inquiry_subject_class_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inquiry_id BIGINT NOT NULL,
    subject_class_id BIGINT NOT NULL,
    INDEX idx_iscm_inquiry_id (inquiry_id),
    INDEX idx_iscm_subject_class_id (subject_class_id),
    CONSTRAINT fk_iscm_inquiry FOREIGN KEY (inquiry_id) REFERENCES inquiry(id) ON DELETE CASCADE,
    CONSTRAINT fk_iscm_subject_class FOREIGN KEY (subject_class_id) REFERENCES subject_class(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inquiry Extra Subject Mapping (Junction Table)
CREATE TABLE IF NOT EXISTS inquiry_extra_subject_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inquiry_id BIGINT NOT NULL,
    extra_subject_id BIGINT NOT NULL,
    INDEX idx_iesm_inquiry_id (inquiry_id),
    INDEX idx_iesm_extra_subject_id (extra_subject_id),
    CONSTRAINT fk_iesm_inquiry FOREIGN KEY (inquiry_id) REFERENCES inquiry(id) ON DELETE CASCADE,
    CONSTRAINT fk_iesm_extra_subject FOREIGN KEY (extra_subject_id) REFERENCES extra_subject(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- EMAIL RATE LIMITING TABLE (Security)
-- ===========================================================================

CREATE TABLE IF NOT EXISTS email_rate_limit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255),
    email_type VARCHAR(50) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    INDEX idx_email_created (email, created_at),
    INDEX idx_ip_created (ip_address, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- END OF MIGRATION
-- ===========================================================================
