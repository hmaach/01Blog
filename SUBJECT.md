## 01Blog

### Overview

In this project, you will build a **social blogging platform** called **01Blog**, where students can share their learning experiences, discoveries, and progress throughout their journey. Users can interact with each other’s content, follow one another, and engage in meaningful discussions.

This project will guide you through creating a fullstack application using **Java Spring Boot** for the backend and **Angular** for the frontend — from setting up REST APIs to building an interactive and responsive interface.

### Role Play

You are a **fullstack developer** working for a platform dedicated to helping students document their learning journey. Your mission is to create a user-friendly and secure blogging system where students can post content, subscribe to others, and report inappropriate behavior. Administrators must have tools to moderate the content and manage users.

### Learning Objectives

- Master **Java Spring Boot** (REST API, authentication, services, security)
- Build **Angular** applications (components, routing, services, UI/UX)
- Understand **fullstack architecture** and RESTful API integration
- Handle **user-generated content** (media upload, content management)
- Design and use **relational databases** for social interactions (likes, comments, subscriptions)
- Implement **secure authentication** and **role-based access**
- Create tools for **moderation and administration**
- Collaborate using **Git**, GitHub, and agile practices (issues, branches, commits)

### Instructions

#### Backend

- **Authentication**

  - User registration, login, and secure password handling
  - Role-based access control (user vs admin)

- **User Block Page**

  - Each user has a public profile (their "block") listing all their posts
  - Users can subscribe to other profiles
  - Subscribed users receive notifications when new posts are published

- **Posts**

  - Users can create/edit/delete posts with media (image or video) and text
  - Each post includes a timestamp, description, and media preview
  - Other users can like and comment on posts

- **Reports**

  - Users can report profiles for inappropriate or offensive content
  - Reports must include a reason and timestamp
  - Reports are stored and visible only to admins

- **Admin Panel**
  - Admin can view and manage all users
  - Admin can manage posts and remove inappropriate content
  - Admin can handle user reports (ban/delete user or post)
  - All admin routes must be protected by access control

#### Frontend

- **User Experience**

  - Homepage with a feed of posts from subscribed users
  - Personal block page with full post management (CRUD)
  - View other users’ blocks and subscribe/unsubscribe

- **Post Interaction**

  - Like and comment on posts (comments update in real time or via refresh)
  - Upload media (images/videos) with previews
  - Display timestamps, likes, and comments on each post

- **Notifications**

  - Notification icon showing updates from subscribed profiles
  - Mark notifications as read/unread

- **Reporting**

  - Report a user with a text reason (UI component/modal)
  - Confirmation before submitting the report

- **Admin Dashboard**

  - View all users, posts, and submitted reports
  - Delete or ban users, remove or hide posts
  - Clean UI for moderation tasks

- Use a responsive UI framework: **Angular Material** or **Bootstrap**

### Constraints

- Use **Spring Security** or **JWT** for authentication and role management
- Store media securely (in file system or using cloud storage like AWS S3)
- Use a relational SQL database (e.g., PostgreSQL or MySQL)
- All routes must be protected according to user roles
- Code generation tools (like JHipster) are **not allowed**
- The project must include a detailed **README** with:
  - How to run the backend and frontend
  - Technologies used

### Evaluation

This project is evaluated through **peer-to-peer code review** and **functional demo**. Evaluation criteria include:

- 💡 **Functionality**: All features implemented and working as expected
- 🔐 **Security**: Proper role-based access and secure user data handling
- 🎨 **UI/UX**: Responsive, intuitive, and clean interface

### Bonus Features (Optional but Recommended)

- Real-time updates using WebSockets (for comments or notifications)
- infinite scroll on feeds
- Dark mode toggle
- Basic analytics for admins (number of posts, most reported users)
- Markdown support for posts

### Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Angular Official Documentation](https://angular.io/docs)
- [Angular Material Documentation](https://material.angular.io/components/categories)
- [Spring Boot File Upload](https://www.bezkoder.com/spring-boot-upload-file-database/)
- [JWT Introduction – Auth0](https://auth0.com/learn/json-web-tokens/)
- [Spring Security Basics – Java Guides](https://www.javaguides.net/2022/01/spring-security-tutorial.html)
- [PostgreSQL Basics](https://www.postgresqltutorial.com/)
- [JPA (Java Persistence API) Guide](https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa)