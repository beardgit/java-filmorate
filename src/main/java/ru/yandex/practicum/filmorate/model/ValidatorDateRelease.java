package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidatorDateRelease implements ConstraintValidator<DateValidator, LocalDate> {
    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate != null && !localDate.isBefore(EARLIEST_DATE);
    }
}