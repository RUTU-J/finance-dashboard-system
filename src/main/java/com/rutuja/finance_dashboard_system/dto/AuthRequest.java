package com.rutuja.finance_dashboard_system.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}