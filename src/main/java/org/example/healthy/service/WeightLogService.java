package org.example.healthy.service;

import org.example.healthy.model.WeightLog;
import org.example.healthy.model.User;
import org.example.healthy.repository.WeightLogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class WeightLogService {

    private final WeightLogRepository weightLogRepository;
    private final UserService userService;

    public WeightLogService(WeightLogRepository weightLogRepository, UserService userService) {
        this.weightLogRepository = weightLogRepository;
        this.userService = userService;
    }

    // Создать запись веса
    public WeightLog createWeightLog(Long userId, BigDecimal weightKg, LocalDate date) {
        User user = userService.getUserById(userId);
        WeightLog log = new WeightLog();
        log.setUser(user);
        log.setWeightKg(weightKg);
        log.setLogDate(date != null ? date : LocalDate.now());
        return weightLogRepository.save(log);
    }

    // Получить все записи веса пользователя
    public List<WeightLog> getWeightLogsByUserId(Long userId) {
        userService.getUserById(userId);
        return weightLogRepository.findByUserId(userId);
    }

    // Получить записи веса пользователя за дату
    public List<WeightLog> getWeightLogsByUserIdAndDate(Long userId, LocalDate date) {
        userService.getUserById(userId);
        return weightLogRepository.findByUserIdAndLogDate(userId, date);
    }

    // Получить последнюю запись веса пользователя
    public WeightLog getLatestWeight(Long userId) {
        userService.getUserById(userId);
        return weightLogRepository.findTopByUserIdOrderByLogDateDesc(userId);
    }

    // Удалить запись
    public void deleteWeightLog(Long id) {
        weightLogRepository.deleteById(id);
    }
}