package com.example.csrfdemo.broken_access_control;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    // "База данных"
    private final List<User> users = new ArrayList<>(
            List.of(
                    new User(1, "admin", "secret123", "admin@test.com", "admin"),
                    new User(2, "user1", "qwerty", "user1@test.com", "user")
            )
    );

    // ❌ УЯЗВИМОСТЬ 1: раскрытие паролей и ID
    @GetMapping
    public List<User> getUsers() {
        return users; // отправляем ВСЁ
    }

    // ❌ УЯЗВИМОСТЬ 2: нет проверки прав + mass assignment
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable int id,
            @RequestParam String token,
            @RequestBody Map<String, Object> updates) {

        checkAdmin(token);

        User user = users.stream()
                .filter(u -> u.id == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<String> allowedUpdates = Set.of("email", "password");

        for (String field : updates.keySet()) {
            if (!allowedUpdates.contains(field)) {
                throw new RuntimeException("Invalid updates");
            }
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "email" -> user.email = value.toString();
                case "password" -> user.password = value.toString();
            }
        });

        return user;
    }

    private void checkAdmin(String token) {
        if (!"admin_token".equals(token)) {
            throw new RuntimeException("Forbidden");
        }
    }
}