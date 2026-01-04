-- ===========================================================================
-- V7__add_inquiry_legal_flags.sql
-- Purpose: Add legal acceptance flags for Terms of Use and User Agreement to Inquiry table
-- ===========================================================================

ALTER TABLE inquiry
ADD COLUMN terms_accepted BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN terms_version VARCHAR(50),
ADD COLUMN terms_accepted_at DATETIME,
ADD COLUMN user_agreement_accepted BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN user_agreement_version VARCHAR(50),
ADD COLUMN user_agreement_accepted_at DATETIME;
