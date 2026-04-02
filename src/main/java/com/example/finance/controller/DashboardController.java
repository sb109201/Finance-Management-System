package com.example.finance.controller;

import com.example.finance.model.Record;
import com.example.finance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private RecordService recordService;

    // Get summary: total income, total expense, balance
    @GetMapping
    public DashboardResponse getDashboard() {

        List<Record> records = recordService.getAllRecords();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Record record : records) {
            if ("INCOME".equalsIgnoreCase(record.getType())) {
                totalIncome += record.getAmount();
            } else {
                totalExpense += record.getAmount();
            }
        }

        double balance = totalIncome - totalExpense;

        return new DashboardResponse(totalIncome, totalExpense, balance);
    }

    // Response DTO
    static class DashboardResponse {
        private double totalIncome;
        private double totalExpense;
        private double balance;

        public DashboardResponse(double totalIncome, double totalExpense, double balance) {
            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
            this.balance = balance;
        }

        public double getTotalIncome() {
            return totalIncome;
        }

        public double getTotalExpense() {
            return totalExpense;
        }

        public double getBalance() {
            return balance;
        }
    }
}