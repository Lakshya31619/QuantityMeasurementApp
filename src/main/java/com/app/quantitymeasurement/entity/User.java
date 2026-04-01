package com.app.quantitymeasurement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    // Null for Google users — they authenticate via Google token, not password
    private String password;

    // "LOCAL" or "GOOGLE"
    @Column(nullable = false)
    private String provider;

    // Populated from Google profile for Google sign-ins
    private String name;
}