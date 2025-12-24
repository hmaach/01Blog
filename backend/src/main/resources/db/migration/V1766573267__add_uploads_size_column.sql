-- Migration: add_uploads_size_column


ALTER TABLE users ADD COLUMN uploads_size BIGINT DEFAULT 0;