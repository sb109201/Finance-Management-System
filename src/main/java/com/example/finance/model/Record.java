package com.example.finance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    @NotNull
    private Double amount;

    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd")   // 🔥 IMPORTANT
    private LocalDate date;

    private String description;

    // Getters & Setters
    public Long getId() { return id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}