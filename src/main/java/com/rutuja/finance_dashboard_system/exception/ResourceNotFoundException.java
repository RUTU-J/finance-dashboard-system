package com.rutuja.finance_dashboard_system.exception;

public class ResourceNotFoundException  extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}