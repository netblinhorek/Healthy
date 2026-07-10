package org.example.healthy.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_items")

public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal proteins;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fats;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal carbs;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal calories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FoodItem() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getProteins() { return proteins; }
    public void setProteins(BigDecimal proteins) { this.proteins = proteins; }

    public BigDecimal getFats() { return fats; }
    public void setFats(BigDecimal fats) { this.fats = fats; }

    public BigDecimal getCarbs() { return carbs; }
    public void setCarbs(BigDecimal carbs) { this.carbs = carbs; }

    public BigDecimal getCalories() { return calories; }
    public void setCalories(BigDecimal calories) { this.calories = calories; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
