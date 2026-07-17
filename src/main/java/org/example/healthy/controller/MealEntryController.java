package org.example.healthy.controller;

import org.example.healthy.model.MealEntry;
import org.example.healthy.service.MealEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-entries")
public class MealEntryController {

    private final MealEntryService mealEntryService;

    public MealEntryController(MealEntryService mealEntryService) {
        this.mealEntryService = mealEntryService;
    }

    // POST /api/meal-entries — добавить продукт в приём
    @PostMapping
    public MealEntry addFoodToMeal(@RequestBody MealEntry entry) {
        return mealEntryService.addFoodToMeal(entry);
    }

    // GET /api/meal-entries/meal/{mealId} — получить все записи приёма
    @GetMapping("/meal/{mealId}")
    public List<MealEntry> getEntriesByMealId(@PathVariable Long mealId) {
        return mealEntryService.getEntriesByMealId(mealId);
    }

    // GET /api/meal-entries/{id} — получить запись по ID
    @GetMapping("/{id}")
    public MealEntry getEntryById(@PathVariable Long id) {
        return mealEntryService.getEntryById(id);
    }

    // PUT /api/meal-entries/{id} — обновить запись
    @PutMapping("/{id}")
    public MealEntry updateEntry(@PathVariable Long id, @RequestBody MealEntry updatedEntry) {
        return mealEntryService.updateEntry(id, updatedEntry);
    }

    // DELETE /api/meal-entries/{id} — удалить запись
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id) {
        mealEntryService.deleteEntry(id);
    }

    // GET /api/meal-entries/meal/{mealId}/total — получить сумму КБЖУ за приём
    @GetMapping("/meal/{mealId}/total")
    public MealEntryService.MealTotalDTO getMealTotal(@PathVariable Long mealId) {
        return mealEntryService.calculateMealTotal(mealId);
    }
}