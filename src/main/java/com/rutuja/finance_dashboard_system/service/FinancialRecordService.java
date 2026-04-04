package com.rutuja.finance_dashboard_system.service;

import com.rutuja.finance_dashboard_system.config.AppConstants;
import com.rutuja.finance_dashboard_system.exception.ResourceNotFoundException;
import com.rutuja.finance_dashboard_system.model.FinancialRecord;
import com.rutuja.finance_dashboard_system.repository.FinancialRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialRecordService {
    private final FinancialRecordRepository repo;

    public FinancialRecordService(FinancialRecordRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public FinancialRecord create(FinancialRecord record, String role) {

        if (role.equals(AppConstants.ROLE_VIEWER)) {
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
    public FinancialRecord update(Long id, FinancialRecord updated, String role) {

        if (role.equals(AppConstants.ROLE_VIEWER)) {
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
    public void delete(Long id, String role) {

        if (!role.equals(AppConstants.ROLE_ADMIN)) {
            throw new RuntimeException("Only ADMIN can delete");
        }

        repo.deleteById(id);
    }
    // FILTER
    public List<FinancialRecord> filter(String type, String category) {

        if (type != null) return repo.findByType(type);
        if (category != null) return repo.findByCategory(category);

        return repo.findAll();
    }
    // DASHBOARD

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
    private void validate(FinancialRecord record) {
        if (record.getAmount() == null || record.getAmount() <= 0)
            throw new RuntimeException("Invalid amount");

        if (record.getType() == null)
            throw new RuntimeException("Type is required");
    }
}
