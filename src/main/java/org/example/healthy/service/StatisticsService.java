package org.example.healthy.service;

import org.example.healthy.model.MealEntry;
import org.example.healthy.model.UserMeal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private final UserMealService userMealService;
    private final MealEntryService mealEntryService;
    private final WaterLogService waterLogService;

    public StatisticsService(UserMealService userMealService,
                             MealEntryService mealEntryService,
                             WaterLogService waterLogService) {
        this.userMealService = userMealService;
        this.mealEntryService = mealEntryService;
        this.waterLogService = waterLogService;
    }

    // Дневная сводка
    public DailySummaryDTO getDailySummary(Long userId, LocalDate date) {
        // Получаем все приёмы пользователя за дату
        List<UserMeal> meals = userMealService.getMealsByUserIdAndDate(userId, date);

        // Собираем все записи из всех приёмов
        List<MealEntry> allEntries = new ArrayList<>();
        for (UserMeal meal : meals) {
            allEntries.addAll(mealEntryService.getEntriesByMealId(meal.getId()));
        }

        // Суммируем КБЖУ
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProteins = BigDecimal.ZERO;
        BigDecimal totalFats = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;

        for (MealEntry entry : allEntries) {
            totalCalories = totalCalories.add(entry.getCalories());
            totalProteins = totalProteins.add(entry.getProteins());
            totalFats = totalFats.add(entry.getFats());
            totalCarbs = totalCarbs.add(entry.getCarbs());
        }

        // Получаем количество воды за день
        Integer totalWater = waterLogService.getTotalWaterForDay(userId, date);

        // Количество приёмов
        Integer mealCount = meals.size();

        return new DailySummaryDTO(
                date,
                totalCalories.setScale(2, RoundingMode.HALF_UP),
                totalProteins.setScale(2, RoundingMode.HALF_UP),
                totalFats.setScale(2, RoundingMode.HALF_UP),
                totalCarbs.setScale(2, RoundingMode.HALF_UP),
                totalWater,
                mealCount,
                meals
        );
    }

    // Ежедневная сводка за неделю
    public List<DailySummaryDTO> getWeeklySummary(Long userId, LocalDate startDate) {
        List<DailySummaryDTO> weekSummary = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            weekSummary.add(getDailySummary(userId, date));
        }
        return weekSummary;
    }

    // DTO для дневной сводки
    public static class DailySummaryDTO {
        private final LocalDate date;
        private final BigDecimal totalCalories;
        private final BigDecimal totalProteins;
        private final BigDecimal totalFats;
        private final BigDecimal totalCarbs;
        private final Integer totalWater;
        private final Integer mealCount;
        private final List<UserMeal> meals;

        public DailySummaryDTO(LocalDate date,
                               BigDecimal totalCalories,
                               BigDecimal totalProteins,
                               BigDecimal totalFats,
                               BigDecimal totalCarbs,
                               Integer totalWater,
                               Integer mealCount,
                               List<UserMeal> meals) {
            this.date = date;
            this.totalCalories = totalCalories;
            this.totalProteins = totalProteins;
            this.totalFats = totalFats;
            this.totalCarbs = totalCarbs;
            this.totalWater = totalWater;
            this.mealCount = mealCount;
            this.meals = meals;
        }

        public LocalDate getDate() { return date; }
        public BigDecimal getTotalCalories() { return totalCalories; }
        public BigDecimal getTotalProteins() { return totalProteins; }
        public BigDecimal getTotalFats() { return totalFats; }
        public BigDecimal getTotalCarbs() { return totalCarbs; }
        public Integer getTotalWater() { return totalWater; }
        public Integer getMealCount() { return mealCount; }
        public List<UserMeal> getMeals() { return meals; }
    }
}