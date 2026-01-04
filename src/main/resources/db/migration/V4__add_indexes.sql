-- ===========================================================================
-- V4__add_indexes.sql
-- Add necessary indexes for teacher search and filtering
-- Created: 2026-01-01
-- ===========================================================================

-- Index for location filtering
CREATE INDEX idx_teacher_city ON teacher(city);
CREATE INDEX idx_teacher_pin ON teacher(pin);
CREATE INDEX idx_teacher_state ON teacher(state);

-- Index for other common filters
CREATE INDEX idx_teacher_mode ON teacher(mode);
CREATE INDEX idx_teacher_gender ON teacher(gender);
CREATE INDEX idx_teacher_experience ON teacher(experience);
CREATE INDEX idx_teacher_expected_fee ON teacher(expected_fee_per_hour);

-- ===========================================================================
-- SCHEDULER & SECURITY OPTIMIZATIONS
-- ===========================================================================

-- 1. Teacher Cleanup Scheduler
-- Query: deleteByEmailVerifiedFalseAndEmailOtpGeneratedAtBefore
-- Query: clearExpiredOtps (emailVerified = true AND emailOtpGeneratedAt < time)
CREATE INDEX idx_teacher_email_verified_otp ON teacher(email_verified, email_otp_generated_at);

-- 2. Email Rate Limiting Service
-- Query: countByEmailAndTypeAndCreatedAtAfter
-- Existing index (email, created_at) is good, but adding type makes it perfect covering index
CREATE INDEX idx_erl_email_type_created ON email_rate_limit(email, email_type, created_at);

-- Query: countByIpAddressAndTypeAndCreatedAtAfter
CREATE INDEX idx_erl_ip_type_created ON email_rate_limit(ip_address, email_type, created_at);

-- Query: deleteOldRecords (cleanup)
CREATE INDEX idx_erl_created_at ON email_rate_limit(created_at);

-- 3. Inquiry Repository
-- Query: countByPhoneAndCreatedAtBetween
CREATE INDEX idx_inquiry_phone_created ON inquiry(phone, created_at);

