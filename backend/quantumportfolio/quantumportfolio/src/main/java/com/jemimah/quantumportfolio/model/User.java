package com.jemimah.quantumportfolio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;   // maps to username in frontend

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password; // ADD THIS

    @Column
    private String role;

    @Column
private boolean priceAlerts = true;

@Column
private boolean newsletter = false;

}
