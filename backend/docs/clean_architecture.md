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