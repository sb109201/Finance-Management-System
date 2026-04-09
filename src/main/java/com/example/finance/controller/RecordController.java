package com.example.finance.controller;

import com.example.finance.model.User;
import com.example.finance.model.Record;
import com.example.finance.service.RecordService;
import com.example.finance.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;

    // ✅ CREATE (ONLY ADMIN)
    @PostMapping
    public Record createRecord(
            @Valid @RequestBody Record record,
            @RequestParam(required = false) Long userId) {

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        if (user.getRole() == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN can create records");
        }

        return recordService.saveRecord(record);
    }

    // ✅ GET ALL (ADMIN + ANALYST)
    @GetMapping
    public List<Record> getAllRecords(@RequestParam Long userId) {

        User user = userService.getUserById(userId);

        // ✅ ADD THIS CHECK
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        if (user.getRole() == null ||
                !(user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        return recordService.getAllRecords();
    }
    // ✅ GET BY ID (ADMIN + ANALYST)
    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id, @RequestParam Long userId) {

        User user = userService.getUserById(userId);

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        if (user.getRole() == null ||
                !(user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        return recordService.getRecordById(id);
    }

    // ✅ DELETE (ONLY ADMIN)
    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id, @RequestParam Long userId) {

        User user = userService.getUserById(userId);

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        if (user.getRole() == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN can delete");
        }

        recordService.deleteRecord(id);
        return "Deleted successfully";
    }

    // ✅ FILTER
    @GetMapping("/filter")
    public List<Record> filterRecords(@RequestParam Long userId,
                                      @RequestParam(required = false) String category,
                                      @RequestParam(required = false) String type) {

        User user = userService.getUserById(userId);

        if (user.getRole() == null ||
                !(user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        if (category != null) return recordService.getByCategory(category);
        if (type != null) return recordService.getByType(type);

        return recordService.getAllRecords();
    }

    // ✅ UPDATE (ONLY ADMIN)
    @PutMapping("/{id}")
    public Record updateRecord(@PathVariable Long id, @Valid @RequestBody Record record,
                               @RequestParam Long userId) {

        User user = userService.getUserById(userId);

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        if (user.getRole() == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN can update");
        }

        return recordService.updateRecord(id, record);
    }

    // ✅ PAGINATED RECORDS (ADMIN + ANALYST)
    @GetMapping("/paginated")
    public Page<Record> getRecordsPaginated(@RequestParam Long userId,
                                            @RequestParam int page,
                                            @RequestParam int size) {

        User user = userService.getUserById(userId);

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        if (user.getRole() == null ||
                !(user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("ANALYST"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        return recordService.getRecordsPaginated(page, size);
    }
}