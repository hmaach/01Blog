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
| GET      | `/products`      | ❌              | -          | List all products        | -> done 
| GET      | `/products/{id}` | ❌              | -          | Get product by ID        | -> done 
| POST     | `/products`      | ✅              | Admin      | Create a new product     | -> done 
| PATCH    | `/products/{id}` | ✅              | Admin      | Update product           | -> done 
| DELETE   | `/products/{id}` | ✅              | Admin      | Delete product           | -> done 
