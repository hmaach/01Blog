-- Migration: create_post_table

CREATE TABLE posts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    title VARCHAR NOT NULL,
    body TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'active',
    likes_count INT DEFAULT 0,
    comments_count INT DEFAULT 0,
    impressions_count INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);