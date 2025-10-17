# Security

## JWT Configuration

**Token Structure**:
```json
{
  "sub": "user-id",
  "username": "john_doe",
  "roles": ["ROLE_USER"],
  "iat": 1729166400,
  "exp": 1729252800
}
```

**Security Features**:
- Password encryption (BCrypt)
- Token expiration (24 hours default)
- Role-based access control
- CORS configuration
- CSRF protection

## Role Hierarchy

- **USER**: Default role, basic platform access
- **ADMIN**: Full platform management access
