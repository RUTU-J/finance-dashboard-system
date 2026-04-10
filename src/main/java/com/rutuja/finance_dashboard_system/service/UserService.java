package com.rutuja.finance_dashboard_system.service;

import com.rutuja.finance_dashboard_system.dto.UserResponse;
import com.rutuja.finance_dashboard_system.exception.ResourceNotFoundException;
import com.rutuja.finance_dashboard_system.model.User;
import com.rutuja.finance_dashboard_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ CREATE
    public UserResponse createUser(User user) {

        String email = user.getEmail().trim().toLowerCase();

        // Prevent duplicate email
        if (repo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setEmail(email);
        user.setRole(formatRole(user.getRole()));

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = repo.save(user);
        return mapToResponse(saved);
    }

    // ✅ READ ALL
    public List<UserResponse> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ READ ONE
    public UserResponse getUser(Long id) {
        return mapToResponse(findUser(id));
    }

    // ✅ UPDATE
    public UserResponse updateUser(Long id, User updated) {

        User user = findUser(id);

        // Email update with duplicate check
        if (updated.getEmail() != null) {
            String newEmail = updated.getEmail().trim().toLowerCase();

            if (!newEmail.equals(user.getEmail()) &&
                    repo.findByEmail(newEmail).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }

            user.setEmail(newEmail);
        }

        if (updated.getName() != null) {
            user.setName(updated.getName().trim());
        }

        if (updated.getRole() != null) {
            user.setRole(formatRole(updated.getRole()));
        }

        if (updated.getStatus() != null) {
            user.setStatus(updated.getStatus());
        }

        // Update password only if provided
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        return mapToResponse(repo.save(user));
    }

    // ✅ DELETE
    public void deleteUser(Long id) {
        User user = findUser(id);
        repo.delete(user);
    }

    // 🔁 COMMON METHOD
    private User findUser(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // 🔁 ROLE FORMATTER
    private String formatRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role is required");
        }
        return role.trim().toUpperCase();
    }

    // 🔁 DTO MAPPING
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }
}