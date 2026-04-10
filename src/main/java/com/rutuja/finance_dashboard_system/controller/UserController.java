package com.rutuja.finance_dashboard_system.controller;

import com.rutuja.finance_dashboard_system.dto.UserResponse;
import com.rutuja.finance_dashboard_system.model.User;
import com.rutuja.finance_dashboard_system.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ✅ Anyone can register
    @PostMapping
    public UserResponse create(@RequestBody User user) {
        return service.createUser(user);
    }

    // ✅ Only ADMIN can see all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return service.getAllUsers();
    }

    // ✅ ADMIN only
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getById(@PathVariable Long id) {
        return service.getUser(id);
    }

    // ✅ ADMIN only
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse update(@PathVariable Long id,
                               @RequestBody User user) {
        return service.updateUser(id, user);
    }

    // ✅ ADMIN only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}