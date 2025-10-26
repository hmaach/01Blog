-- Migration: create_notifications_table

CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    post_id UUID,
    type VARCHAR(20) NOT NULL DEFAULT 'direct',
    payload JSONB,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_notifications_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);