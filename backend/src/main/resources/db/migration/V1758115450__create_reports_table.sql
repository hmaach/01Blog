-- Migration: create_reports_table

CREATE TABLE reports (
    id UUID PRIMARY KEY,
    reporter_id UUID NOT NULL REFERENCES users (id),
    reported_user_id UUID NOT NULL REFERENCES users (id),
    reported_post_id UUID REFERENCES posts (id),
    reason TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    created_at TIMESTAMPTZ NOT NULL
);