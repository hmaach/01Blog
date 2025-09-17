-- Migration: create_likes_table

CREATE TABLE likes (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id),
    post_id UUID NOT NULL REFERENCES posts (id),
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT unique_post_user UNIQUE (post_id, user_id)
);