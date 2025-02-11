package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password; // This should be encrypted before storing

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please enter a valid email address")
    private String email;

    @Transient
    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    // Method to check if password and confirmPassword match
    public boolean isPasswordMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }

    // Encrypt password before saving (for demonstration, you will handle this in the service layer)
    public void encryptPassword() {
        // Password encryption logic can be added here
        // For example, you can use BCrypt or any other hashing algorithm
    }
}
