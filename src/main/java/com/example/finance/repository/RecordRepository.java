package com.example.finance.repository;

import com.example.finance.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;


public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByCategory(String category);
    List<Record> findByType(String type);
    // 🔹 ✅ THIS IS MISSING → ADD THIS
    List<Record> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Record> findAllByOrderByDateDesc(Pageable pageable);
}