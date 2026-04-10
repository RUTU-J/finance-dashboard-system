package com.rutuja.finance_dashboard_system.controller;

import com.rutuja.finance_dashboard_system.service.FinancialRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final FinancialRecordService service;

    public DashboardController(FinancialRecordService service) {
        this.service = service;
    }

    // ✅ Only ANALYST or ADMIN can access dashboard summary
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public Map<String, Double> summary() {
        return Map.of(
                "totalIncome", service.totalIncome(),
                "totalExpense", service.totalExpense(),
                "netBalance", service.netBalance()
        );
    }

    // ✅ Only ANALYST or ADMIN can access category-wise summary
    @GetMapping("/category")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public Map<String, Double> categorySummary() {
        return service.categorySummary();
    }
}