# 🛡️ Admin Module

## Overview

The **Admin Module** provides moderation tools and management features for platform administrators.  
It allows handling of users, posts, and reports through dedicated REST endpoints.

---

## Responsibilities

- Manage users (block, assign roles)
- Moderate posts and comments
- Handle user reports
- View platform statistics

---

## Architecture

```

admin/
├── domain/
│   └── port/       # Service interfaces
├── application/     # Service implementations
└── infrastructure/  # Controllers, DTOs, config

```

---

## Main Components

| Layer | Component | Description |
|--------|------------|-------------|
| Domain | `AdminUserService`, `AdminPostService`, `AdminReportService` | Ports |
| Application | `AdminUserServiceImpl`, `AdminPostServiceImpl`, `AdminReportServiceImpl` | Use cases |
| Infrastructure | `AdminController`, `AdminUserController`, `AdminReportController` | Endpoints |
| Infrastructure | `CreateUserCommand`, `UpdateUserRoleCommand` | DTOs |
| Infrastructure | `AdminModuleConfig` | Module config |

---

## API Endpoints

| Method | Endpoint | Description |
|---------|-----------|-------------|
| `GET` | `/api/admin/users` | List all users |
| `PATCH` | `/api/admin/users/{id}/role` | Update user role |
| `DELETE` | `/api/admin/posts/{id}` | Delete a post |
| `GET` | `/api/admin/reports` | Get all reports |

