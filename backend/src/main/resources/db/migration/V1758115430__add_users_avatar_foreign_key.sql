-- Migration: add_users_avatar_foreign_key

ALTER TABLE users
ADD CONSTRAINT fk_users_avatar_media
FOREIGN KEY (avatar_media_id)
REFERENCES media (id)
ON DELETE SET NULL;
