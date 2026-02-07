package com.sysinteg.uy.security;

import com.sysinteg.uy.model.User;
import com.sysinteg.uy.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {

    private final SecretKey secretKey;
    private final long expiration;
    private final UserRepository userRepository;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.expiration}") long expiration,
                         UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
        this.userRepository = userRepository;
    }

    // createToken(user: User): String
    public String createToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    // verifyToken(token: String): Boolean
    public boolean verifyToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // getUserFromToken(token: String): User
    public User getUserFromToken(String token) {
        String email = extractClaims(token).getSubject();
        return userRepository.findByEmail(email).orElse(null);
    }

    public String getEmailFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
