# 01Blog

## Overview

In this project, you will build a social blogging platform called **01Blog**, where students can share their learning experiences, discoveries, and progress throughout their journey. Users can interact with each other’s content, follow one another, and engage in meaningful discussions.

You will develop this platform as a fullstack application, using **Java Spring Boot** for the backend and **Angular** for the frontend. The project covers essential features such as REST API development, user authentication, media handling, and more.

### Key Features:

* User registration and secure login
* Role-based access control (User vs Admin)
* Media uploads (Images/Video)
* Real-time post interactions (Likes/Comments)
* Admin panel for content moderation

## Clean Architecture

**Clean Architecture** is a software design philosophy that emphasizes separation of concerns, testability, and scalability. It structures your application into layers, each with a specific responsibility, making it easier to maintain and extend.

* **Entities**: Core business logic, independent of any external dependencies.
* **Use Cases**: Application-specific business rules.
* **Interface Adapters**: Converts data from external sources into a format that can be understood by the use cases and entities.
* **Frameworks & Drivers**: External agents such as databases, web frameworks, or UI components.


[Read more about Clean Architecture](https://medium.com/@souzaluis/applying-clean-architecture-in-java-with-spring-boot-framework-part-iv-a3cb82d5421a)



![Clean Architecture Diagram](./docs/assets/clean_architecture.png)

---

## Backend Documentation

For more detailed backend implementation and setup instructions, please check the [Backend Documentation](./docs/README-backend.md).

---

### Technologies Used:

* **Java**: Spring Boot, Spring Security
* **SQL**: PostgreSQL
* **Frontend**: Angular
* **Docker**

