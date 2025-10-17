# API Documentation

## Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | User login | ❌ |
| GET | `/api/auth/me` | Get current user | ✅ |

## User Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/{id}` | Get user profile | ❌ |
| PUT | `/api/users/{id}` | Update profile | ✅ (Owner) |
| POST | `/api/users/{id}/avatar` | Upload avatar | ✅ (Owner) |
| GET | `/api/users/{id}/posts` | Get user posts | ❌ |

## Post Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/posts` | List all posts | ❌ |
| GET | `/api/posts/{id}` | Get post details | ❌ |
| POST | `/api/posts` | Create post | ✅ |
| PUT | `/api/posts/{id}` | Update post | ✅ (Owner/Admin) |
| DELETE | `/api/posts/{id}` | Delete post | ✅ (Owner/Admin) |
| POST | `/api/posts/{id}/like` | Like post | ✅ |
| DELETE | `/api/posts/{id}/like` | Unlike post | ✅ |

## Comment Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/posts/{postId}/comments` | Get comments | ❌ |
| POST | `/api/posts/{postId}/comments` | Add comment | ✅ |
| PUT | `/api/comments/{id}` | Update comment | ✅ (Owner) |
| DELETE | `/api/comments/{id}` | Delete comment | ✅ (Owner/Admin) |

## Media Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/media/upload` | Upload files | ✅ |
| GET | `/api/media/{id}` | Get media file | ❌ |
| DELETE | `/api/media/{id}` | Delete media | ✅ (Owner) |

## Admin Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/admin/users` | List all users | ✅ (Admin) |
| PUT | `/api/admin/users/{id}/block` | Block user | ✅ (Admin) |
| PUT | `/api/admin/users/{id}/role` | Update user role | ✅ (Admin) |
| GET | `/api/admin/reports` | View reports | ✅ (Admin) |
| PUT | `/api/admin/reports/{id}` | Resolve report | ✅ (Admin) |

## Request/Response Examples

**Register User**
```json
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe"
}

Response: 201 Created
{
  "id": "uuid",
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2025-10-17T10:30:00Z"
}
```

**Create Post**
```json
POST /api/posts
Authorization: Bearer {token}
{
  "title": "My Learning Journey",
  "content": "Today I learned about Clean Architecture...",
  "mediaIds": ["media-uuid-1", "media-uuid-2"]
}

Response: 201 Created
{
  "id": "post-uuid",
  "title": "My Learning Journey",
  "content": "Today I learned...",
  "author": {
    "id": "user-uuid",
    "username": "john_doe"
  },
  "likeCount": 0,
  "commentCount": 0,
  "createdAt": "2025-10-17T10:30:00Z"
}
```