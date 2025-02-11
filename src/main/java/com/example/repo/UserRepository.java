package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Define a method to find a user by email
    User findByEmail(String email);
    
    // You can also keep findByUsername if needed
    User findByUsername(String username);
}
