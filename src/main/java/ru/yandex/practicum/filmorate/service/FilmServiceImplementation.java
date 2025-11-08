package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmCreateRequestDto;
import ru.yandex.practicum.filmorate.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImplementation implements FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;

    public FilmServiceImplementation(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                                     @Qualifier("likeDbStorage") LikeStorage likeStorage,
                                     @Qualifier("userDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
    }

    @Override
    public List<FilmResponseDto> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public FilmResponseDto create(FilmCreateRequestDto film) {
        return filmStorage.create(film);
    }

    @Override
    public FilmResponseDto update(FilmCreateRequestDto film) {
        return filmStorage.update(film);
    }

    @Override
    public FilmResponseDto findById(long id) {
        return filmStorage.findById(id);
    }

    @Override
    public void addLike(long filmId, long userId) {
        filmStorage.findById(filmId);
        userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        likeStorage.addLike(filmId, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        filmStorage.findById(filmId);
        userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        likeStorage.removeLike(filmId, userId);
    }

    @Override
    public List<FilmResponseDto> getPopularFilms(int count) {
        return likeStorage.getFilmIdSortedByLikes(count).stream()
                .map(filmStorage::findById)
                .collect(Collectors.toList());
    }

    @Override
    public FilmResponseDto removeFilmById(long id) {
        return filmStorage.removeFilmById(id);
    }
}
