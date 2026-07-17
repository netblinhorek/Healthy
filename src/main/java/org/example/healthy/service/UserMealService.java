package org.example.healthy.service;

import org.example.healthy.model.UserMeal;
import org.example.healthy.repository.UserMealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserMealService {

    private final UserMealRepository userMealRepository;
    private final UserService userService;

    public UserMealService(UserMealRepository userMealRepository, UserService userService) {
        this.userMealRepository = userMealRepository;
        this.userService = userService;
    }

    // Создать приём пищи
    public UserMeal createMeal(UserMeal meal) {
        // Проверяем, что пользователь существует
        userService.getUserById(meal.getUser().getId());
        meal.setCreatedAt(LocalDateTime.now());
        return userMealRepository.save(meal);
    }

    // Получить все приёмы
    public List<UserMeal> getAllMeals() {
        return userMealRepository.findAll();
    }

    // Получить приём по ID
    public UserMeal getMealById(Long id) {
        return userMealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Приём пищи не найден с ID: " + id));
    }

    // Получить все приёмы пользователя
    public List<UserMeal> getMealsByUserId(Long userId) {
        userService.getUserById(userId);
        return userMealRepository.findByUserId(userId); // ← исправлено!
    }

    // Получить приёмы пользователя за конкретную дату
    public List<UserMeal> getMealsByUserIdAndDate(Long userId, LocalDate date) {
        userService.getUserById(userId);
        return userMealRepository.findByUserIdAndMealDate(userId, date);
    }

    // Обновить приём
    public UserMeal updateMeal(Long id, UserMeal updatedMeal) {
        UserMeal existing = getMealById(id);

        // Проверяем, что пользователь существует
        if (updatedMeal.getUser() != null && updatedMeal.getUser().getId() != null) {
            userService.getUserById(updatedMeal.getUser().getId());
            existing.setUser(updatedMeal.getUser());
        }

        existing.setMealDate(updatedMeal.getMealDate());
        existing.setMealType(updatedMeal.getMealType());

        return userMealRepository.save(existing);
    }

    // Удалить приём
    public void deleteMeal(Long id) {
        getMealById(id);
        userMealRepository.deleteById(id);
    }
}