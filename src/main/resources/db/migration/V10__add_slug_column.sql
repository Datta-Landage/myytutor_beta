-- Add slug column to teacher table
ALTER TABLE teacher ADD COLUMN slug VARCHAR(255);

-- Create unique index on slug
CREATE UNIQUE INDEX idx_teacher_slug ON teacher(slug);
