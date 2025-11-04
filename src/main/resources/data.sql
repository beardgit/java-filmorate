-- Очистка таблиц перед заполнением (опционально)
DELETE FROM likes;
DELETE FROM film_genres;
DELETE FROM friendships;
DELETE FROM films;
DELETE FROM users;
DELETE FROM genres;
DELETE FROM mpa_ratings;

-- Заполнение MPA рейтингов
MERGE INTO mpa_ratings (mpa_id, name, description) KEY(mpa_id) VALUES
(1, 'G', 'Нет возрастных ограничений'),
(2, 'PG', 'Детям рекомендуется смотреть с родителями'),
(3, 'PG-13', 'Детям до 13 лет просмотр не желателен'),
(4, 'R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
(5, 'NC-17', 'Лицам до 18 лет просмотр запрещён');

-- Заполнение жанров
MERGE INTO genres (genre_id, name) KEY(genre_id) VALUES
(1, 'Комедия'),
(2, 'Драма'),
(3, 'Мультфильм'),
(4, 'Триллер'),
(5, 'Документальный'),
(6, 'Боевик');