package org.example.healthy.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.healthy.model.User;

@Entity
@Table(name = "user_meals")

public class UserMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "meal_date", nullable = false)
    private LocalDate  mealDate;

    @Column(name = "meal_type", nullable = false, length = 20)
    private String mealType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealEntry> entries = new ArrayList<>();

    public UserMeal() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getMealDate() { return mealDate; }
    public void setMealDate(LocalDate mealDate) { this.mealDate = mealDate; }

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<MealEntry> getEntries() { return entries; }
    public void setEntries(List<MealEntry> entries) { this.entries = entries; }

}

