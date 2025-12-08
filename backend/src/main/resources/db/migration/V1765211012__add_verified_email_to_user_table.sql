-- Migration: add_verified_email_to_user_table

ALTER TABLE users ADD COLUMN verified_email BOOLEAN DEFAULT FALSE;