package com.example.finance.service;

import java.util.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import com.example.finance.repository.RecordRepository;
import com.example.finance.model.Record;
import java.time.LocalDate;
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public Record saveRecord(Record record) {
        String category = autoCategorize(record.getDescription());
        record.setCategory(category);
        return recordRepository.save(record);
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    private String autoCategorize(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Other";
        }

        description = description.toLowerCase();

        Map<String, List<String>> categoryMap = new HashMap<>();

        categoryMap.put("Food", Arrays.asList("swiggy", "zomato", "food"));
        categoryMap.put("Travel", Arrays.asList("uber", "ola", "travel"));
        categoryMap.put("Rent", Arrays.asList("rent", "house rent"));
        categoryMap.put("Entertainment", Arrays.asList("movie", "netflix"));
        categoryMap.put("Income", Arrays.asList("salary", "credited", "income"));

        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (description.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }

        return "Other";
    }
    public List<Record> getByType(String type) {
        return recordRepository.findByType(type);
    }

    // Filter by category
    public List<Record> getByCategory(String category) {
        return recordRepository.findByCategory(category);
    }

    // Filter by date range
    public List<Record> getByDateRange(LocalDate start, LocalDate end) {
        return recordRepository.findByDateBetween(start, end);
    }
    // 🔹 Total Income
    public Double getTotalIncome() {
        return recordRepository.findAll().stream()
                .filter(r -> "INCOME".equalsIgnoreCase(r.getType()))
                .mapToDouble(Record::getAmount)
                .sum();
    }

    // 🔹 Total Expense
    public Double getTotalExpense() {
        return recordRepository.findAll().stream()
                .filter(r -> "EXPENSE".equalsIgnoreCase(r.getType()))
                .mapToDouble(Record::getAmount)
                .sum();
    }

    // 🔹 Net Balance
    public Double getNetBalance() {
        return getTotalIncome() - getTotalExpense();
    }
    public Record updateRecord(Long id, Record newRecord) {

        Record existing = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        // ✅ Update fields safely
        existing.setAmount(newRecord.getAmount());
        existing.setType(newRecord.getType());
        existing.setDescription(newRecord.getDescription());
        existing.setDate(newRecord.getDate());

        // ✅ Auto categorize again
        String category = autoCategorize(newRecord.getDescription());
        existing.setCategory(category);

        return recordRepository.save(existing);
    }
    public List<Record> getRecentRecords() {
        return recordRepository.findAllByOrderByDateDesc(PageRequest.of(0, 5));
    }
    public Page<Record> getRecordsPaginated(int page, int size) {
        return recordRepository.findAll(PageRequest.of(page, size));
    }
}