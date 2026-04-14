package com.app.authservice.controller;

import com.app.authservice.entity.User;
import com.app.authservice.repository.UserRepository;
import com.app.authservice.util.JwtUtil;
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
    @Value("${google.client-id}") private String googleClientId;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String email = body.get("email"); String password = body.get("password");
        if (email == null || email.trim().isEmpty()) return error(HttpStatus.BAD_REQUEST, "Email is required");
        if (password == null || password.trim().isEmpty()) return error(HttpStatus.BAD_REQUEST, "Password is required");
        email = email.trim().toLowerCase();
        if (userRepo.findByEmail(email).isPresent()) return error(HttpStatus.CONFLICT, "Account already exists");
        User user = new User();
        user.setEmail(email); user.setPassword(passwordEncoder.encode(password.trim())); user.setProvider("LOCAL");
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "Account created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email"); String password = body.get("password");
        if (email == null || password == null) return error(HttpStatus.BAD_REQUEST, "Email and password are required");
        final String norm = email.trim().toLowerCase();
        return userRepo.findByEmail(norm).map(u -> {
            if ("GOOGLE".equals(u.getProvider())) return error(HttpStatus.BAD_REQUEST, "Use Google login");
            if (!passwordEncoder.matches(password, u.getPassword())) return error(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(u.getEmail())));
        }).orElse(error(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String credential = body.get("token");
        if (credential == null || credential.isBlank()) return error(HttpStatus.BAD_REQUEST, "Google credential is required");
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId)).build();
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) return error(HttpStatus.UNAUTHORIZED, "Invalid Google token");
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail().trim().toLowerCase();
            User user = userRepo.findByEmail(email).orElse(null);
            if (user != null && "LOCAL".equals(user.getProvider())) return error(HttpStatus.CONFLICT, "Use password login");
            if (user == null) {
                user = new User(); user.setEmail(email); user.setProvider("GOOGLE");
                user.setName((String) payload.get("name")); userRepo.save(user);
            }
            return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user.getEmail())));
        } catch (Exception e) { return error(HttpStatus.INTERNAL_SERVER_ERROR, "Google auth failed: " + e.getMessage()); }
    }

    private ResponseEntity<Map<String, String>> error(HttpStatus s, String msg) {
        return ResponseEntity.status(s).body(Map.of("message", msg));
    }
}
