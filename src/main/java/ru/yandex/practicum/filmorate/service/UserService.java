package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

public interface UserService {

    public Collection<User> findAll();

    public User create(User user);

    public User update(User user);

    public User findUserById(long id);

    // Методы для работы с друзьями
    public void addFriend(long userId, long friendId);

    public void removeFriend(long userId, long friendId);

    public Collection<User> getFriends(long userId);

    public Collection<User> getCommonFriends(long userId, long otherId);
}
