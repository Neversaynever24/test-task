package org.example.test_task.controler;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.test_task.dto.StatisticsDto;
import org.example.test_task.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/clear")
    public ResponseEntity<String> clearAllData() {
        try {
            return ResponseEntity.ok(adminService.clearAll());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error clearing data: " + e.getMessage());
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDto> getStatistics() {
        return ResponseEntity.ok(adminService.getStatistics());
    }
}
