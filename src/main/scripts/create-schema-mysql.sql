create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists users
(
    id bigint not null
        primary key,
    email varchar(255) null,
    enabled bit null,
    password varchar(255) null,
    is_writer bit null,
    name varchar(255) null,
    username varchar(255) null,
    constraint users_username_uindex
        unique (username)
);

create table if not exists authorities
(
    id bigint not null
        primary key,
    authority varchar(255) null,
    username varchar(255) null,
    constraint fk_authorities_users
        foreign key (username) references users (username)
);

create table if not exists verification_token
(
    id bigint not null
        primary key,
    expiry_date datetime null,
    token varchar(255) null,
    user_id bigint null,
    constraint fk_veriftoken_users
        foreign key (user_id) references users (id)
);

