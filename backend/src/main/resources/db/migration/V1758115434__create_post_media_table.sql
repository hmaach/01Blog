-- Migration: create_post_media_table

CREATE TABLE post_media (
    post_id UUID NOT NULL,
    media_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (post_id, media_id),
    CONSTRAINT fk_postmedia_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_postmedia_media FOREIGN KEY (media_id) REFERENCES media (id) ON DELETE CASCADE
);