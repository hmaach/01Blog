| Method   | Endpoint         |  Auth Required  | Roles      | Description              |
| -------- | ---------------- | --------------- | ---------- | ------------------------ |
| POST     | `/auth/register` | ❌              | -          | Register a new user      | -> done 
| POST     | `/auth/login`    | ❌              | -          | User login               | -> done 
| GET      | `/users`         | ✅              | Admin      | List all users           | -> done 
| GET      | `/users/{id}`    | ✅              | Admin/User | Get user by ID           | -> done 
| GET      | `/users/me`      | ✅              | Any        | Get current user profile | -> done 
| POST     | `/users`         | ✅              | Admin      | Create user              | -> done 
| PATCH    | `/users/{id}`    | ✅              | Admin/User | Update user details      | -> done 
| DELETE   | `/users/{id}`    | ✅              | Admin/User | Delete user              | -> done 
| GET      | `/posts`      | ❌              | -          | List all posts        | -> done 
| GET      | `/posts/{id}` | ❌              | -          | Get post by ID        | -> done 
| POST     | `/posts`      | ✅              | Admin      | Create a new post     | -> done 
| PATCH    | `/posts/{id}` | ✅              | Admin      | Update post           | -> done 
| DELETE   | `/posts/{id}` | ✅              | Admin      | Delete post           | -> done 
