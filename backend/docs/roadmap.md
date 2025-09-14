# Let's Play Project Roadmap - Hexagonal Architecture

## Phase 1: Project Setup & Foundation (Days 1-3)

### 1.1 Environment Setup
- **Install Java Development Kit (JDK 17+)**
- **IDE Setup**: IntelliJ IDEA Community or VS Code with Java extensions
- **MongoDB**: Install locally or use MongoDB Atlas (cloud)
- **Postman/Thunder Client**: For API testing

### 1.2 Project Initialization
```bash
# Use Spring Initializer (https://start.spring.io/)
# Dependencies to select:
- Spring Boot DevTools
- Spring Web
- Spring Data MongoDB
- Spring Security
- Validation
- Lombok (reduces boilerplate code)
```

### 1.3 Project Structure (Hexagonal Architecture)
```
src/main/java/com/letsplay/
├── LetsPlayApplication.java
├── domain/                    # Core business logic (no dependencies)
│   ├── model/
│   │   ├── User.java
│   │   ├── Product.java
│   ├── port/
│   │   ├── in/               # Input ports (use cases)
│   │   │   ├── UserService.java
│   │   │   ├── ProductService.java
│   │   │   └── AuthService.java
│   │   └── out/              # Output ports (interfaces)
│   │       ├── UserRepository.java
│   │       ├── ProductRepository.java
│   │       └── PasswordEncoder.java
│   └── service/              # Domain services (business logic)
│       ├── UserServiceImpl.java
│       ├── ProductServiceImpl.java
│       └── AuthServiceImpl.java
├── infrastructure/           # External concerns
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── MongoConfig.java
│   │   └── CorsConfig.java
│   ├── persistence/          # Database adapters
│   │   ├── UserRepositoryImpl.java
│   │   ├── ProductRepositoryImpl.java
│   │   └── entity/
│   │       ├── UserEntity.java
│   │       └── ProductEntity.java
│   └── security/
│       ├── JwtUtil.java
│       ├── JwtFilter.java
│       └── CustomUserDetailsService.java
└── application/              # Application layer
    ├── controller/           # REST controllers
    │   ├── UserController.java
    │   ├── ProductController.java
    │   └── AuthController.java
    ├── dto/                  # Data Transfer Objects
    │   ├── request/
    │   └── response/
    └── exception/            # Global exception handling
        ├── GlobalExceptionHandler.java
        └── CustomExceptions.java
```

## Phase 2: Domain Layer Development (Days 4-6)

### 2.1 Domain Models
**Key Concept**: In hexagonal architecture, domain models are pure Java objects without framework dependencies.

```java
// User.java (Domain Model)
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    // constructors, getters, business methods
}
```

### 2.2 Define Ports (Interfaces)
**Laravel Parallel**: Think of ports as contracts/interfaces, similar to Laravel's Service Contracts.

```java
// Input Port (Use Case Interface)
public interface UserService {
    User createUser(CreateUserCommand command);
    User findById(String id);
    List<User> findAll();
    User updateUser(String id, UpdateUserCommand command);
    void deleteUser(String id);
}

// Output Port (Repository Interface)
public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    void deleteById(String id);
}
```

### 2.3 Domain Services Implementation
**Laravel Parallel**: Similar to Service classes in Laravel, but framework-agnostic.

## Phase 3: Infrastructure Layer (Days 7-10)

### 3.1 MongoDB Configuration
**Laravel Parallel**: Similar to database configuration in Laravel's config/database.php

```java
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }
}
```

