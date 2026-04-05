package com.example.finance.controller;

import com.example.finance.model.Record;
import com.example.finance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private RecordService recordService;

    // Get summary: total income, total expense, balance
    @GetMapping
    public DashboardResponse getDashboard(@RequestParam String role) {

        // ✅ Access Control
        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Access Denied"
            );
        }

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
    @GetMapping("/category")
    public Map<String, Double> getCategorySummary(@RequestParam String role) {

        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        List<Record> records = recordService.getAllRecords();

        Map<String, Double> summary = new HashMap<>();

        for (Record record : records) {
            summary.put(
                    record.getCategory(),
                    summary.getOrDefault(record.getCategory(), 0.0) + record.getAmount()
            );
        }

        return summary;
    }
    @GetMapping("/recent")
    public List<Record> getRecentRecords(@RequestParam String role) {

        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        return recordService.getRecentRecords();
    }
    @GetMapping("/monthly")
    public Map<String, Double> getMonthlySummary(@RequestParam String role) {

        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        List<Record> records = recordService.getAllRecords();

        Map<String, Double> monthlySummary = new HashMap<>();

        for (Record r : records) {
            String month = r.getDate().getMonth().toString();

            monthlySummary.put(
                    month,
                    monthlySummary.getOrDefault(month, 0.0) + r.getAmount()
            );
        }

        return monthlySummary;
    }
}