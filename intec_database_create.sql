create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table user (dtype varchar(31) not null, id bigint not null, confirm_password varchar(255) not null, email varchar(255), first_name varchar(255), is_something bit, last_name varchar(255), name varchar(255), password varchar(255), can_write bit, primary key (id)) engine=InnoDB;
