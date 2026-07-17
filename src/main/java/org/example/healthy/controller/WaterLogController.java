package org.example.healthy.controller;

import org.example.healthy.model.WaterLog;
import org.example.healthy.service.WaterLogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/water-logs")
public class WaterLogController {

    private final WaterLogService waterLogService;

    public WaterLogController(WaterLogService waterLogService) {
        this.waterLogService = waterLogService;
    }

    // POST /api/water-logs — создать запись воды
    @PostMapping
    public WaterLog createWaterLog(
            @RequestParam Long userId,
            @RequestParam Integer waterMl,
            @RequestParam(required = false) String date) {
        LocalDate logDate = date != null ? LocalDate.parse(date) : null;
        return waterLogService.createWaterLog(userId, waterMl, logDate);
    }

    // GET /api/water-logs/user/{userId} — получить все записи воды пользователя
    @GetMapping("/user/{userId}")
    public List<WaterLog> getWaterLogsByUserId(@PathVariable Long userId) {
        return waterLogService.getWaterLogsByUserId(userId);
    }

    // GET /api/water-logs/user/{userId}/date/{date} — получить записи за дату
    @GetMapping("/user/{userId}/date/{date}")
    public List<WaterLog> getWaterLogsByUserIdAndDate(
            @PathVariable Long userId,
            @PathVariable String date) {
        LocalDate logDate = LocalDate.parse(date);
        return waterLogService.getWaterLogsByUserIdAndDate(userId, logDate);
    }

    // GET /api/water-logs/user/{userId}/date/{date}/total — получить сумму воды за день
    @GetMapping("/user/{userId}/date/{date}/total")
    public Integer getTotalWaterForDay(
            @PathVariable Long userId,
            @PathVariable String date) {
        LocalDate logDate = LocalDate.parse(date);
        return waterLogService.getTotalWaterForDay(userId, logDate);
    }

    // DELETE /api/water-logs/{id} — удалить запись
    @DeleteMapping("/{id}")
    public void deleteWaterLog(@PathVariable Long id) {
        waterLogService.deleteWaterLog(id);
    }
}