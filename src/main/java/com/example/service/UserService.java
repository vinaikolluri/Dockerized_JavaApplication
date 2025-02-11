package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Authenticate user
    public boolean authenticateUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // Compare the provided password with the stored hashed password
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false; // User not found or password does not match
    }

    // Check if username is already taken
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }

    // Check if email is already taken
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // Save new user
    public void saveUser(User user) {
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
