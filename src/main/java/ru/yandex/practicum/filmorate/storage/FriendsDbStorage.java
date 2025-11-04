package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("friendsDbStorage")
@RequiredArgsConstructor
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(long userId, long friendId) {
        validateIds(userId, friendId);
        String sql = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        validateIds(userId, friendId);
        String sql = "DELETE FROM friendships WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public Set<Long> getFriends(long userId) {
        if (userId <= 0) throw new ValidationException("ID пользователя должен быть положительным");
        String sql = "SELECT friend_id FROM friendships WHERE user_id = ?";
        List<Long> friends = jdbcTemplate.queryForList(sql, Long.class, userId);
        return new HashSet<>(friends);
    }

    @Override
    public Set<Long> getCommonFriends(long userId, long otherId) {
        validateIds(userId, otherId);
        String sql = """
            SELECT f1.friend_id
            FROM friendships f1
            INNER JOIN friendships f2 ON f1.friend_id = f2.friend_id
            WHERE f1.user_id = ? AND f2.user_id = ?
        """;
        List<Long> common = jdbcTemplate.queryForList(sql, Long.class, userId, otherId);
        return new HashSet<>(common);
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
