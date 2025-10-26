-- Migration: create_indexes
-- USERS
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_created_at ON users(created_at);

-- POSTS
CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_status ON posts(status);
CREATE INDEX idx_posts_created_at ON posts(created_at);

-- MEDIA
CREATE INDEX idx_media_user_id ON media(user_id);
CREATE INDEX idx_media_type ON media(media_type);

-- COMMENTS
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_comments_created_at ON comments(created_at);

-- LIKES
CREATE INDEX idx_likes_post_id ON likes(post_id);
CREATE INDEX idx_likes_user_id ON likes(user_id);

-- SUBSCRIPTIONS
CREATE INDEX idx_subscriptions_subscriber ON subscriptions(subscriber_id);
CREATE INDEX idx_subscriptions_target ON subscriptions(subscribed_to_id);

-- REPORTS
CREATE INDEX idx_reports_reporter_id ON reports(reporter_id);
CREATE INDEX idx_reports_status ON reports(status);
CREATE INDEX idx_reports_created_at ON reports(created_at);

-- NOTIFICATIONS
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_type ON notifications(type);
CREATE INDEX idx_notifications_created_at ON notifications(created_at);

-- UNSEENED NOTIFICATIONS
CREATE INDEX idx_unseened_notifications_user_id ON unseened_notifications(user_id);
CREATE INDEX idx_unseened_notifications_notification_id ON unseened_notifications(notification_id);
