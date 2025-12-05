# **API Documentation**

# **1. Error Response Format**

```json
{
  "error": "UNAUTHORIZED",
  "message": "Authentication is required",
  "status": 401
}
```

---

# **2. Authentication Mechanism**

### **Authentication Endpoints**

| Method | Path                 | Handler                   | Description       | Auth |
| ------ | -------------------- | ------------------------- | ----------------- | ---- |
| POST   | `/api/auth/login`    | `AuthController#login`    | User login        | No   |
| POST   | `/api/auth/register` | `AuthController#register` | Register new user | No   |

### **JWT Authentication (Bearer Token)**

All protected endpoints require:

```
Authorization: Bearer <access_token>
```

### **Token Behavior**

* Access tokens are **short-lived**
* On expiration → client must log in again
* The backend:

  1. Extracts JWT
  2. Validates signature/expiration
  3. Injects authenticated user ID into request

---

# **3. Pagination & Query Parameters**

All list endpoints may include:

| Param    | Description                                                   |
| -------- | ------------------------------------------------------------- |
| `before` | Filter: return items **created before** timestamp (ISO-8601). |
| `size`   | Number of items to return (default: `10`).                    |
| `query`  | Text search term (users, explore posts, admin users).         |

Format for `before`:

```
2025-01-20T12:30:55Z
```

---

# **4. User Module**

| Method | Path                                 | Handler             | Description                     | Auth |
| ------ | ------------------------------------ | ------------------- | ------------------------------- | ---- |
| GET    | `/api/user`                          | `getCurrentUser`    | Get current authenticated user  | Yes  |
| PATCH  | `/api/user`                          | `updateUser`        | Update current user             | Yes  |
| GET    | `/api/user/id/{id}`                  | `getUserById`       | Get user by ID                  | Yes  |
| GET    | `/api/user/{username}`               | `getUserByUsername` | Get user by username            | Yes  |
| GET    | `/api/user/all`                      | `getUsers`          | List users (pagination + query) | Yes  |
| DELETE | `/api/user/subscribe/{targetUserId}` | `unsubscribeToUser` | Unsubscribe from user           | Yes  |

### **Query Parameters for `/api/user/all`**

```
query?: string
before?: Instant
size?: number (default: 10)
```

---

# **5. Post Module**

| Method | Path                         | Handler                 | Description                  | Auth |
| ------ | ---------------------------- | ----------------------- | ---------------------------- | ---- |
| GET    | `/api/posts/feed`            | `getFeedPosts`          | Posts from followed accounts | Yes  |
| GET    | `/api/posts/explore`         | `getExplorePosts`       | All posts + optional search  | Yes  |
| GET    | `/api/posts/user/{username}` | `getPostByUserUsername` | Posts by specific user       | Yes  |
| GET    | `/api/posts/{postId}`        | `getPost`               | Get post by ID               | Yes  |
| PATCH  | `/api/posts/{postId}`        | `updatePost`            | Update post                  | Yes  |
| DELETE | `/api/posts/{postId}`        | `deletePost`            | Delete post                  | Yes  |
| POST   | `/api/posts/like/{postId}`   | `likePost`              | Like post                    | Yes  |

### **Query Parameters**

For:

* `/api/posts/feed`
* `/api/posts/explore`
* `/api/posts/user/{username}`

```
before?: Instant
size?: number (default: 10)
query?: string (explore only)
```

---

# **6. Comment Module**

| Method | Path                                | Handler          | Description                        | Auth |
| ------ | ----------------------------------- | ---------------- | ---------------------------------- | ---- |
| GET    | `/api/comments/{postId}`            | `getAllComments` | List comments for post (paginated) | Yes  |
| GET    | `/api/comments/details/{commentId}` | `getComment`     | Get comment details                | Yes  |
| DELETE | `/api/comments/{commentId}`         | `deleteComment`  | Delete comment                     | Yes  |

### **Query Parameters for `/api/comments/{postId}`**

```
before?: Instant
size?: number (default: 10)
```

---

# **7. Media Module**

| Method | Path                            | Handler           | Description       | Auth |
| ------ | ------------------------------- | ----------------- | ----------------- | ---- |
| POST   | `/api/media/posts`              | `uploadPostMedia` | Upload post media | Yes  |
| POST   | `/api/media/avatar`             | `uploadAvatar`    | Upload avatar     | Yes  |
| GET    | `/api/media/posts/{filename}`   | `getPostMedia`    | Get post media    | Yes  |
| GET    | `/api/media/avatars/{filename}` | `getAvatar`       | Get avatar        | Yes  |
| DELETE | `/api/media/{mediaId}`          | `deleteMedia`     | Delete media      | Yes  |

---

## **Media Upload Guidelines**

### **Accepted File Types**

* **Images:**

  * `image/jpeg`, `image/png`, `image/jpg`

* **Videos:**

  * `video/mp4`, `video/webm`

### **Max File Size**

| Type       | Max Size  |
| ---------- | --------- |
| Avatar     | **2 MB**  |
| Post Media | **10 MB** |

### **Upload Process**

1. Client sends file via `multipart/form-data`
2. Server generates unique filename & stores media
3. Server responds with:

```json
"UUID"
```

### **Serving Media**

Media is **not publicly accessible**.

Clients must fetch as `Blob` and convert:

```ts
URL.createObjectURL(blob);
```

---

# **8. Admin Module**

| Method | Path                                      | Handler              | Description              | Auth  |
| ------ | ----------------------------------------- | -------------------- | ------------------------ | ----- |
| GET    | `/api/admin/users`                        | `getUsers`           | List all users           | Admin |
| DELETE | `/api/admin/users/{userId}`               | `deleteUser`         | Delete user              | Admin |
| GET    | `/api/admin/reports`                      | `findAll`            | List reports (paginated) | Admin |
| DELETE | `/api/admin/reports/{reportId}`           | `deleteReportStatus` | Delete report            | Admin |
| DELETE | `/api/admin/comments/{commentId}`         | `deleteComment`      | Delete comment           | Admin |
| PATCH  | `/api/admin/posts/change-status/{postId}` | `likePost`           | Change post status       | Admin |

### **Query Parameters for `/api/admin/users`**

```
query?: string   // name or username
before?: Instant
size?: number (default: 10)
```

### **Query Parameters for `/api/admin/reports`**

```
before?: Instant
size?: number (default: 10)
```