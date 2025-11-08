package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public List<MpaDto> getAllMpa() {
        String sql = "SELECT * FROM mpa_ratings ORDER BY id";
        return jdbcTemplate.query(sql, this::mapRowToMpa);
    }

    public MpaDto getMpaById(int id) {
        String sql = "SELECT * FROM mpa_ratings WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Рейтинг MPA с id " + id + " не найден");
        }
    }

    private MpaDto mapRowToMpa(ResultSet rs, int rowNum) throws SQLException {
        return new MpaDto(rs.getInt("id"), rs.getString("name"));
    }
}