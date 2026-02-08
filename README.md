# ResearchCenter – IT342_G1_Uy_Lab1

## Project Description
ResearchCenter is a user registration and authentication system built as a full-stack application. It features a **Spring Boot** backend with JWT-based authentication and a **React** web frontend styled with **Tailwind CSS**. Users can register, log in, view their profile dashboard, and log out securely.

## Technologies Used
| Layer    | Technology                             |
| -------- | -------------------------------------- |
| Backend  | Java 17, Spring Boot 3, Spring Security, JPA/Hibernate |
| Database | MySQL (via XAMPP / phpMyAdmin)          |
| Auth     | JWT (JSON Web Tokens), BCrypt password hashing |
| Frontend | React 18, Vite 6, Tailwind CSS 3       |
| Mobile   | Android Kotlin *(upcoming)*            |

## Steps to Run Backend

1. **Prerequisites** – Java 17+, Maven, MySQL running on port 3306 (e.g., via XAMPP).
2. Create a MySQL database named `it342_uy_db`.
3. Open a terminal and navigate to the `/backend` directory:
   ```bash
   cd backend
   ```
4. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```
5. The backend server starts on **http://localhost:8080**.

## Steps to Run Web App

1. **Prerequisites** – Node.js 18+ and npm.
2. Navigate to the `/web` directory:
   ```bash
   cd web
   ```
3. Install dependencies:
   ```bash
   npm install
   ```
4. Start the dev server:
   ```bash
   npm run dev
   ```
5. Open **http://localhost:5173** in your browser.

> The Vite dev server proxies `/api` requests to the backend at `localhost:8080`.

## Steps to Run Mobile App
*(Mobile app will be implemented in a future lab session.)*

## API Endpoints

| Method | Endpoint            | Description                       | Auth Required |
| ------ | ------------------- | --------------------------------- | ------------- |
| POST   | `/api/auth/register` | Register a new user               | No            |
| POST   | `/api/auth/login`    | Login and receive JWT token       | No            |
| POST   | `/api/auth/logout`   | Logout (client-side token removal)| Yes (Bearer)  |
| GET    | `/api/user/me`       | Get current authenticated user    | Yes (Bearer)  |

### Register – Request Body
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### Login – Request Body
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

### Login – Response
```json
{
  "message": "Login successful",
  "token": "<JWT_TOKEN>"
}
```

### Get Profile – Response
```json
{
  "userId": 1,
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2026-02-08T12:00:00"
}
```
