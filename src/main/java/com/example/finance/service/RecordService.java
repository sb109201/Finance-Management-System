package com.example.finance.service;

import com.example.finance.model.Record;
import com.example.finance.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    // Create a new record
    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    // Get all records
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    // Get record by ID
    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }

    // Delete record
    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}