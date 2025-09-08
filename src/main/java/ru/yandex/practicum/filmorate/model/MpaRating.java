package ru.yandex.practicum.filmorate.model;

/*
G — у фильма нет возрастных ограничений,
PG — детям рекомендуется смотреть фильм с родителями,
PG-13 — детям до 13 лет просмотр не желателен,
R — лицам до 17 лет просматривать фильм можно только в присутствии взрослого,
NC-17 — лицам до 18 лет просмотр запрещён.
*/

public enum MpaRating {
    G("G", "Нет возрастных ограничений"),
    PG("PG", "детям рекомендуется смотреть фильм с родителями"),
    PG13("PG-13", "детям до 13 лет просмотр не желателен"),
    R("R", "лицам до 17 лет просматривать фильм можно только в присутствии взрослого"),
    NC17("NC-17", "лицам до 18 лет просмотр запрещён");

    private final String code;
    private final String description;

    MpaRating(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

