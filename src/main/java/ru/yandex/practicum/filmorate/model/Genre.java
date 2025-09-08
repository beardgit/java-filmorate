package ru.yandex.practicum.filmorate.model;

/*
У фильма может быть сразу несколько жанров,
 а у поля — несколько значений. Например, таких:

Комедия.
Драма.
Мультфильм.
Триллер.
Документальный.
Боевик.
*/

public enum Genre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    CARTOON("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
