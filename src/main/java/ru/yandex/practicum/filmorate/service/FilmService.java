package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film findById(long id) {
        return filmStorage.findById(id);
    }

    public void addLike(long filmId, long userId) {
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        likeStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return likeStorage.getFilmIdSortedByLikes(count).stream()
                .map(filmStorage::findById)
                .collect(Collectors.toList());
    }
}
