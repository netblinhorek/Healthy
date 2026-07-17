package org.example.healthy.service;

import org.example.healthy.model.FoodItem;
import org.example.healthy.model.MealEntry;
import org.example.healthy.model.UserMeal;
import org.example.healthy.repository.MealEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MealEntryService {

    private final MealEntryRepository mealEntryRepository;
    private final UserMealService userMealService;
    private final FoodService foodService;

    public MealEntryService(MealEntryRepository mealEntryRepository,
                            UserMealService userMealService,
                            FoodService foodService) {
        this.mealEntryRepository = mealEntryRepository;
        this.userMealService = userMealService;
        this.foodService = foodService;
    }

    // Добавить продукт в приём пищи (с автоматическим расчётом КБЖУ)
    public MealEntry addFoodToMeal(MealEntry entry) {
        // Проверяем, что приём пищи существует
        UserMeal meal = userMealService.getMealById(entry.getMeal().getId());
        entry.setMeal(meal);

        // Проверяем, что продукт существует
        FoodItem food = foodService.getFoodById(entry.getFoodItem().getId());
        entry.setFoodItem(food);

        // Рассчитываем КБЖУ на основе веса порции
        BigDecimal weight = entry.getWeightGrams();
        BigDecimal factor = weight.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        entry.setProteins(food.getProteins().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        entry.setFats(food.getFats().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        entry.setCarbs(food.getCarbs().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        entry.setCalories(food.getCalories().multiply(factor).setScale(2, RoundingMode.HALF_UP));

        return mealEntryRepository.save(entry);
    }

    // Получить все записи приёма
    public List<MealEntry> getEntriesByMealId(Long mealId) {
        // Проверяем, что приём существует
        userMealService.getMealById(mealId);
        return mealEntryRepository.findByMealId(mealId);
    }

    // Получить запись по ID
    public MealEntry getEntryById(Long id) {
        return mealEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена с ID: " + id));
    }

    // Обновить запись (вес порции)
    public MealEntry updateEntry(Long id, MealEntry updatedEntry) {
        MealEntry existing = getEntryById(id);

        // Проверяем, что продукт существует
        FoodItem food = foodService.getFoodById(updatedEntry.getFoodItem().getId());
        existing.setFoodItem(food);

        // Обновляем вес и пересчитываем КБЖУ
        BigDecimal weight = updatedEntry.getWeightGrams();
        BigDecimal factor = weight.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        existing.setWeightGrams(weight);

        existing.setProteins(food.getProteins().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        existing.setFats(food.getFats().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        existing.setCarbs(food.getCarbs().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        existing.setCalories(food.getCalories().multiply(factor).setScale(2, RoundingMode.HALF_UP));

        return mealEntryRepository.save(existing);
    }

    // Удалить запись
    public void deleteEntry(Long id) {
        getEntryById(id);
        mealEntryRepository.deleteById(id);
    }

    // Рассчитать сумму КБЖУ за приём
    public MealTotalDTO calculateMealTotal(Long mealId) {
        List<MealEntry> entries = getEntriesByMealId(mealId);

        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProteins = BigDecimal.ZERO;
        BigDecimal totalFats = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;

        for (MealEntry entry : entries) {
            totalCalories = totalCalories.add(entry.getCalories());
            totalProteins = totalProteins.add(entry.getProteins());
            totalFats = totalFats.add(entry.getFats());
            totalCarbs = totalCarbs.add(entry.getCarbs());
        }

        return new MealTotalDTO(totalCalories, totalProteins, totalFats, totalCarbs);
    }

    // Вспомогательный класс для возврата суммы
    public static class MealTotalDTO {
        private final BigDecimal calories;
        private final BigDecimal proteins;
        private final BigDecimal fats;
        private final BigDecimal carbs;

        public MealTotalDTO(BigDecimal calories, BigDecimal proteins, BigDecimal fats, BigDecimal carbs) {
            this.calories = calories;
            this.proteins = proteins;
            this.fats = fats;
            this.carbs = carbs;
        }

        public BigDecimal getCalories() { return calories; }
        public BigDecimal getProteins() { return proteins; }
        public BigDecimal getFats() { return fats; }
        public BigDecimal getCarbs() { return carbs; }
    }
}