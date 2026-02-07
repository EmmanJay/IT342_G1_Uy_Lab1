package com.sysinteg.uy.service;

import com.sysinteg.uy.model.User;
import com.sysinteg.uy.repository.UserRepository;
import com.sysinteg.uy.security.TokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // registerUser(): User
    public User registerUser(String email, String password, String firstName, String lastName) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return userRepository.save(user);
    }

    // loginUser(): String (returns JWT token)
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return tokenProvider.createToken(user);
    }

    // logoutUser(): void
    public void logoutUser() {
        // With JWT, logout is handled client-side by removing the token.
        // Server-side invalidation can be added with a token blacklist if needed.
    }

    // validateUser(): Boolean
    public boolean validateUser(String token) {
        return tokenProvider.verifyToken(token);
    }
}
