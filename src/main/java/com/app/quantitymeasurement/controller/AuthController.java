package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    // ✅ SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "User already exists"));
        }

        user.setProvider("LOCAL");
        userRepo.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered"));
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        return userRepo.findByEmail(user.getEmail())
                .map(existing -> {

                    if (!existing.getPassword().equals(user.getPassword())) {
                        return ResponseEntity.badRequest()
                                .body(Map.of("message", "Invalid password"));
                    }

                    String token = jwtUtil.generateToken(existing.getEmail());

                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "User not found")));
    }

    // ✅ GOOGLE LOGIN (basic)
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {

        String email = "google_user@gmail.com"; // simplified

        User user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setProvider("GOOGLE");
                    return userRepo.save(newUser);
                });

        String jwt = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of("token", jwt));
    }
}