-- Migration: create_user_table

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    name VARCHAR NOT NULL,
    username VARCHAR NOT NULL UNIQUE,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    avatar_media_id UUID,
    impressions_count INT DEFAULT 0,
    posts_count INT DEFAULT 0,
    readme TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);