package org.example.healthy.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "meal_entries")
public class MealEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private UserMeal meal;

    @ManyToOne
    @JoinColumn(name = "food_item_id", nullable = false)
    private FoodItem foodItem;

    @Column(name = "weight_grams", nullable = false, precision = 10, scale = 2)
    private BigDecimal weightGrams;

    // Слепок КБЖУ на момент создания записи
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal proteins;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fats;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal carbs;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal calories;

    public MealEntry() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserMeal getMeal() { return meal; }
    public void setMeal(UserMeal meal) { this.meal = meal; }

    public FoodItem getFoodItem() { return foodItem; }
    public void setFoodItem(FoodItem foodItem) { this.foodItem = foodItem; }

    public BigDecimal getWeightGrams() { return weightGrams; }
    public void setWeightGrams(BigDecimal weightGrams) { this.weightGrams = weightGrams; }

    public BigDecimal getProteins() { return proteins; }
    public void setProteins(BigDecimal proteins) { this.proteins = proteins; }

    public BigDecimal getFats() { return fats; }
    public void setFats(BigDecimal fats) { this.fats = fats; }

    public BigDecimal getCarbs() { return carbs; }
    public void setCarbs(BigDecimal carbs) { this.carbs = carbs; }

    public BigDecimal getCalories() { return calories; }
    public void setCalories(BigDecimal calories) { this.calories = calories; }
}