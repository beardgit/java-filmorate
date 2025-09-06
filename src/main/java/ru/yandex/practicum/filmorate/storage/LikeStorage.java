package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.Set;

public interface LikeStorage {
    void addLike(long filmId, long userId);

    void removeLike(long filmId, long userId);

    Set<Long> getLikes(long filmId);

    Collection<Long> getFilmIdSortedByLikes(int count);

    boolean hasUserLikedFilm(long filmId, long userId);
}