package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> dataFilms = new HashMap<>();

    //    получение всех фильмов.
    @GetMapping
    public Collection<Film> findAllFilms() {
        return dataFilms.values();
    }

    //    добавление фильма;
    @PostMapping
    public Film appendFilm(@Valid @RequestBody Film film) {
        log.info("Получен аргумент film для добавления: {}", film);
        long idFilm = getNextId();
        Film storageFilm = new Film(idFilm,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
        log.info("Создана сущность storageFilm: {}", storageFilm);

        dataFilms.put(storageFilm.getId(), storageFilm);
        return storageFilm;
    }

    //    обновление фильма;
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен аргумент film для обновления: {}", film);
        if (!dataFilms.containsKey(film.getId())) {
            throw new ValidationException("Фильм с id " + film.getId() + " не найден");
        }
        Film updateFilm = new Film(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration()
        );
        log.info("Создана сущность updateFilm: {}", updateFilm);

        dataFilms.put(updateFilm.getId(), updateFilm);
        return updateFilm;

    }

    private long getNextId() {
        long currentMaxId = dataFilms.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
