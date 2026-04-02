package com.example.finance.controller;

import com.example.finance.model.Record;
import com.example.finance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    // Create a new record
    @PostMapping
    public Record createRecord(@RequestBody Record record) {
        return recordService.saveRecord(record);
    }

    // Get all records
    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }

    // Get record by ID
    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    // Delete record
    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return "Record deleted successfully!";
    }
}