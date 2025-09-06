package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
// данный клас будет реализовывать интерфейс ConstraintValidator для работы с аннотацией DateValidator  и проверки значений типа LocalDate
public class ValidatorDateRelease implements ConstraintValidator<DateValidator, LocalDate> {
    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return !localDate.isBefore(EARLIEST_DATE);
    }
}
