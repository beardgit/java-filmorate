package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User findUserById(long id) {
        return userStorage.findUserById(id);
    }

    // Методы для работы с друзьями
    public void addFriend(long userId, long friendId) {
        // Проверяем, что пользователи существуют
        userStorage.findUserById(userId);
        userStorage.findUserById(friendId);

        friendsStorage.addFriend(userId, friendId);
    }

    public void removeFriend(long userId, long friendId) {
        // Проверяем, что пользователи существуют
        userStorage.findUserById(userId);
        userStorage.findUserById(friendId);

        friendsStorage.removeFriend(userId, friendId);
    }

    public Collection<User> getFriends(long userId) {
        // Проверяем, что пользователь существует
        userStorage.findUserById(userId);

        return friendsStorage.getFriends(userId).stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(long userId, long otherId) {
        // Проверяем, что пользователи существуют
        userStorage.findUserById(userId);
        userStorage.findUserById(otherId);

        return friendsStorage.getCommonFriends(userId, otherId).stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }
}