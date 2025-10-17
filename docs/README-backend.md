# 01Blog - Backend Documentation

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Modules](#modules)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database](#database)
- [Security](#security)

---

## Overview

The 01Blog backend is a robust RESTful API built with **Java Spring Boot**, following **Clean Architecture** principles. It provides a scalable, maintainable foundation for the blogging platform with clear separation of concerns and dependency inversion.

### Core Technologies

- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Database**: PostgreSQL
- **Migration**: Flyway
- **Security**: Spring Security + JWT
- **Build Tool**: Maven

---

## Architecture

### Clean Architecture Layers

The backend follows a modular Clean Architecture approach with four distinct layers:

```
┌─────────────────────────────────────────┐
│         Web Controllers (API)           │ ← Infrastructure Layer
├─────────────────────────────────────────┤
│         Service Implementations         │ ← Application Layer
├─────────────────────────────────────────┤
│      Domain Models & Interfaces         │ ← Domain Layer
├─────────────────────────────────────────┤
│    Database & External Integrations     │ ← Infrastructure Layer
└─────────────────────────────────────────┘
```

### Layer Responsibilities

#### 1. **Domain Layer** (`domain/`)
- **Purpose**: Pure business logic, framework-independent
- **Contains**:
  - Domain models (entities)
  - Business exceptions
  - Port interfaces (contracts)
  - Domain events
- **Dependencies**: None (fully isolated)

#### 2. **Application Layer** (`application/`)
- **Purpose**: Orchestrates business workflows
- **Contains**:
  - Service implementations
  - Use case handlers
  - Event listeners
  - Business validators
- **Dependencies**: Domain layer only

#### 3. **Infrastructure Layer** (`infrastructure/`)
- **Purpose**: External integrations and technical concerns
- **Contains**:
  - **Inbound Adapters** (`adapter/in/web/`):
    - REST controllers
    - DTOs and commands
    - Request/response mapping
  - **Outbound Adapters** (`adapter/out/persistence/`):
    - JPA entities
    - Repository implementations
    - Database mappers
  - **Configuration** (`config/`):
    - Module-specific configs
    - Bean definitions

---

## Project Structure

```
src/main/java/com/blog/
│
├── modules/                         # Feature modules
│   ├── user/                        # User management
│   │   ├── domain/
│   │   │   ├── model/User.java
│   │   │   ├── port/in/UserService.java
│   │   │   ├── port/out/UserRepository.java
│   │   │   └── exception/
│   │   ├── application/
│   │   │   └── service/UserServiceImpl.java
│   │   └── infrastructure/
│   │       ├── adapter/
│   │       │   ├── in/web/
│   │       │   │   ├── UserController.java
│   │       │   │   └── dto/
│   │       │   └── out/persistence/
│   │       │       ├── UserEntity.java
│   │       │       ├── UserMapper.java
│   │       │       └── UserRepositoryImpl.java
│   │       └── config/UserModuleConfig.java
│   │
│   ├── post/                        # Post & engagement
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Post.java
│   │   │   │   ├── Comment.java
│   │   │   │   └── Like.java
│   │   │   ├── port/
│   │   │   ├── event/                  # Domain events
│   │   │   └── exception/
│   │   ├── application/
│   │   │   ├── service/
│   │   │   └── handler/                # Event handlers
│   │   └── infrastructure/
│   │
│   ├── media/                       # File uploads
│   ├── admin/                       # Admin operations
│   ├── report/                      # Content reporting
│   └── notification/                # User notifications
│
├── shared/                          # Cross-cutting concerns
│   ├── domain/
│   │   ├── event/                      # Event infrastructure
│   │   │   ├── DomainEvent.java
│   │   │   └── EventPublisher.java
│   │   └── model/BaseEntity.java
│   └── infrastructure/
│       ├── adapter/in/web/
│       │   ├── InfoController.java     
│       │   └── IntegrityController.java
│       ├── config/
│       │   ├── SecurityConfig.java
│       │   ├── CorsConfig.java
│       │   ├── AsyncConfig.java
│       │   └── GlobalExceptionHandler.java
│       ├── security/
│       │   ├── JwtService.java
│       │   ├── JwtFilter.java
│       │   └── CustomUserDetailsService.java
│       └── exception/                  # Base exceptions
│
├── utils/                           # Helper utilities
└── BlogApplication.java                # Main application
```

---

## Modules

### User Module
**Purpose**: Authentication, authorization, and user profile management

**Key Features**:
- User registration with validation
- JWT-based authentication
- Profile management (bio, avatar)
- Password encryption (BCrypt)

**Main Components**:
- `AuthService`: Login, token generation
- `UserService`: CRUD operations, profile updates
- `UserController`: `/api/auth/*`, `/api/users/*`

---

### Post Module
**Purpose**: Content creation, engagement, and social interactions

**Key Features**:
- Create, read, update, delete posts
- Rich text content support
- Comments and nested replies
- Like/unlike functionality
- Post statistics tracking

**Main Components**:
- `PostService`: Post lifecycle management
- `CommentService`: Comment operations
- `LikeService`: Engagement tracking
- Event-driven architecture for notifications

**Domain Events**:
- `PostLikedEvent`: Triggered on likes
- `PostCommentedEvent`: Triggered on comments
- `PostFetchedEvent`: View count tracking

---

### Media Module
**Purpose**: File upload, storage, and management

**Key Features**:
- Multi-file upload support
- Image and video validation
- Multiple storage backends (local/cloud)
- File type and size restrictions
- Secure file serving

**Storage Implementations**:
- `LocalFileStorage`: Filesystem storage
- `CloudFileStorage`: Cloud provider integration (S3, etc.)

**Validators**:
- `AvatarMediaValidator`: Profile pictures (max 2MB, JPG/PNG)
- `PostMediaValidator`: Post attachments (max 10MB, images/videos)

---

### Admin Module
**Purpose**: Platform moderation and management

**Key Features**:
- User management (block/unblock, role assignment)
- Content moderation (remove posts/comments)
- Report management
- Platform statistics

**Main Components**:
- `AdminUserService`: User administration
- `AdminPostService`: Content moderation
- `AdminReportService`: Report handling

---

### Report Module
**Purpose**: Content reporting and community safety

**Key Features**:
- Report posts, comments, users
- Report categorization
- Report status tracking
- Admin review workflow

---

### Notification Module
**Purpose**: Real-time user notifications

**Key Features**:
- Event-driven notifications
- Multiple notification types (likes, comments, follows)
- Notification preferences
- Read/unread status

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 14+
- Docker (optional)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/hmaach/01blog.git
   cd 01blog/backend
   ```

TODO...

---

## API Documentation

**[API Documentation →](./README-api.md)**

---

##  Database

**[Database Documentation →](./README-database.md)**
---

## Security

### Authentication Flow

1. User sends credentials to `/api/auth/login`
2. Server validates credentials
3. JWT token generated and returned
4. Client includes token in `Authorization` header
5. `JwtFilter` validates token on each request
6. User principal injected into security context

**[Security Documentation →](./README-security.md)**

---

## 🔧 Configuration

TODO

---

## 📚 Additional Resources

- [Clean Architecture Blog Post](https://medium.com/@souzaluis/applying-clean-architecture-in-java-with-spring-boot-framework-part-iv-a3cb82d5421a)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Flyway Documentation](https://flywaydb.org/documentation/)
