package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.util.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${google.client-id}")
    private String googleClientId;

    // ── Signup ────────────────────────────────────────────────────────────────

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {

        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || email.trim().isEmpty()) {
            return error(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (password == null || password.trim().isEmpty()) {
            return error(HttpStatus.BAD_REQUEST, "Password is required");
        }

        email = email.trim().toLowerCase();

        if (userRepo.findByEmail(email).isPresent()) {
            return error(HttpStatus.CONFLICT, "An account with this email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password.trim()));
        user.setProvider("LOCAL");
        userRepo.save(user);

        return ResponseEntity.ok(Map.of("message", "Account created successfully"));
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || password == null) {
            return error(HttpStatus.BAD_REQUEST, "Email and password are required");
        }

        final String normalizedEmail = email.trim().toLowerCase();

        return userRepo.findByEmail(normalizedEmail)
                .map(existing -> {

                    if ("GOOGLE".equals(existing.getProvider())) {
                        return error(HttpStatus.BAD_REQUEST,
                                "This account uses Google login. Please sign in with Google.");
                    }

                    if (!passwordEncoder.matches(password, existing.getPassword())) {
                        return error(HttpStatus.UNAUTHORIZED, "Invalid email or password");
                    }

                    String token = jwtUtil.generateToken(existing.getEmail());
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElse(error(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
    }

    // ── Google Login ──────────────────────────────────────────────────────────

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {

        String credential = body.get("token");

        if (credential == null || credential.isBlank()) {
            return error(HttpStatus.BAD_REQUEST, "Google credential is required");
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(credential);

            if (idToken == null) {
                return error(HttpStatus.UNAUTHORIZED, "Invalid Google token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail().trim().toLowerCase();
            String name  = (String) payload.get("name");

            User user = userRepo.findByEmail(email).orElse(null);

            if (user != null && "LOCAL".equals(user.getProvider())) {
                return error(HttpStatus.CONFLICT,
                        "An account with this email already exists. Please log in with your password.");
            }

            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setProvider("GOOGLE");
                user.setName(name);
                userRepo.save(user);
            }

            String jwt = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(Map.of("token", jwt));

        } catch (Exception e) {
            return error(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Google authentication failed: " + e.getMessage());
        }
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("message", message));
    }
}