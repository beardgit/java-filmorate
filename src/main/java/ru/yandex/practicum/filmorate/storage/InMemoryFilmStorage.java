package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> dataFilms = new HashMap<>();


    @Override
    public List<Film> findAll() {
        return dataFilms.values().stream().toList();
    }

    @Override
    public Film create(Film film) {
        long idFilm = getNextId();
        Film storageFilm = new Film(idFilm,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa(),
                film.getGenre());

        dataFilms.put(storageFilm.getId(), storageFilm);
        return storageFilm;
    }

    public Film update(Film film) {
        if (!dataFilms.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        Film updateFilm = new Film(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa(),
                film.getGenre()
        );
        dataFilms.put(updateFilm.getId(), updateFilm);
        return updateFilm;
    }

    public Film findById(long id) {
        if (!dataFilms.containsKey(id)) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return dataFilms.get(id);
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
