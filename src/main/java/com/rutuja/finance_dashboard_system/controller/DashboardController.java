package com.rutuja.finance_dashboard_system.controller;

import com.rutuja.finance_dashboard_system.service.FinancialRecordService;
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
    @GetMapping("/summary")
    public Map<String, Double> summary() {

        return Map.of(
                "totalIncome", service.totalIncome(),
                "totalExpense", service.totalExpense(),
                "netBalance", service.netBalance()
        );
    }
    @GetMapping("/category")
    public Map<String, Double> categorySummary() {
        return service.categorySummary();
    }

}
