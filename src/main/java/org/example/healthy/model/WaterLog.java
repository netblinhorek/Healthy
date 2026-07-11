package org.example.healthy.model;
import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_logs")
public class WaterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "water_ml", nullable = false)
    private Integer waterMl;

    @Column(name = "log_date")
    private LocalDate logDate;

    public WaterLog() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getWaterMl() { return waterMl; }
    public void setWaterMl(Integer waterMl) { this.waterMl = waterMl; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }
}