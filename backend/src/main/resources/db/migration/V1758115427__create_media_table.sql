-- Migration: create_media_table
CREATE TABLE media (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL REFERENCES users (id),
    media_type VARCHAR NOT NULL,
    url TEXT NOT NULL,
    uploaded_at TIMESTAMP NOT NULL
);