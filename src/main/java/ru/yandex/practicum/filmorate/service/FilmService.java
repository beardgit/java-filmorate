package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmCreateRequestDto;
import ru.yandex.practicum.filmorate.dto.FilmResponseDto;

import java.util.List;

public interface FilmService {
    List<FilmResponseDto> findAll();
    FilmResponseDto create(FilmCreateRequestDto film);
    FilmResponseDto update(FilmCreateRequestDto film);
    FilmResponseDto findById(long id);
    void addLike(long filmId, long userId);
    void removeLike(long filmId, long userId);
    List<FilmResponseDto> getPopularFilms(int count);
    FilmResponseDto removeFilmById(long id);
}
