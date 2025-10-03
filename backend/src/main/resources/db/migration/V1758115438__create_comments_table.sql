-- Migration: create_comments_table

CREATE TABLE comments (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id),
    post_id UUID NOT NULL REFERENCES posts (id),
    text TEXT NOT NULL,
    media_id UUID REFERENCES media (id),
    created_at TIMESTAMPTZ NOT NULL
);