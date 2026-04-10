package com.rutuja.finance_dashboard_system.service;

import com.rutuja.finance_dashboard_system.model.User;
import com.rutuja.finance_dashboard_system.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Add ROLE_ prefix for Spring Security
        String role = "ROLE_" + user.getRole().toUpperCase();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toUpperCase()) // Spring will internally add ROLE_ prefix
                .build();
    }
}