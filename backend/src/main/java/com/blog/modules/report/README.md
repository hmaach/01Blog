# Report Module

## Overview

The **Report Module** allows users to report inappropriate content (posts, comments, or users).  
It integrates with the Admin Module to facilitate moderation and community safety.

---

## Responsibilities

- Create and track user reports
- Categorize report types
- Manage report statuses (pending, reviewed, resolved)
- Notify administrators of new reports

---

## Architecture

```

report/
├── domain/          # Report model, ports
├── application/     # Service logic
└── infrastructure/  # Web controllers & persistence

```

---

## Main Components

| Layer | Component | Description |
|--------|------------|-------------|
| Domain | `Report` | Core model |
| Domain | `ReportService` | Interface for report operations |
| Application | `ReportServiceImpl` | Use case implementation |
| Infrastructure | `ReportController` | REST API |
| Infrastructure | `ReportRepositoryImpl` | Persistence adapter |

---

## API Endpoints

| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/api/reports` | Create a new report |
| `GET` | `/api/reports` | List all reports (admin only) |
| `PATCH` | `/api/reports/{id}` | Update report status |