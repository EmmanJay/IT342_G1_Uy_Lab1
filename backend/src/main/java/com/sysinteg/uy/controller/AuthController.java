package com.sysinteg.uy.controller;

import com.sysinteg.uy.dto.LoginRequest;
import com.sysinteg.uy.dto.RegisterRequest;
import com.sysinteg.uy.model.User;
import com.sysinteg.uy.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // register(): ResponseEntity
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.registerUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName()
            );
            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "userId", user.getUserId(),
                    "email", user.getEmail(),
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // login(): ResponseEntity
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.loginUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // logout(): ResponseEntity
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logoutUser();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
