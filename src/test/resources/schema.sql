drop table if exists member cascade;
drop table if exists sns_member cascade;

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