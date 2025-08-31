package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        Film film = new Film(1L, "Valid Film", "Description",
                LocalDate.of(2000, 1, 1), 120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Не должно быть нарушений валидации");
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        Film film = new Film(1L, "", "Description",
                LocalDate.of(2000, 1, 1), 120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Название не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenDescriptionTooLong() {
        String longDescription = "A".repeat(201);
        Film film = new Film(1L, "Film", longDescription,
                LocalDate.of(2000, 1, 1), 120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Максимальная длина описания — 200 символов", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenDurationNotPositive() {
        Film film = new Film(1L, "Film", "Description",
                LocalDate.of(2000, 1, 1), -10);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность фильма должна быть положительным числом", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenDurationIsZero() {
        Film film = new Film(1L, "Film", "Description",
                LocalDate.of(2000, 1, 1), 0);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }
}