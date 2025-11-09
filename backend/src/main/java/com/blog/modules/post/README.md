# Post Module

## Overview

The **Post Module** manages content creation and social interactions such as comments and likes.  
It defines the core logic for posts, ensuring scalability and clean separation between the domain and infrastructure layers.

---

## Responsibilities

- CRUD operations for posts
- Commenting and nested replies
- Liking/unliking posts
- Event publishing for engagement
- Post feed pagination

---

## Architecture

```

post/
├── domain/
│   ├── model/      # Post, Comment, Like
│   ├── port/       # Interfaces
│   └── event/      # Domain events
├── application/     # Services & event handlers
└── infrastructure/  # Controllers, repositories, config

```

---

## Main Components

| Layer          | Component                                                  | Description              |
| -------------- | ---------------------------------------------------------- | ------------------------ |
| Domain         | `Post`, `Comment`, `Like`                                  | Core models              |
| Domain         | `PostService`, `CommentService`, `LikeService`             | Ports (interfaces)       |
| Application    | `PostServiceImpl`, `CommentServiceImpl`, `LikeServiceImpl` | Use case implementations |
| Infrastructure | `PostController`, `CommentController`                      | API endpoints            |
| Infrastructure | `PostRepositoryImpl`, `LikeRepositoryImpl`                 | Persistence adapters     |
| Infrastructure | `PostMapper`, `CommentMapper`                              | Entity ↔ DTO conversions |

---

## API Endpoints

| Method | Endpoint                  | Description        |
| ------ | ------------------------- | ------------------ |
| `POST` | `/api/posts`              | Create new post    |
| `GET`  | `/api/posts`              | Fetch feed posts   |
| `GET`  | `/api/posts/{id}`         | Get post by ID     |
| `POST` | `/api/posts/{id}/like`    | Like/unlike a post |
| `POST` | `/api/posts/{id}/comment` | Add a comment      |

---

## Domain Events

- `PostLikedEvent`
- `PostCommentedEvent`
- `PostFetchedEvent`
- `PostUnlikedEvent`
- `PostsFetchedEvent`

---

## Exceptions

- `PostNotFoundException`
- `CommentNotFoundException`
- `DuplicateLikeException`
