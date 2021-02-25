create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );

create table if not exists users
(
    id bigint not null
        primary key,
    email varchar(255) null,
    is_writer bit null,
    name varchar(20) null,
    password varchar(255) null,
    username varchar(20) null,
    enabled tinyint default 1 not null,
    constraint users_username_uindex
        unique (username)
);

create table if not exists authorities
(
    id bigint auto_increment
        primary key,
    authority varchar(255) null,
    username varchar(255) null,
    constraint unique_index
        unique (username, authority),
    constraint authorities_users_username_fk
        foreign key (username) references users (username)
);



