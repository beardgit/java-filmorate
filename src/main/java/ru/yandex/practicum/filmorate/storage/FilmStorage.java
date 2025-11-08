package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.dto.FilmCreateRequestDto;
import ru.yandex.practicum.filmorate.dto.FilmResponseDto;

import java.util.List;

public interface FilmStorage {
    List<FilmResponseDto> findAll();

    FilmResponseDto create(FilmCreateRequestDto film);

    FilmResponseDto update(FilmCreateRequestDto film);

    FilmResponseDto findById(long id);

    FilmResponseDto removeFilmById(long id);
}