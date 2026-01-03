package com.jemimah.quantumportfolio.controller;

import com.jemimah.quantumportfolio.model.User;
import com.jemimah.quantumportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ===== Register User =====
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already registered!");
        }

        // Save new user
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // ===== Login User =====
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        // Fetch user by email safely using Optional
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with email: " + loginRequest.getEmail()
                ));

        // Simple password check (for demo purposes; hash passwords in production!)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Invalid password!");
        }

        return ResponseEntity.ok("Login successful!");
    }

    // ===== Optional: Test endpoint =====
    @GetMapping("/hello")
    public String hello() {
        return "Welcome to Quantum AI Portfolio Backend 🌟";
    }
}
