package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFriendsStorage implements FriendsStorage {
    private final Map<Long, Map<Long, FriendshipStatus>> friendships = new HashMap<>();

    @Override
    public void addFriend(long userId, long friendId) {
        validateIds(userId, friendId);
        // запрос от user к friend
        friendships
                .computeIfAbsent(userId, k -> new HashMap<>())
                .put(friendId, FriendshipStatus.UNCONFIRMED);

        // Если friendID уже отправлял запрос то подтверждаем
        if (hasIncomingRequest(friendId, userId)) {
            confirmFriend(userId, friendId);
            confirmFriend(friendId, userId);
        }
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        validateIds(userId, friendId);
        if (friendships.containsKey(userId)) {
            friendships.get(userId).remove(friendId);
        }
        if (friendships.containsKey(friendId)) {
            friendships.get(friendId).remove(userId);
        }
    }

    @Override
    public Set<Long> getFriends(long userId) {
        if (userId <= 0) {
            throw new ValidationException("ID пользователя должен быть положительным");
        }
        return friendships.getOrDefault(userId, Collections.emptyMap()).entrySet().stream()
                .filter(e -> e.getValue() == FriendshipStatus.CONFIRMED)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getCommonFriends(long userId, long otherId) {
        validateIds(userId, otherId);
        Set<Long> userFriends = getFriends(userId);
        Set<Long> otherFriends = getFriends(otherId);

        return userFriends.stream()
                .filter(otherFriends::contains)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean areFriends(long userId, long friendId) {
        validateIds(userId, friendId);
        return getFriends(userId).contains(friendId);
    }

    private void validateIds(long userId, long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ValidationException("ID пользователей должны быть положительными");
        }
        if (userId == friendId) {
            throw new ValidationException("Пользователь не может быть другом сам себе");
        }
    }

    // Подтверждаем дружбу
    public void confirmFriend(long userId, long friendId) {
        validateIds(userId, friendId);
        if (hasIncomingRequest(friendId, userId)) {
            friendships.get(friendId).put(userId, FriendshipStatus.CONFIRMED);
            friendships.get(userId).put(friendId, FriendshipStatus.CONFIRMED);
        }
    }

    // Проверяем был ли входящий запрос
    private boolean hasIncomingRequest(long userId, long fromId) {
        return friendships.containsKey(userId)
                && friendships.get(userId).getOrDefault(fromId, null) == FriendshipStatus.UNCONFIRMED;
    }

}
