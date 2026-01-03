package com.jemimah.quantumportfolio.controller;

import com.jemimah.quantumportfolio.model.User;
import com.jemimah.quantumportfolio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") // React frontend
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // ==========================
    // Get all users
    // ==========================
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // ==========================
    // Get user by ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // ==========================
    // Register new user
    // ==========================
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    // ==========================
    // Simple login (demo)
    // ==========================
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        User existing = userService.getAllUsers()
                                   .stream()
                                   .filter(u -> u.getEmail().equals(user.getEmail()))
                                   .findFirst()
                                   .orElse(null);
        if(existing == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(existing);
    }

    // ==========================
    // Update Profile
    // ==========================
    @PostMapping("/updateProfile")
    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser) {
        User user = userService.getUserById(updatedUser.getId());
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        userService.createUser(user); // save changes
        return ResponseEntity.ok(user);
    }

    // ==========================
    // Update Notifications
    // ==========================
    @PostMapping("/updateNotifications")
    public ResponseEntity<User> updateNotifications(@RequestBody Map<String, Boolean> notifications) {
        User user = userService.getUserById(1L); // demo: single user
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        user.setPriceAlerts(notifications.getOrDefault("priceAlerts", true));
        user.setNewsletter(notifications.getOrDefault("newsletter", false));
        userService.createUser(user); // save changes
        return ResponseEntity.ok(user);
    }

}

