-- Migration: create_subscriptions_table

CREATE TABLE subscriptions (
    id UUID PRIMARY KEY,
    subscriber_id UUID NOT NULL REFERENCES users (id),
    subscribed_to_id UUID NOT NULL REFERENCES users (id),
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT unique_subscriber_subscribed UNIQUE (
        subscriber_id,
        subscribed_to_id
    )
);