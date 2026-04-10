package com.rutuja.finance_dashboard_system.controller;

import com.rutuja.finance_dashboard_system.config.JwtUtil;
import com.rutuja.finance_dashboard_system.dto.AuthRequest;
import com.rutuja.finance_dashboard_system.model.User;
import com.rutuja.finance_dashboard_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        // ✅ set default role (security)
        user.setRole("USER");

        user.setPassword(encoder.encode(user.getPassword())); // 🔐 encrypt password

        repo.save(user);

        return "User registered successfully";
    }
}