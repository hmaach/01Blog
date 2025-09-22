```text
src/main/java/com/blogplatform/
├── BlogPlatformApplication.java
├── modules/
│   ├── user/
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   └── Subscription.java
│   │   │   ├── port/
│   │   │   │   ├── in/
│   │   │   │   │   ├── UserManagementUseCase.java
│   │   │   │   │   └── SubscriptionUseCase.java
│   │   │   │   └── out/
│   │   │   │       ├── UserRepository.java
│   │   │   │       └── SubscriptionRepository.java
│   │   │   └── service/
│   │   │       ├── UserService.java
│   │   │       └── SubscriptionService.java
│   │   ├── infrastructure/
│   │   │   ├── adapter/
│   │   │   │   ├── in/
│   │   │   │   │   └── web/
│   │   │   │   │       └── UserController.java
│   │   │   │   └── out/
│   │   │   │       └── persistence/
│   │   │   │           ├── UserJpaRepository.java
│   │   │   │           └── UserRepositoryImpl.java
│   │   │   └── config/
│   │   │       └── UserModuleConfig.java
│   │   └── dto/
│   │       ├── UserDto.java
│   │       └── SubscriptionDto.java
│   ├── post/
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Post.java
│   │   │   │   ├── Comment.java
│   │   │   │   └── Like.java
│   │   │   ├── port/
│   │   │   └── service/
│   │   ├── infrastructure/
│   │   └── dto/
│   ├── notification/
│   │   ├── domain/
│   │   ├── infrastructure/
│   │   └── dto/
│   ├── report/
│   │   ├── domain/
│   │   ├── infrastructure/
│   │   └── dto/
│   └── admin/
│       ├── domain/
│       ├── infrastructure/
│       └── dto/
├── shared/
│   ├── domain/
│   │   ├── event/
│   │   │   ├── DomainEvent.java
│   │   │   └── EventPublisher.java
│   │   └── model/
│   │       └── BaseEntity.java
│   ├── infrastructure/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── DatabaseConfig.java
│   │   │   └── FileStorageConfig.java
│   │   ├── security/
│   │   │   ├── JwtAuthenticationProvider.java
│   │   │   └── RoleBasedAccessControl.java
│   │   ├── exception/
│   │   │   └── GlobalExceptionHandler.java
│   │   └── event/
│   │       └── EventPublisherImpl.java
│   └── dto/
│       ├── ApiResponse.java
│       └── PagedResponse.java
└── utils/
    ├── MediaProcessor.java
    └── ValidationUtils.java```








    ```text
modules/user/
├── domain/
│   ├── entity/
│   │   ├── User.java                    # Pure domain entity
│   │   └── UserPrincipal.java          # Domain model for authentication
│   ├── service/
│   │   └── UserDomainService.java      # Pure domain business rules
│   ├── repository/
│   │   └── UserRepository.java         # Repository interface
│   ├── exception/
│   │   ├── EmailAlreadyExistsException.java
│   │   ├── UserNotFoundException.java
│   │   └── InvalidCredentialsException.java
│   └── event/
│       ├── UserRegisteredEvent.java
│       └── UserLoggedInEvent.java
├── application/
│   ├── usecase/
│   │   ├── RegisterUserUseCase.java    # Application use cases
│   │   ├── LoginUserUseCase.java
│   │   ├── UpdateUserUseCase.java
│   │   └── GetUserUseCase.java
│   ├── service/
│   │   └── UserApplicationService.java # Orchestrates use cases
│   ├── port/
│   │   ├── in/
│   │   │   ├── AuthUseCase.java       # Input port interfaces
│   │   │   └── UserManagementUseCase.java
│   │   └── out/
│   │       ├── UserRepositoryPort.java # Output port interfaces
│   │       └── PasswordEncoderPort.java
│   ├── dto/
│   │   ├── command/
│   │   │   ├── RegisterUserCommand.java
│   │   │   ├── LoginUserCommand.java
│   │   │   └── UpdateUserCommand.java
│   └── mapper/
│       └── UserApplicationMapper.java
├── presentation/
│   ├── web/
│   │   ├── AuthController.java
│   │   └── UserController.java
│   ├── dto/
│   │   ├── request/
│   │   │   ├── RegisterUserRequest.java
│   │   │   ├── LoginRequest.java
│   │   │   └── UpdateUserRequest.java
│   │   └── response/
│   │       ├── UserResponse.java
│   │       ├── AuthResponse.java
│   │       └── ApiResponse.java
│   ├── mapper/
│   │   └── UserWebMapper.java
│   └── exception/
│       └── UserControllerAdvice.java
└── infrastructure/
    ├── persistence/
    │   ├── entity/
    │   │   └── UserJpaEntity.java
    │   ├── repository/
    │   │   └── UserJpaRepository.java
    │   ├── mapper/
    │   │   └── UserPersistenceMapper.java
    │   └── adapter/
    │       └── UserRepositoryAdapter.java
    ├── security/
    │   ├── JwtTokenProvider.java
    │   ├── PasswordEncoderAdapter.java
    │   └── UserDetailsServiceAdapter.java
    └── config/
        └── UserModuleConfig.java
```