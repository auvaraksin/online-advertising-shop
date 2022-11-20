-- liquibase formatted sql

-- changeset AUV:1

CREATE TABLE IF NOT EXISTS users
(
    user_id   SERIAL PRIMARY KEY,
    user_first_name VARCHAR (255) NOT NULL,
    user_last_name VARCHAR (255) NOT NULL,
    user_email VARCHAR (255) UNIQUE NOT NULL,
    user_phone VARCHAR(11) UNIQUE,
    user_password VARCHAR (255) NOT NULL,
    user_role VARCHAR (5) NOT NULL
    );

CREATE TABLE IF NOT EXISTS ads
(
    ads_id  SERIAL PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES users (user_id) NOT NULL,
    ads_comment_id BIGINT FOREIGN KEY REFERENCES ads_comments (ads_comment_id) NOT NULL,
    ads_image_url VARCHAR (255) NOT NULL,
    ads_price INTEGER NOT NULL,
    ads_title VARCHAR (255) NOT NULL,
    ads_description TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS ads_comments
(
    ads_comments_id   SERIAL PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES users (user_id) NOT NULL,
    ads_id BIGINT FOREIGN KEY REFERENCES ads (ads_id) NOT NULL,
    ads_comment_created_time TIMESTAMP NOT NULL,
    ads_comment_text TEXT NOT NULL
    );