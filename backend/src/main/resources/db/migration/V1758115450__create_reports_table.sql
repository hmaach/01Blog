-- Migration: create_reports_table

CREATE TABLE reports (
    id UUID PRIMARY KEY,
    reporter_id UUID NOT NULL,
    reported_user_id UUID NOT NULL,
    reported_post_id UUID,
    reported_comment_id UUID,
    category VARCHAR NOT NULL,
    reason TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'waiting',
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_reports_reporter FOREIGN KEY (reporter_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_reports_reported_user FOREIGN KEY (reported_user_id) REFERENCES users (id) ON DELETE SET NULL,
    CONSTRAINT fk_reports_reported_post FOREIGN KEY (reported_post_id) REFERENCES posts (id) ON DELETE SET NULL
);