# Notepad Backend API

A **Spring Boot REST API** for a secure Notepad application.
The system allows users to **create, read, update, and delete notes** with **JWT-based authentication and authorization**.

Each note belongs to a specific user, and only the owner of a note can modify or delete it.

---

# Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **JWT (JSON Web Tokens)**
* **Spring Data JPA / Hibernate**
* **PostgreSQL**
* **Maven**

---

# Features

* User authentication using **JWT**
* Secure API endpoints
* Create notes
* View notes
* Update notes
* Soft delete notes
* Ownership validation (users can only access their own notes)
* Token auto-refresh if expiration is near

---

# Project Architecture

The project follows a **layered architecture**.

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Project structure:

```
notepad-backend
│
├── controller
│   └── NotesController
│
├── service
│   └── NotesService
│
├── repository
│   ├── AuthUserRepository
│   ├── NotesRepository
│   └── NotesUserMappingRepository
│
├── entity
│   ├── AuthUser
│   ├── Notes
│   └── NotesUserMapping
│
├── security
│   ├── JwtFilter
│   └── JwtUtil
│
├── dto
│   ├── LoginRequest
│   ├── LoginResponse
│   ├── NotesRequest
│   └── NoteResponse
```

---

# Database Design

### Auth User Table

```
tbl_auth_user
```

| Column            | Description        |
| ----------------- | ------------------ |
| id                | User ID            |
| username          | Login username     |
| password          | Encrypted password |
| email             | User email         |
| created_date_time | Creation timestamp |

---

### Notes Table

```
tbl_notes
```

| Column            | Description      |
| ----------------- | ---------------- |
| id                | Note ID          |
| title             | Note title       |
| description       | Note content     |
| is_active         | Active flag      |
| is_deleted        | Soft delete flag |
| created_date_time | Creation time    |
| updated_date_time | Last update time |

---

### Notes User Mapping

```
tbl_notes_user_mapping
```

This table maps **users to their notes**.

| Column   | Description    |
| -------- | -------------- |
| user_id  | User reference |
| notes_id | Note reference |

Relationship:

```
User
  ↓
NotesUserMapping
  ↓
Notes
```

---

# Authentication Flow

1. User logs in with **username and password**
2. Server validates credentials
3. JWT token is generated
4. Token must be sent with each request

Example header:

```
Authorization: Bearer <JWT_TOKEN>
```

The **JwtFilter** validates the token before accessing secured APIs.

---

# API Endpoints

### Login

```
POST /api/auth/login
```

Request:

```json
{
  "username": "user123",
  "password": "password"
}
```

Response:

```json
{
  "token": "JWT_TOKEN",
  "notesList": [
    {
      "id": 1,
      "title": "Sample Note"
    }
  ]
}
```

---

### Create Note

```
POST /api/notes/create_note
```

Headers:

```
Authorization: Bearer <JWT_TOKEN>
```

Request:

```json
{
  "noteTitle": "My Note",
  "noteContent": "This is a test note"
}
```

---

### Get Note

```
GET /api/notes/{id}
```

---

### Update Note

```
PUT /api/notes/{id}
```

---

### Delete Note

```
DELETE /api/notes/{id}
```

Notes are **soft deleted** using `is_deleted = true`.

---

# Running the Project

Clone the repository:

```
git clone https://github.com/yourusername/notepad-backend.git
```

Navigate into the project:

```
cd notepad-backend
```

Run the application:

```
mvn spring-boot:run
```

Server will start at:

```
http://localhost:8080
```

---

# Future Improvements

* Global exception handling
* Pagination for notes
* Search notes
* Note tagging
* User roles and permissions
* Docker deployment

---

# Author

**Rugved Patil**

Backend project built for learning **Spring Boot, Security, and REST API design**.
