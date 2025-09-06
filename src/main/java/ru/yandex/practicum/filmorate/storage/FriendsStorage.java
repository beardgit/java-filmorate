package ru.yandex.practicum.filmorate.storage;

import java.util.Set;

public interface FriendsStorage {
    void addFriend(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    Set<Long> getFriends(long userId);

    Set<Long> getCommonFriends(long userId, long otherId);

    boolean areFriends(long userId, long friendId);
}