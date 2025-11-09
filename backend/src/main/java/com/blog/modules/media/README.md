# Media Module

## Overview

The **Media Module** is responsible for managing file uploads, validation, and storage for both user avatars and post attachments.  
It supports both local and cloud-based storage providers.

---

## Responsibilities

- Upload and delete images or videos
- Validate media size and type
- Handle storage abstraction (Local / Cloud)
- Secure file serving

---

## Architecture

```

media/
├── domain/
│   ├── model/      # Media, MediaType
│   └── port/       # FileStorage, MediaRepository
├── application/     # Service & validation logic
└── infrastructure/  # Controller, persistence, storage, config

```

---

## Main Components

| Layer          | Component                                    | Description                    |
| -------------- | -------------------------------------------- | ------------------------------ |
| Domain         | `Media`, `MediaType`                         | Domain models                  |
| Domain         | `FileStorage`, `MediaRepository`             | Ports                          |
| Application    | `MediaServiceImpl`                           | Core logic for upload/download |
| Application    | `AvatarMediaValidator`, `PostMediaValidator` | Validation logic               |
| Infrastructure | `MediaController`                            | REST API for uploads           |
| Infrastructure | `LocalFileStorage`, `CloudFileStorage`       | Storage providers              |
| Infrastructure | `MediaMapper`, `MediaEntity`                 | Persistence mapping            |

---

## API Endpoints

| Method   | Endpoint            | Description          |
| -------- | ------------------- | -------------------- |
| `POST`   | `/api/media/upload` | Upload media file    |
| `DELETE` | `/api/media/{id}`   | Delete media file    |
| `GET`    | `/api/media/{id}`   | Fetch media metadata |

---

## Exceptions

- `EmptyMediaFileException`
- `InvalidMediaTypeException`
- `TooLargeMediaFileException`
- `MediaStorageException`
- `MediaNotFoundException`
