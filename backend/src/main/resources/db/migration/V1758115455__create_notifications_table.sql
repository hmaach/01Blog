-- Migration: create_notifications_table

CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    post_owner_id UUID,
    post_id UUID,
    seen BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_notifications_owner FOREIGN KEY (post_owner_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_notifications_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);