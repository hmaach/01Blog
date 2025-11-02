-- Migration: create_media_table

CREATE TABLE media (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    media_type VARCHAR NOT NULL,
    related_to VARCHAR NOT NULL,
    size INT NOT NULL,
    url TEXT NOT NULL,
    uploaded_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_media_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);