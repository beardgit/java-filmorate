package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// для проверки связываем с классом ValidatorDateRelease
@Constraint(validatedBy = ValidatorDateRelease.class)
@interface DateValidator {
//    При проврке @Valid  будет браться данное сообщение
    String message() default "Дата релиза должна быть не раньше 28 декабря 1895 года";

//    оставляем пустыми по умолчанию для работы данной аннотации
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}