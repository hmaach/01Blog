-- Migration: create_indexes

-- ========== USERS ==========
CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_created_at ON users (created_at);

-- ========== POSTS ==========
CREATE INDEX idx_posts_user_id ON posts (user_id);
CREATE INDEX idx_posts_created_at ON posts (created_at);
CREATE INDEX idx_posts_status ON posts (status);

-- ========== MEDIA ==========
CREATE INDEX idx_media_user_id ON media (user_id);
CREATE INDEX idx_media_uploaded_at ON media (uploaded_at);
CREATE INDEX idx_media_type ON media (media_type);

-- ========== POST_MEDIA ==========
CREATE INDEX idx_post_media_post_id ON post_media (post_id);
CREATE INDEX idx_post_media_media_id ON post_media (media_id);
CREATE UNIQUE INDEX idx_post_media_unique ON post_media (post_id, media_id);

-- ========== COMMENTS ==========
CREATE INDEX idx_comments_post_id ON comments (post_id);
CREATE INDEX idx_comments_user_id ON comments (user_id);
CREATE INDEX idx_comments_created_at ON comments (created_at);

-- ========== LIKES ==========
-- The combination (post_id, user_id) is already unique.
CREATE UNIQUE INDEX idx_likes_post_user_unique ON likes (post_id, user_id);
CREATE INDEX idx_likes_post_id ON likes (post_id);
CREATE INDEX idx_likes_user_id ON likes (user_id);
CREATE INDEX idx_likes_created_at ON likes (created_at);

-- ========== SUBSCRIPTIONS ==========
CREATE UNIQUE INDEX idx_subscriptions_unique ON subscriptions (subscriber_id, subscribed_to_id);
CREATE INDEX idx_subscriptions_subscriber_id ON subscriptions (subscriber_id);
CREATE INDEX idx_subscriptions_subscribed_to_id ON subscriptions (subscribed_to_id);
CREATE INDEX idx_subscriptions_created_at ON subscriptions (created_at);

-- ========== REPORTS ==========
CREATE INDEX idx_reports_reporter_id ON reports (reporter_id);
CREATE INDEX idx_reports_reported_user_id ON reports (reported_user_id);
CREATE INDEX idx_reports_reported_post_id ON reports (reported_post_id);
CREATE INDEX idx_reports_status ON reports (status);
CREATE INDEX idx_reports_created_at ON reports (created_at);

-- ========== NOTIFICATIONS ==========
CREATE INDEX idx_notifications_user_id ON notifications (user_id);
CREATE INDEX idx_notifications_is_read ON notifications (is_read);
CREATE INDEX idx_notifications_created_at ON notifications (created_at);
CREATE INDEX idx_notifications_type ON notifications (type);

