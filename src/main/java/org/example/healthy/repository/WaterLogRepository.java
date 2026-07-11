package org.example.healthy.repository;

import org.example.healthy.model.WaterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WaterLogRepository extends JpaRepository<WaterLog, Long> {

    List<WaterLog> findByUserId(Long userId);

    List<WaterLog> findByUserIdAndLogDate(Long userId, LocalDate logDate);
}