-- liquibase formatted sql

-- changeset AUV:1

CREATE TABLE IF NOT EXISTS users
(
    id   SERIAL PRIMARY KEY,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    username VARCHAR (255) UNIQUE NOT NULL,
    phone VARCHAR(12) UNIQUE,
    password VARCHAR (255) NOT NULL,
    role VARCHAR (5) NOT NULL
    );

CREATE TABLE IF NOT EXISTS ads
(
    id  SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id) NOT NULL,
    ads_comment_id BIGINT NOT NULL,
    image_url VARCHAR (255) NOT NULL,
    price INTEGER NOT NULL,
    title VARCHAR (255) NOT NULL,
    description TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS ads_comments
(
    id   SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id) NOT NULL,
    ads_id BIGINT REFERENCES ads (id) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    text TEXT NOT NULL
    );

ALTER TABLE ads
    ADD FOREIGN KEY (ads_comment_id) REFERENCES ads_comments (id);