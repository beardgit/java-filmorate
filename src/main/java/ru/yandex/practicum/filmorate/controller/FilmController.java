package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmCreateRequestDto;
import ru.yandex.practicum.filmorate.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<FilmResponseDto> findAllFilms() {
        return filmService.findAll();
    }

    @DeleteMapping("/{id}")
    public FilmResponseDto deleteFilm(@PathVariable long id) {
        return filmService.removeFilmById(id);
    }

    @GetMapping("/{id}")
    public FilmResponseDto getFilm(@PathVariable long id) {
        return filmService.findById(id);
    }

    @PostMapping
    public FilmResponseDto append(@Valid @RequestBody FilmCreateRequestDto film) {
        return filmService.create(film);
    }

    @PutMapping
    public FilmResponseDto update(@Valid @RequestBody FilmCreateRequestDto film) {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<FilmResponseDto> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }
}
