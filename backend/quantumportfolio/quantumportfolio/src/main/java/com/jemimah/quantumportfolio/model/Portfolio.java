package com.jemimah.quantumportfolio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // Portfolio name or stock name
    private double amount;        // Investment amount
    private double currentValue;  // Current market value
}
