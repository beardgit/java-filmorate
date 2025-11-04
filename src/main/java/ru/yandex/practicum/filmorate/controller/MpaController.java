package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private static final List<MpaDto> MPA_LIST = List.of(
            new MpaDto(1, "G"),
            new MpaDto(2, "PG"),
            new MpaDto(3, "PG-13"),
            new MpaDto(4, "R"),
            new MpaDto(5, "NC-17")
    );

    @GetMapping
    public List<MpaDto> getAllMpa() {
        return MPA_LIST;
    }

    @GetMapping("/{id}")
    public MpaDto getMpaById(@PathVariable int id) {
        return MPA_LIST.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Рейтинг MPA с id " + id + " не найден"));
    }
}
