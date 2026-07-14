package org.example.healthy.controller;

import org.example.healthy.model.User;
import org.example.healthy.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users - создать пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user.getEmail(), user.getPasswordHash());
    }

    // GET /api/users - получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id} - получить пользователя по ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // PUT /api/users/{id}/calorie-goal - обновить норму калорий
    @PutMapping("/{id}/calorie-goal")
    public User updateCalorieGoal(@PathVariable Long id, @RequestParam Integer goal) {
        return userService.updateCalorieGoal(id, goal);
    }

    // DELETE /api/users/{id} - удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}