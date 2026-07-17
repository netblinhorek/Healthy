package org.example.healthy.repository;

import org.example.healthy.model.UserMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserMealRepository extends JpaRepository<UserMeal, Long> {
    List<UserMeal> findByUserId(Long userId);
    List<UserMeal> findByUserIdAndMealDate(Long userId, LocalDate mealDate);
}