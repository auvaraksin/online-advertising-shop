-- liquibase formatted sql

-- changeset avasilievaaa:8

CREATE TABLE IF NOT EXISTS users
(
    id bigserial PRIMARY KEY,
    first_name  text,
    last_name   text,
    phone       varchar(16),
    username    text UNIQUE NOT NULL,
    password    text NOT NULL,
    enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS ads
(
    id              bigserial       PRIMARY KEY,
    user_id       bigint          REFERENCES users (id),
    title           text            NOT NULL,
    description     text            NOT NULL,
    price           decimal(17, 2)  NOT NULL CHECK (price > 0::decimal)
    );

CREATE TABLE IF NOT EXISTS images
(
    id              bigserial   PRIMARY KEY,
    id_ads          bigint      REFERENCES ads (id),
    image           bytea       NOT NULL
);

CREATE TABLE IF NOT EXISTS ads_comments
(
    id              bigserial    PRIMARY KEY,
    author_id       bigint       REFERENCES users (id),
    id_ads          bigint       REFERENCES ads (id),
    comment_time    timestamp    NOT NULL,
    comment_text    text         NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    username    text            NOT NULL,
    authority   varchar(16)     NOT NULL,
    CONSTRAINT authorities_unique UNIQUE (username, authority)
);

CREATE TABLE IF NOT EXISTS avatars
(
    id_user     bigint      PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    image       bytea
);

alter table ads_comments
    add foreign key (id_ads) references ads
        on delete cascade;

alter table ads_comments
    add foreign key (author_id) references users
        on delete cascade;


alter table images
    add foreign key (id_ads) references ads
        on delete cascade;

alter table ads
drop constraint ads_user_id_fkey;

alter table ads
    add foreign key (user_id) references users
        on delete cascade;