-- Создание таблицы MPA рейтингов
CREATE TABLE IF NOT EXISTS mpa_ratings (
    mpa_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Создание таблицы жанров
CREATE TABLE IF NOT EXISTS genres (
    genre_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Создание таблицы фильмов
CREATE TABLE IF NOT EXISTS films (
    film_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    release_date DATE,
    duration INTEGER,
    mpa_id INTEGER,
    FOREIGN KEY (mpa_id) REFERENCES mpa_ratings(mpa_id)
);

-- Создание связи фильмов и жанров
CREATE TABLE IF NOT EXISTS film_genres (
    film_id INTEGER,
    genre_id INTEGER,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    birthday DATE
);

-- Создание таблицы дружбы
CREATE TABLE IF NOT EXISTS friendships (
    user_id INTEGER,
    friend_id INTEGER,
    status VARCHAR(20) DEFAULT 'UNCONFIRMED',
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Создание таблицы лайков
CREATE TABLE IF NOT EXISTS likes (
    film_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);