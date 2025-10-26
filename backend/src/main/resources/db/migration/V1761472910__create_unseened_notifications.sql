-- Migration: create_unseened_notifications

CREATE TABLE unseened_notifications (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    notification_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_unseened_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_unseened_notification FOREIGN KEY (notification_id) REFERENCES notifications (id) ON DELETE CASCADE
);