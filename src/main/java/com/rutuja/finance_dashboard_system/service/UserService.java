package com.rutuja.finance_dashboard_system.service;

import com.rutuja.finance_dashboard_system.exception.ResourceNotFoundException;
import com.rutuja.finance_dashboard_system.model.User;
import com.rutuja.finance_dashboard_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createUser(User user) {
        return repo.save(user);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUser(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User updateUser(Long id, User updated) {
        User user = getUser(id);
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setRole(updated.getRole());
        user.setStatus(updated.getStatus());
        return repo.save(user);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
