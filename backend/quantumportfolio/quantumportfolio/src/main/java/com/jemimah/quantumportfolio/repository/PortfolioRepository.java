package com.jemimah.quantumportfolio.repository;

import com.jemimah.quantumportfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    // Provides findAll(), findById(), save(), delete(), etc.
}
