package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("likeDbStorage")
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(long filmId, long userId) {
        validateIds(filmId, userId);
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        validateIds(filmId, userId);
        String sql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public Set<Long> getLikes(long filmId) {
        if (filmId <= 0) throw new ValidationException("ID фильма должен быть положительным");
        String sql = "SELECT user_id FROM likes WHERE film_id = ?";
        List<Long> likes = jdbcTemplate.queryForList(sql, Long.class, filmId);
        return new HashSet<>(likes);
    }

    @Override
    public Collection<Long> getFilmIdSortedByLikes(int count) {
        if (count <= 0) throw new ValidationException("Count должен быть положительным");
        String sql = "SELECT film_id FROM likes GROUP BY film_id ORDER BY COUNT(user_id) DESC LIMIT ?";
        return jdbcTemplate.queryForList(sql, Long.class, count);
    }

    @Override
    public boolean hasUserLikedFilm(long filmId, long userId) {
        validateIds(filmId, userId);
        String sql = "SELECT 1 FROM likes WHERE film_id = ? AND user_id = ?";
        return !jdbcTemplate.queryForList(sql, Integer.class, filmId, userId).isEmpty();
    }

    private void validateIds(long filmId, long userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("ID фильма и пользователя должны быть положительными");
        }
    }
}