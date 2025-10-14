package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceImplementation;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmServiceImplementation filmServiceImplementation) {
        this.filmService = filmServiceImplementation;
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        log.info("Запрос на получение всех фильмов");
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        String messageFormat = String.format("Запрос получения фильма по id: %d", id);
        log.info(messageFormat);
        return filmService.findById(id);
    }

    @PostMapping
    public Film append(@Valid @RequestBody Film film) {
        log.info("Запрос добавление нового фильма: {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        String messageFormat = String.format("Запрос на обновление информации о фильме фильма: %s", film);
        log.info(messageFormat);
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        String messageFormat = String.format("Запрос на добавление лайка у фильма с id: %d, у userId: %d", id, userId);
        log.info(messageFormat);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        String messageFormat = String.format("Запрос на удаление лайка у фильма с id: %d, у userId: %d", id, userId);
        log.info(messageFormat);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(defaultValue = "10") int count) {
        String messageFormat = String.format("Запрос получения популярных фильмов count: %d, по умолчанию 10", count);
        log.info(messageFormat);
        return filmService.getPopularFilms(count);
    }
}