package com.jemimah.quantumportfolio.controller;

import com.jemimah.quantumportfolio.model.Portfolio;
import com.jemimah.quantumportfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "http://localhost:5173")  // allow React frontend
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    // ✅ Get all portfolios from DB
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    // ✅ Add new portfolio (for testing)
    @PostMapping
    public Portfolio addPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }
}


