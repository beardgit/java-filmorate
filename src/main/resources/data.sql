-- Очистка таблиц
DELETE FROM likes;
DELETE FROM film_genres;
DELETE FROM friendships;
DELETE FROM films;
DELETE FROM users;
DELETE FROM genres;
DELETE FROM mpa_ratings;

-- Заполнение таблицы рейтингов
MERGE INTO mpa_ratings (id, name) KEY(id) VALUES
(1, 'G'),
(2, 'PG'),
(3, 'PG-13'),
(4, 'R'),
(5, 'NC-17');

-- Заполнение жанров
MERGE INTO genres (id, name) KEY(id) VALUES
(1, 'Комедия'),
(2, 'Драма'),
(3, 'Мультфильм'),
(4, 'Триллер'),
(5, 'Документальный'),
(6, 'Боевик');
