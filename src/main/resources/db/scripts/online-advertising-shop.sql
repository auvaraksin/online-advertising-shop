-- liquibase formatted sql

CREATE TABLE users
(
    id bigserial PRIMARY KEY,
    first_name  text,
    last_name   text,
    phone       varchar(16),
    username    text UNIQUE NOT NULL,
    password    text NOT NULL,
    enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE ads
(
    id              bigserial       PRIMARY KEY,
    id_author       bigint          REFERENCES users (id),
    title           text            NOT NULL,
    description     text            NOT NULL,
    price           decimal(17, 2)  NOT NULL CHECK (price > 0::decimal)
    );

CREATE TABLE ads_images
(
    id              bigserial   PRIMARY KEY,
    id_ads          bigint      REFERENCES ads (id),
    image           bytea       NOT NULL
);

CREATE TABLE ads_comments
(
    id              bigserial    PRIMARY KEY,
    id_author       bigint       REFERENCES users (id),
    id_ads          bigint       REFERENCES ads (id),
    comment_time    timestamp    NOT NULL,
    comment_text    text         NOT NULL
);

CREATE TABLE authorities
(
    username    text            NOT NULL,
    authority   varchar(16)     NOT NULL,
    CONSTRAINT authorities_unique UNIQUE (username, authority)
);

CREATE TABLE avatars
(
    id_user     bigint      PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    image       bytea
);

alter table ads_comments
drop constraint ads_comments_id_ads_fkey;

alter table ads_comments
    add foreign key (id_ads) references ads
        on delete cascade;

alter table ads_comments
drop constraint ads_comments_id_author_fkey;

alter table ads_comments
    add foreign key (id_author) references users
        on delete cascade;

alter table ads_images
drop constraint ads_images_id_ads_fkey;

alter table ads_images
    add foreign key (id_ads) references ads
        on delete cascade;

alter table ads
drop constraint ads_id_author_fkey;

alter table ads
    add foreign key (id_author) references users
        on delete cascade;