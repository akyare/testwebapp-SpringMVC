create table authorities (id bigint not null, authority varchar(255), username varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table users (id bigint not null, email varchar(255), password varchar(255), is_writer bit, name varchar(255), username varchar(255), primary key (id)) engine=InnoDB
alter table authorities add constraint FKhjuy9y4fd8v5m3klig05ktofg foreign key (username) references users (id)
