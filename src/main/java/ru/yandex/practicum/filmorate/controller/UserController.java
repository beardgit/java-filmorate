package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> dataUsers = new HashMap<>();

    //получение списка всех пользователей.
    @GetMapping
    public Collection<User> findAllUsers() {
        return dataUsers.values();
    }

    //создание пользователя;
    @PostMapping
    public User appendUser(@Valid @RequestBody User user) {
        log.info("Получен пользователь user по POST запросу{}", user);
        long idUser = getNextId();
        log.info("Создание idUser: {}", idUser);
        LocalDate currentDateTime = LocalDate.now();
        log.info("Получение текущей даты: {}", currentDateTime);

        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }

        User createdUser = new User(idUser, user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday());
        log.info("Создание сущности пользователя: {}", createdUser);
        dataUsers.put(idUser, createdUser);
        return createdUser;
    }

    //обновление пользователя;
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен для обновления user: {}", user);
        long idUpdateUser = user.getId();
        if (!dataUsers.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не найден");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }
        User updatedUser = new User(
                idUpdateUser,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        log.info("Создана новая сущность пользователя для обновления updatadUser: {}", updatedUser);
        dataUsers.put(idUpdateUser, updatedUser);

        return updatedUser;
    }

    // вспомогательный метод для генерации идентификатора нового id
    private long getNextId() {
        long currentMaxId = dataUsers.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}

