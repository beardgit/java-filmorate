package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImplementation implements FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage; // Добавляем зависимость

    @Autowired
    public FilmServiceImplementation(FilmStorage filmStorage, LikeStorage likeStorage, UserStorage userStorage) {
        log.info("В FilmServiceImplements Инициализирован filmStorage, likeStorage, userStorage");
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
    }


    public Film removeFilmById(long id) {
        return filmStorage.removeFilmById(id);
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
        String messageInfo = String.format("В FilmServiceImplementation вызов добавления по filmId: %d и userId: %d", filmId, userId);
        log.info(messageInfo);
        filmStorage.findById(filmId);
        userStorage.findUserById(userId);
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        String messageInfo = String.format("В FilmServiceImplementation вызов удаления по filmId: %d и userId: %d", filmId, userId);
        log.info(messageInfo);
        filmStorage.findById(filmId);
        userStorage.findUserById(userId);
        likeStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        String messageInfo = String.format("В FilmServiceImplementation вызов метода получения популярных фильмов %d", count);
        log.info(messageInfo);
        return likeStorage.getFilmIdSortedByLikes(count).stream().map(filmStorage::findById).collect(Collectors.toList());
    }
}