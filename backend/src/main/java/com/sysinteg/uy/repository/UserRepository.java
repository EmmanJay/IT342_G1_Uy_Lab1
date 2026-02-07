package com.sysinteg.uy.repository;

import com.sysinteg.uy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // findByEmail(email: String): User?
    Optional<User> findByEmail(String email);

    // findById(id: Int): User? — inherited from JpaRepository

    // save(user: User): User — inherited from JpaRepository

    boolean existsByEmail(String email);
}
