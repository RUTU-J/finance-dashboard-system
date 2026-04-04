package com.rutuja.finance_dashboard_system.controller;

import com.rutuja.finance_dashboard_system.model.FinancialRecord;
import com.rutuja.finance_dashboard_system.service.FinancialRecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {
    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }
    @PostMapping
    public FinancialRecord create(@RequestBody FinancialRecord record,
                                  @RequestParam String role) {
        return service.create(record, role);
    }

    @GetMapping
    public List<FinancialRecord> getAll() {
        return service.getAll();
    }

    @GetMapping("/paginated")
    public Page<FinancialRecord> getPaginatedRecords(Pageable pageable) {
        return service.getPaginatedRecords(pageable);
    }

    @PutMapping("/{id}")
    public FinancialRecord update(@PathVariable Long id,
                                  @RequestBody FinancialRecord record,
                                  @RequestParam String role) {
        return service.update(id, record, role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestParam String role) {
        service.delete(id, role);
    }

}
