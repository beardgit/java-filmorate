package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;


@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> dataUsers = new HashMap<>();

    @Override
    public User findUserById(long id) {
        validateUserExists(id);
        return dataUsers.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(dataUsers.values());
    }

    @Override
    public User create(User user) {
        long idUser = getNextId();
        LocalDate currentDateTime = LocalDate.now();
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }

        User createdUser = new User(idUser,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        dataUsers.put(idUser, createdUser);
        return createdUser;
    }

    @Override
    public User update(User user) {
        long idUpdateUser = user.getId();
        validateUserExists(user.getId());
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

    private void validateUserExists(long userId) {
        if (!dataUsers.containsKey(userId)) {
            throw new ValidationException("Пользователь с id " + userId + " не найден");
        }
    }
}
