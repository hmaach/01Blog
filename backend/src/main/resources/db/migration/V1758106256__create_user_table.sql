-- Migration: create_user_table

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    username VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    avatar_media_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);