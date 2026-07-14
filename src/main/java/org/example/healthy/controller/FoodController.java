package org.example.healthy.controller;

import org.example.healthy.model.FoodItem;
import org.example.healthy.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")  // ← /api/foods, а не /api/users!
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // POST /api/foods — создать продукт
    @PostMapping
    public FoodItem createFood(@RequestBody FoodItem foodItem) {
        return foodService.createFood(foodItem);
    }

    // GET /api/foods — получить все продукты
    @GetMapping
    public List<FoodItem> getAllFoods() {
        return foodService.getAllFoods();
    }

    // GET /api/foods/{id} — получить продукт по ID
    @GetMapping("/{id}")
    public FoodItem getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    // PUT /api/foods/{id} — обновить продукт
    @PutMapping("/{id}")
    public FoodItem updateFood(@PathVariable Long id, @RequestBody FoodItem updatedFood) {
        return foodService.updateFood(id, updatedFood);
    }

    // DELETE /api/foods/{id} — удалить продукт
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
    }

    // GET /api/foods/user/{userId} — получить продукты пользователя (и общие)
    @GetMapping("/user/{userId}")
    public List<FoodItem> getFoodsForUser(@PathVariable Long userId) {
        return foodService.getFoodsForUser(userId);
    }
}