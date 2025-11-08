package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public UserServiceImplementation(@Qualifier("userDbStorage") UserStorage userStorage,
                                     @Qualifier("friendsDbStorage") FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User create(User user) {
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        return userStorage.update(user);
    }

    @Override
    public User findUserById(long id) {
        return userStorage.findUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    @Override
    public void addFriend(long userId, long friendId) {
        findUserById(userId);
        findUserById(friendId);
        friendsStorage.addFriend(userId, friendId);
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        findUserById(userId);
        findUserById(friendId);
        friendsStorage.removeFriend(userId, friendId);
    }

    @Override
    public Collection<User> getFriends(long userId) {
        findUserById(userId);
        return friendsStorage.getFriends(userId).stream()
                .map(userStorage::findUserById)
                .map(opt -> opt.orElseThrow(() -> new NotFoundException("Пользователь не найден")))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        findUserById(userId);
        findUserById(otherId);
        return friendsStorage.getCommonFriends(userId, otherId).stream()
                .map(userStorage::findUserById)
                .map(opt -> opt.orElseThrow(() -> new NotFoundException("Пользователь не найден")))
                .collect(Collectors.toList());
    }
}