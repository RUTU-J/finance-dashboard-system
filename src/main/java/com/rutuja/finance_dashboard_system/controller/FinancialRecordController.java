package com.rutuja.finance_dashboard_system.controller;

import com.rutuja.finance_dashboard_system.model.FinancialRecord;
import com.rutuja.finance_dashboard_system.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    // ✅ CREATE (ADMIN, ANALYST only)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public FinancialRecord create(@Valid @RequestBody FinancialRecord record) {
        return service.create(record);
    }

    // ✅ READ (All authenticated users)
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<FinancialRecord> getAll() {
        return service.getAll();
    }

    // ✅ PAGINATION (safe)
    @GetMapping("/paginated")
    @PreAuthorize("isAuthenticated()")
    public Page<FinancialRecord> getPaginatedRecords(Pageable pageable) {
        return service.getPaginatedRecords(pageable);
    }

    // ✅ FILTER (NEW - useful API)
    @GetMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    public List<FinancialRecord> filter(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {
        return service.filter(type, category);
    }

    // ✅ UPDATE (ADMIN, ANALYST)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public FinancialRecord update(@PathVariable Long id,
                                  @Valid @RequestBody FinancialRecord record) {
        return service.update(id, record);
    }

    // ✅ DELETE (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}