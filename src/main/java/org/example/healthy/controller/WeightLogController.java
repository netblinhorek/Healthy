package org.example.healthy.controller;

import org.example.healthy.model.WeightLog;
import org.example.healthy.service.WeightLogService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weight-logs")
public class WeightLogController {

    private final WeightLogService weightLogService;

    public WeightLogController(WeightLogService weightLogService) {
        this.weightLogService = weightLogService;
    }

    // POST /api/weight-logs — создать запись веса
    @PostMapping
    public WeightLog createWeightLog(
            @RequestParam Long userId,
            @RequestParam BigDecimal weightKg,
            @RequestParam(required = false) String date) {
        LocalDate logDate = date != null ? LocalDate.parse(date) : null;
        return weightLogService.createWeightLog(userId, weightKg, logDate);
    }

    // GET /api/weight-logs/user/{userId} — получить все записи веса пользователя
    @GetMapping("/user/{userId}")
    public List<WeightLog> getWeightLogsByUserId(@PathVariable Long userId) {
        return weightLogService.getWeightLogsByUserId(userId);
    }

    // GET /api/weight-logs/user/{userId}/date/{date} — получить записи за дату
    @GetMapping("/user/{userId}/date/{date}")
    public List<WeightLog> getWeightLogsByUserIdAndDate(
            @PathVariable Long userId,
            @PathVariable String date) {
        LocalDate logDate = LocalDate.parse(date);
        return weightLogService.getWeightLogsByUserIdAndDate(userId, logDate);
    }

    // GET /api/weight-logs/user/{userId}/latest — получить последнюю запись
    @GetMapping("/user/{userId}/latest")
    public WeightLog getLatestWeight(@PathVariable Long userId) {
        return weightLogService.getLatestWeight(userId);
    }

    // DELETE /api/weight-logs/{id} — удалить запись
    @DeleteMapping("/{id}")
    public void deleteWeightLog(@PathVariable Long id) {
        weightLogService.deleteWeightLog(id);
    }
}