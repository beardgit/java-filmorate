package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        User user = new User(1L, "test@mail.ru", "testuser",
                "Test User", LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Не должно быть нарушений валидации");
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        User user = new User(1L, "invalid-email", "testuser",
                "Test User", LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("должна содержать символ @", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        User user = new User(1L, "", "testuser",
                "Test User", LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("электронная почта не может быть пустой", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenLoginIsBlank() {
        User user = new User(1L, "test@mail.ru", "",
                "Test User", LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("логин не может быть пустым и содержать пробелы", violations.iterator().next().getMessage());
    }

    @Test
    void shouldUseLoginWhenNameIsEmpty() {
        User user = new User(1L, "test@mail.ru", "testuser",
                "", LocalDate.of(1990, 1, 1));

        assertEquals("testuser", user.getName());
    }

    @Test
    void shouldUseLoginWhenNameIsNull() {
        User user = new User(1L, "test@mail.ru", "testuser",
                null, LocalDate.of(1990, 1, 1));

        assertEquals("testuser", user.getName());
    }
}