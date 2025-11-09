# User Module

## Overview

The **User Module** handles all user-related operations, including authentication, registration, profile management, and subscriptions.  
It integrates tightly with Spring Security and JWT to ensure secure user authentication and authorization.

---

## Responsibilities

- User registration and login
- Profile management (avatar, bio, username)
- Subscription system (follow/unfollow)
- Authentication via JWT
- User lifecycle events (created, deleted, subscribed, etc.)

---

## Architecture

```

user/
├── domain/          # Business logic & models
├── application/     # Service implementations & event handlers
├── infrastructure/  # Web controllers, persistence adapters, config

```

### Main Components

| Layer | Component | Description |
|-------|------------|-------------|
| Domain | `User`, `Subscription` | Core domain models |
| Domain | `UserService`, `AuthService` | Use case interfaces |
| Application | `UserServiceImpl`, `AuthServiceImpl` | Business logic implementation |
| Infrastructure | `UserController`, `AuthController` | REST endpoints |
| Infrastructure | `UserEntity`, `SubscriptionEntity` | JPA entities |
| Infrastructure | `UserRepositoryImpl` | Outbound persistence adapter |

---

## API Endpoints

| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Authenticate user |
| `GET` | `/api/users/me` | Get current user profile |
| `PATCH` | `/api/users/profile` | Update user profile |
| `POST` | `/api/users/{id}/subscribe` | Subscribe to another user |

---

## Domain Events

- `UserCreatedPostEvent`
- `UserDeletedPostEvent`
- `UserFetchedEvent`
- `UserWasSubscribedEvent`
- `UserWasUnsubscribedEvent`

---

## Exceptions

- `UserNotFoundException`
- `UsernameAlreadyExistsException`
- `EmailAlreadyExistsException`

---

## Configuration

`UserModuleConfig.java` registers the user-related beans and ensures dependency injection across layers.