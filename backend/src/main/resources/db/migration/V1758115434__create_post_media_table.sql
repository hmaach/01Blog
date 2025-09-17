-- Migration: create_post_media_table

CREATE TABLE post_media (
    post_id UUID NOT NULL REFERENCES posts (id),
    media_id UUID NOT NULL REFERENCES media (id),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (post_id, media_id)
);