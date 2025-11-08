-- Migration: create_subscriptions_table

CREATE TABLE subscriptions (
    subscriber_id UUID NOT NULL,
    subscribed_to_id UUID NOT NULL,
    PRIMARY KEY (subscriber_id, subscribed_to_id),
    CONSTRAINT fk_subscriptions_subscriber FOREIGN KEY (subscriber_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_subscriptions_target FOREIGN KEY (subscribed_to_id) REFERENCES users (id) ON DELETE CASCADE
);