package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryLikeStorage implements LikeStorage {
    private final Map<Long, Set<Long>> likesData = new HashMap<>();

    @Override
    public void addLike(long filmId, long userId) {
        validateIds(filmId, userId);
        likesData.computeIfAbsent(filmId, k -> new HashSet<>()).add(userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        validateIds(filmId, userId);
        if (likesData.containsKey(filmId)) {
            likesData.get(filmId).remove(userId);
        }
    }

    @Override
    public Set<Long> getLikes(long filmId) {
        if (filmId <= 0) {
            throw new ValidationException("ID фильма должен быть положительным");
        }
        return likesData.getOrDefault(filmId, Collections.emptySet());
    }

    @Override
    public Collection<Long> getFilmIdSortedByLikes(int count) {
        if (count <= 0) {
            throw new ValidationException("Count должен быть положительным");
        }
        return likesData.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .map(Map.Entry::getKey)
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasUserLikedFilm(long filmId, long userId) {
        validateIds(filmId, userId);
        return likesData.containsKey(filmId) && likesData.get(filmId).contains(userId);
    }

    private void validateIds(long filmId, long userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("ID фильма и пользователя должны быть положительными");
        }
    }
}