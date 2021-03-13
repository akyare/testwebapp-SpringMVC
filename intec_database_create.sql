
    create table authorities (
       id bigint not null,
        authority varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;

    insert into hibernate_sequence values ( 1 );

    create table user_authority (
       user_id bigint not null,
        auth_id bigint not null
    ) engine=InnoDB;

    create table users (
       id bigint not null,
        email varchar(255),
        enabled bit,
        password varchar(255),
        is_writer bit,
        name varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table verification_token (
       id bigint not null,
        expiry_date datetime,
        token varchar(255),
        user_id bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table user_authority 
       add constraint FKlw8vjvnr05ipx4u5c6m3ypxoq 
       foreign key (auth_id) 
       references authorities (id);

    alter table user_authority 
       add constraint FKhi46vu7680y1hwvmnnuh4cybx 
       foreign key (user_id) 
       references users (id);

    alter table verification_token 
       add constraint FK3asw9wnv76uxu3kr1ekq4i1ld 
       foreign key (user_id) 
       references users (id);
