package com.rutuja.finance_dashboard_system.service;

import com.rutuja.finance_dashboard_system.config.AppConstants;
import com.rutuja.finance_dashboard_system.exception.ResourceNotFoundException;
import com.rutuja.finance_dashboard_system.model.FinancialRecord;
import com.rutuja.finance_dashboard_system.repository.FinancialRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository repo;

    public FinancialRecordService(FinancialRecordRepository repo) {
        this.repo = repo;
    }

    // 🔐 Get logged-in user role
    private String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities().isEmpty()) {
            return "";
        }
        return auth.getAuthorities().iterator().next().getAuthority(); // e.g., ROLE_ADMIN
    }

    // CREATE
    public FinancialRecord create(FinancialRecord record) {

        String role = getRole();

        if ("ROLE_VIEWER".equals(role)) {
            throw new RuntimeException("Viewer cannot create records");
        }

        validate(record);
        return repo.save(record);
    }

    // READ
    public List<FinancialRecord> getAll() {
        return repo.findAll();
    }

    public Page<FinancialRecord> getPaginatedRecords(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // UPDATE
    public FinancialRecord update(Long id, FinancialRecord updated) {

        String role = getRole();

        if ("ROLE_VIEWER".equals(role)) {
            throw new RuntimeException("Viewer cannot update records");
        }

        FinancialRecord record = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        record.setAmount(updated.getAmount());
        record.setType(updated.getType());
        record.setCategory(updated.getCategory());
        record.setDate(updated.getDate());
        record.setDescription(updated.getDescription());

        return repo.save(record);
    }

    // DELETE
    public void delete(Long id) {

        String role = getRole();

        if (!"ROLE_ADMIN".equals(role)) {
            throw new RuntimeException("Only ADMIN can delete");
        }

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Record not found");
        }

        repo.deleteById(id);
    }

    // FILTER
    public List<FinancialRecord> filter(String type, String category) {
        if (type != null && !type.isBlank()) return repo.findByType(type);
        if (category != null && !category.isBlank()) return repo.findByCategory(category);
        return repo.findAll();
    }

    // DASHBOARD (optimized)
    public double totalIncome() {
        return repo.findAll().stream()
                .filter(r -> AppConstants.TYPE_INCOME.equalsIgnoreCase(r.getType()))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double totalExpense() {
        return repo.findAll().stream()
                .filter(r -> AppConstants.TYPE_EXPENSE.equalsIgnoreCase(r.getType()))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double netBalance() {
        return totalIncome() - totalExpense();
    }

    public Map<String, Double> categorySummary() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory,
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));
    }

    // VALIDATION
    private void validate(FinancialRecord record) {
        if (record.getAmount() == null || record.getAmount() <= 0)
            throw new RuntimeException("Invalid amount");
        if (record.getType() == null || record.getType().isBlank())
            throw new RuntimeException("Type is required");
    }
}