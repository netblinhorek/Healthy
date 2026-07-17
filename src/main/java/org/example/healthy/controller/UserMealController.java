package org.example.healthy.controller;

import org.example.healthy.model.UserMeal;
import org.example.healthy.service.UserMealService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class UserMealController {

    private final UserMealService userMealService;

    public UserMealController(UserMealService userMealService) {
        this.userMealService = userMealService;
    }

    // POST /api/meals — создать приём пищи
    @PostMapping
    public UserMeal createMeal(@RequestBody UserMeal meal) {
        return userMealService.createMeal(meal);
    }

    // GET /api/meals — получить все приёмы
    @GetMapping
    public List<UserMeal> getAllMeals() {
        return userMealService.getAllMeals();
    }

    // GET /api/meals/{id} — получить приём по ID
    @GetMapping("/{id}")
    public UserMeal getMealById(@PathVariable Long id) {
        return userMealService.getMealById(id);
    }

    // GET /api/meals/user/{userId} — получить приёмы пользователя
    @GetMapping("/user/{userId}")
    public List<UserMeal> getMealsByUserId(@PathVariable Long userId) {
        return userMealService.getMealsByUserId(userId);
    }

    // GET /api/meals/user/{userId}/date/{date} — получить приёмы за дату
    @GetMapping("/user/{userId}/date/{date}")
    public List<UserMeal> getMealsByUserIdAndDate(
            @PathVariable Long userId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date); // ожидается формат YYYY-MM-DD
        return userMealService.getMealsByUserIdAndDate(userId, localDate);
    }

    // PUT /api/meals/{id} — обновить приём
    @PutMapping("/{id}")
    public UserMeal updateMeal(@PathVariable Long id, @RequestBody UserMeal updatedMeal) {
        return userMealService.updateMeal(id, updatedMeal);
    }

    // DELETE /api/meals/{id} — удалить приём
    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable Long id) {
        userMealService.deleteMeal(id);
    }
}