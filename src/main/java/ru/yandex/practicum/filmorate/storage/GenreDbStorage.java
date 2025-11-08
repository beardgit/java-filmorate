package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public List<GenreDto> getAllGenres() {
        String sql = "SELECT * FROM genres ORDER BY id";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    public GenreDto getGenreById(int id) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр с id " + id + " не найден");
        }
    }

    private GenreDto mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        return new GenreDto(rs.getInt("id"), rs.getString("name"));
    }
}