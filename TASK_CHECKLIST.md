# TASK CHECKLIST

## DONE
- [x] Create repository structure (/web, /backend, /docs, /mobile)  
  Commit: da9c834 
- [x] Initialize backend Spring Boot project with .gitignore  
  Commit: a637434
- [x] Database connection (MySQL)  
  Configured XAMPP MySQL (root@localhost:3306/it342_uy_db), JPA/Hibernate auto DDL
- [x] Password encryption (BCrypt)  
  BCryptPasswordEncoder via Spring Security
- [x] POST /api/auth/register  
  AuthController.register() → AuthService.registerUser()
- [x] POST /api/auth/login  
  AuthController.login() → AuthService.loginUser() returns JWT
- [x] POST /api/auth/logout  
  AuthController.logout() → AuthService.logoutUser()
- [x] GET /api/user/me (protected)  
  UserController.getCurrentUser() — JWT-protected endpoint

## IN-PROGRESS

## TODO

### Web Application – ReactJS
- [ ] Initialize React project
- [ ] Register page
- [ ] Login page
- [ ] Dashboard/Profile page (protected)
- [ ] Logout functionality

### Documentation
- [ ] Update FRS PDF (ERD, UML, Web UI screenshots)
