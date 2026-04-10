package com.rutuja.finance_dashboard_system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;

    private String type; // INCOME / EXPENSE

    private String category;
    private LocalDate date;
    private String description;

    @ManyToOne
    private User createdBy;

}