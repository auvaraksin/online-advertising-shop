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
    user_id BIGINT REFERENCES users (user_id) NOT NULL,
    ads_comment_id BIGINT,
    ads_image_url VARCHAR (255) NOT NULL,
    ads_price INTEGER NOT NULL,
    ads_title VARCHAR (255) NOT NULL,
    ads_description TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS ads_comments
(
    ads_comments_id   SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (user_id) NOT NULL,
    ads_id BIGINT REFERENCES ads (ads_id) NOT NULL,
    ads_comment_created_time TIMESTAMP NOT NULL,
    ads_comment_text TEXT NOT NULL
    );

-- changeset AUV:2
ALTER TABLE ads ADD CONSTRAINT user_id FOREIGN KEY REFERENCES users (user_id) NOT NULL;
ALTER TABLE ads ADD CONSTRAINT ads_comment_id FOREIGN KEY REFERENCES ads_comments (ads_comment_id) NOT NULL;
ALTER TABLE ads_comments ADD CONSTRAINT user_id FOREIGN KEY REFERENCES users (user_id) NOT NULL;
ALTER TABLE ads_comments ADD CONSTRAINT ads_id FOREIGN KEY REFERENCES ads (ads_id) NOT NULL;