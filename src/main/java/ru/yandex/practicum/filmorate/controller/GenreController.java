package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private static final List<GenreDto> GENRE_LIST = List.of(
            new GenreDto(1, "Комедия"),
            new GenreDto(2, "Драма"),
            new GenreDto(3, "Мультфильм"),
            new GenreDto(4, "Триллер"),
            new GenreDto(5, "Документальный"),
            new GenreDto(6, "Боевик")
    );

    @GetMapping
    public List<GenreDto> getAllGenres() {
        return GENRE_LIST;
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable int id) {
        return GENRE_LIST.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Жанр с id " + id + " не найден"));
    }
}
