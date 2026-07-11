package org.example.healthy.repository;

import org.example.healthy.model.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeightLogRepository extends JpaRepository<WeightLog, Long> {

    List<WeightLog> findByUserId(Long userId);

    List<WeightLog> findByUserIdAndLogDate(Long userId, LocalDate logDate);

    WeightLog findTopByUserIdOrderByLogDateDesc(Long userId);
}