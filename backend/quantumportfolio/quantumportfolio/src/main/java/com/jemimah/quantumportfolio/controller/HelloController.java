package com.jemimah.quantumportfolio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // For root URL (http://localhost:8080)
    @GetMapping("/")
    public String home() {
        return "<h1>Welcome to Quantum AI Portfolio Backend 🌟</h1>"
             + "<p>Go to <a href='/api/hello'>/api/hello</a> to test the API.</p>";
    }

    // For /api/hello
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello Quantum!";
    }
}


