package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImplementation implements GenreService {
    private final GenreDbStorage genreDbStorage;

    @Override
    public List<GenreDto> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }

    @Override
    public GenreDto getGenreById(int id) {
        return genreDbStorage.getGenreById(id);
    }
}