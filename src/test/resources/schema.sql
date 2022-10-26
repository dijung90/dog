drop table if exists member cascade;
drop table if exists sns_member cascade;
drop table if exists board_talk cascade;

create table member(
    id varchar(100)  primary key ,
    password varchar(200) not null ,
    nickname varchar(100) not null ,
    dog_name varchar(100) null,
    dog_age int null,
    reg_date timestamp not null,
    last_date timestamp null
);

create table sns_member(
    sns_id varchar(200)  primary key ,
    nickname varchar(100) not null ,
    dog_name varchar(100) null,
    dog_age int null,
    reg_date timestamp not null,
    last_date timestamp null,
    refresh_token varchar(300) not null
);

create table board_talk(
                           no int primary key auto_increment,
                           title varchar(200) not null,
                           writer varchar (100) not null,
                           content varchar(2000) not null,
                           reg_date timestamp not null,
                           update_date timestamp null,
                           file_no int null
);
