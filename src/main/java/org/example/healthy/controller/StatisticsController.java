package org.example.healthy.controller;

import org.example.healthy.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // GET /api/statistics/daily/{userId}/{date} — получить дневную сводку
    @GetMapping("/daily/{userId}/{date}")
    public StatisticsService.DailySummaryDTO getDailySummary(
            @PathVariable Long userId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return statisticsService.getDailySummary(userId, localDate);
    }

    // GET /api/statistics/weekly/{userId}/{startDate} — получить сводку за неделю
    @GetMapping("/weekly/{userId}/{startDate}")
    public List<StatisticsService.DailySummaryDTO> getWeeklySummary(
            @PathVariable Long userId,
            @PathVariable String startDate) {
        LocalDate localDate = LocalDate.parse(startDate);
        return statisticsService.getWeeklySummary(userId, localDate);
    }
}