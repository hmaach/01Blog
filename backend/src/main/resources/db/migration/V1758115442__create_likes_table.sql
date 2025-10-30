-- Migration: create_likes_table

CREATE TABLE likes (
    user_id UUID NOT NULL,
    post_id UUID NOT NULL,
    PRIMARY KEY (user_id, post_id),
    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);