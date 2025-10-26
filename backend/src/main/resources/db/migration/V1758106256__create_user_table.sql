-- Migration: create_user_table

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    username VARCHAR NOT NULL UNIQUE,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    status VARCHAR NOT NULL DEFAULT 'active',
    role VARCHAR NOT NULL,
    avatar_media_id UUID,
    subscribers_count INT DEFAULT 0,
    impressions_count INT DEFAULT 0,
    posts_count INT DEFAULT 0,
    readme TEXT,
    created_at TIMESTAMP NOT NULL
);