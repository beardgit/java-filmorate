package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFriendsStorage implements FriendsStorage {
    private final Map<Long, Set<Long>> friendships = new HashMap<>();

    @Override
    public void addFriend(long userId, long friendId) {
        validateIds(userId, friendId);
        friendships.computeIfAbsent(userId, k -> new HashSet<>()).add(friendId);
        friendships.computeIfAbsent(friendId, k -> new HashSet<>()).add(userId);
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
        return friendships.getOrDefault(userId, Collections.emptySet());
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
}
