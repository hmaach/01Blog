-- Migration: create_post_table

CREATE TABLE posts (
    id UUID PRIMARY KEY,
    title VARCHAR NOT NULL,
    body TEXT NOT NULL,
    user_id UUID NOT NULL,
    status VARCHAR,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);