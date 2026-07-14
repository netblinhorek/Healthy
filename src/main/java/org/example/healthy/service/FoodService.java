package org.example.healthy.service;

import org.example.healthy.model.FoodItem;
import org.example.healthy.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class FoodService {

    private final FoodItemRepository foodItemRepository;

    public FoodService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    public FoodItem createFood(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> getAllFoods() {
        return foodItemRepository.findAll();
    }

    public FoodItem getFoodById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден с ID: " + id));
    }

    public FoodItem updateFood(Long id, FoodItem updatedFood) {
        FoodItem existing = getFoodById(id);
        existing.setName(updatedFood.getName());
        existing.setProteins(updatedFood.getProteins());
        existing.setFats(updatedFood.getFats());
        existing.setCarbs(updatedFood.getCarbs());
        existing.setCalories(updatedFood.getCalories());
        existing.setUser(updatedFood.getUser());
        return foodItemRepository.save(existing);
    }

    public void deleteFood(Long id) {
        foodItemRepository.deleteById(id);
    }

    public List<FoodItem> getFoodsForUser(Long userId) {
        return foodItemRepository.findByUserIdOrUserIdIsNull(userId);
    }

}
