#  Database

## Schema Overview

The database uses **Flyway** for version control and migrations.

**Core Tables**:
- `users` - User accounts and profiles
- `posts` - Blog posts and content
- `comments` - Post comments
- `likes` - Post likes
- `media` - Uploaded files metadata
- `post_media` - Post-media relationships
- `subscriptions` - User follows
- `reports` - Content reports
- `notifications` - User notifications

## Migration Files

Located in `src/main/resources/db/migration/`:

```
V1758106256__create_user_table.sql
V1758110314__create_post_table.sql
V1758115427__create_media_table.sql
V1758115434__create_post_media_table.sql
V1758115438__create_comments_table.sql
V1758115442__create_likes_table.sql
V1758115446__create_subscriptions_table.sql
V1758115450__create_reports_table.sql
V1758115455__create_notifications_table.sql
V1759577620__create_indexes.sql
```

## Running Migrations

```bash
# Run all pending migrations
./mvnw flyway:migrate

# View migration info
./mvnw flyway:info

# Rollback (if configured)
./mvnw flyway:undo
```

**View the complete schema**: [Database Diagram →](https://dbdiagram.io/d/01Blog-68c981561ff9c616bdf62bbc)