### 3.2 Repository Implementation
**Laravel Parallel**: Similar to Eloquent models, but using MongoDB operations.

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final MongoTemplate mongoTemplate;
    
    // Convert between Domain User and UserEntity
    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = mongoTemplate.save(entity);
        return UserMapper.toDomain(saved);
    }
}
```

### 3.3 Security Configuration
**Laravel Parallel**: Similar to Laravel's authentication guards and middleware.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

## Phase 4: Application Layer (Days 11-13)

### 4.1 REST Controllers
**Laravel Parallel**: Similar to Laravel Controllers, but with Spring annotations.

```java
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        // Implementation
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        // Implementation
    }
}
```

### 4.2 DTOs and Validation
**Laravel Parallel**: Similar to Laravel's Form Requests for validation.

```java
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String username;
    
    @Email(message = "Invalid email format")
    @NotBlank
    private String email;
    
    @NotBlank
    @Size(min = 8)
    private String password;
    
    // getters, setters
}
```

### 4.3 Global Exception Handling
**Laravel Parallel**: Similar to Laravel's Exception Handler.

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("USER_NOT_FOUND", ex.getMessage()));
    }
}
```

## Phase 5: Authentication & Authorization (Days 14-16)

### 5.1 JWT Implementation
**Laravel Parallel**: Similar to Laravel Sanctum or Passport tokens.

```java
@Service
public class JwtUtil {
    private final String jwtSecret;
    private final int jwtExpirationMs;
    
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}
```

### 5.2 Custom UserDetailsService
**Laravel Parallel**: Similar to Laravel's User Provider.

## Phase 6: Security Measures (Days 17-18)

### 6.1 Password Hashing
**Laravel Parallel**: Spring Security's BCryptPasswordEncoder is similar to Laravel's Hash facade.

### 6.2 Input Validation & MongoDB Injection Prevention
- Use `@Valid` annotations
- Implement custom validators
- Sanitize inputs using Spring Data MongoDB's built-in protections

### 6.3 Data Sanitization
- Never return passwords in responses
- Use DTOs to control exposed data
- Implement field-level security

## Phase 7: Testing (Days 19-20)

### 7.1 Unit Tests
```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void shouldCreateUserSuccessfully() {
        // Test implementation
    }
}
```

### 7.2 Integration Tests
```java
@SpringBootTest
@TestContainers
class UserControllerIntegrationTest {
    @Container
    static MongoDBContainer mongoContainer = new MongoDBContainer("mongo:5.0");
    
    @Test
    void shouldCreateUserViaAPI() {
        // Test implementation
    }
}
```

## Phase 8: Bonus Features (Days 21-22)

### 8.1 CORS Configuration
```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Implementation
    }
}
```

### 8.2 Rate Limiting
- Use Spring Cloud Gateway or
- Implement custom rate limiting with Redis
- Use Bucket4j library

## Key Learning Points for Laravel Developers

### Similarities with Laravel:
- **Dependency Injection**: Spring's @Autowired is like Laravel's constructor injection
- **Middleware**: Spring Security filters work like Laravel middleware
- **Service Layer**: Both frameworks encourage service-oriented architecture
- **Validation**: Spring's validation is similar to Laravel's Form Requests
- **Configuration**: application.properties is like Laravel's .env file

### Key Differences:
- **Compilation**: Java is compiled (vs PHP interpreted)
- **Type Safety**: Java is strongly typed
- **Annotations**: Spring uses annotations extensively (vs Laravel's facades/helpers)
- **Database**: MongoDB document structure vs relational databases

## Daily Checklist Template

### Day X Tasks:
- [ ] **Morning**: Review previous day's code
- [ ] **Core Development**: Focus on current phase tasks
- [ ] **Testing**: Write tests for implemented features
- [ ] **Documentation**: Update README and API docs
- [ ] **Evening**: Plan next day's tasks

## Resources for Learning

### Essential Documentation:
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)

### Recommended Tutorials:
- Spring Boot official guides
- Baeldung Spring tutorials
- YouTube: "Spring Boot Tutorial for Beginners"

### Tools:
- **Postman**: API testing (similar to Laravel's API testing)
- **MongoDB Compass**: Database GUI
- **Spring Boot DevTools**: Hot reload (similar to Laravel's file watching)

## Success Metrics

By the end of this roadmap, you should have:
- ✅ A fully functional CRUD API
- ✅ JWT-based authentication
- ✅ Role-based authorization  
- ✅ Proper error handling (no 5XX errors)
- ✅ Security best practices implemented
- ✅ Comprehensive test coverage
- ✅ Clean hexagonal architecture
- ✅ Production-ready code
