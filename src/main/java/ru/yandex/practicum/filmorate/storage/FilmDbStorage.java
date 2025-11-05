package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmCreateRequestDto;
import ru.yandex.practicum.filmorate.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component("filmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FilmResponseDto> findAll() {
        List<Long> filmIds = jdbcTemplate.queryForList("SELECT id FROM films", Long.class);
        return filmIds.stream().map(this::findById).collect(Collectors.toList());
    }

    @Override
    public FilmResponseDto create(FilmCreateRequestDto filmDto) {
        if (filmDto.getMpa() == null) {
            filmDto.setMpa(new MpaDto(1, "G"));
        }
        validateMpaAndGenres(filmDto);

        String sql = "INSERT INTO films (name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, filmDto.getName());
            ps.setString(2, filmDto.getDescription());
            ps.setDate(3, Date.valueOf(filmDto.getReleaseDate()));
            ps.setInt(4, filmDto.getDuration());
            ps.setInt(5, filmDto.getMpa().getId());
            return ps;
        }, keyHolder);

        long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        if (filmDto.getGenres() != null && !filmDto.getGenres().isEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                    filmDto.getGenres().stream()
                            .map(genre -> new Object[]{filmId, genre.getId()})
                            .collect(Collectors.toList()));
        }

        return findById(filmId);
    }

    @Override
    public FilmResponseDto update(FilmCreateRequestDto filmDto) {
        validateMpaAndGenres(filmDto);

        if (filmDto.getId() == null) {
            throw new ValidationException("ID фильма не указан");
        }

        long filmId = filmDto.getId();

        String sql = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql,
                filmDto.getName(),
                filmDto.getDescription(),
                Date.valueOf(filmDto.getReleaseDate()),
                filmDto.getDuration(),
                filmDto.getMpa().getId(),
                filmId
        );

        if (updated == 0) throw new NotFoundException("Фильм не найден");

        // Update genres
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?", filmId);
        if (filmDto.getGenres() != null && !filmDto.getGenres().isEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                    filmDto.getGenres().stream()
                            .map(genre -> new Object[]{filmId, genre.getId()})
                            .collect(Collectors.toList()));
        }

        return findById(filmId);
    }

    @Override
    public FilmResponseDto findById(long id) {
        String sql = "SELECT f.*, m.name as mpa_name FROM films f JOIN mpa_ratings m ON f.mpa_id = m.id WHERE f.id = ?";
        try {
            FilmResponseDto film = jdbcTemplate.queryForObject(sql, this::mapRowToFilmResponse, id);
            if (film == null) throw new NotFoundException("Фильм не найден");
            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильм не найден");
        }
    }

    @Override
    public FilmResponseDto removeFilmById(long id) {
        FilmResponseDto film = findById(id);
        jdbcTemplate.update("DELETE FROM films WHERE id = ?", id);
        return film;
    }

    private FilmResponseDto mapRowToFilmResponse(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        MpaDto mpa = new MpaDto(rs.getInt("mpa_id"), rs.getString("mpa_name"));
        Set<GenreDto> genres = getGenresByFilmId(id);
        return new FilmResponseDto(
                id,
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                mpa,
                genres
        );
    }

    private Set<GenreDto> getGenresByFilmId(long filmId) {
        String sql = "SELECT g.id, g.name FROM film_genres fg JOIN genres g ON fg.genre_id = g.id WHERE fg.film_id = ?";
        List<GenreDto> genres = jdbcTemplate.query(sql, (rs, rn) -> new GenreDto(rs.getInt("id"), rs.getString("name")), filmId);
        return new LinkedHashSet<>(genres);
    }

    private void validateMpaAndGenres(FilmCreateRequestDto filmDto) {
        if (filmDto.getMpa() != null) {
            Integer mpaId = filmDto.getMpa().getId();
            if (!Arrays.asList(1, 2, 3, 4, 5).contains(mpaId)) {
                throw new NotFoundException("MPA с id=" + mpaId + " не найден");
            }
        }
        if (filmDto.getGenres() != null) {
            for (GenreDto genre : filmDto.getGenres()) {
                if (genre.getId() < 1 || genre.getId() > 6) {
                    throw new NotFoundException("Жанр с id=" + genre.getId() + " не найден");
                }
            }
        }
    }
}