package org.example.healthy.service;

import org.example.healthy.model.WaterLog;
import org.example.healthy.model.User;
import org.example.healthy.repository.WaterLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WaterLogService {

    private final WaterLogRepository waterLogRepository;
    private final UserService userService;

    public WaterLogService(WaterLogRepository waterLogRepository, UserService userService) {
        this.waterLogRepository = waterLogRepository;
        this.userService = userService;
    }

    // Создать запись воды
    public WaterLog createWaterLog(Long userId, Integer waterMl, LocalDate date) {
        User user = userService.getUserById(userId);
        WaterLog log = new WaterLog();
        log.setUser(user);
        log.setWaterMl(waterMl);
        log.setLogDate(date != null ? date : LocalDate.now());
        return waterLogRepository.save(log);
    }

    // Получить все записи воды пользователя
    public List<WaterLog> getWaterLogsByUserId(Long userId) {
        userService.getUserById(userId);
        return waterLogRepository.findByUserId(userId);
    }

    // Получить записи воды пользователя за дату
    public List<WaterLog> getWaterLogsByUserIdAndDate(Long userId, LocalDate date) {
        userService.getUserById(userId);
        return waterLogRepository.findByUserIdAndLogDate(userId, date);
    }

    // Получить сумму воды за день
    public Integer getTotalWaterForDay(Long userId, LocalDate date) {
        userService.getUserById(userId);
        List<WaterLog> logs = waterLogRepository.findByUserIdAndLogDate(userId, date);
        return logs.stream().mapToInt(WaterLog::getWaterMl).sum();
    }

    // Удалить запись
    public void deleteWaterLog(Long id) {
        waterLogRepository.deleteById(id);
    }
}