create table member
(
    id          bigint          not null    auto_increment      comment 'id',
    email       varchar(100)    not null    comment 'email',
    password    varchar(70)     not null    comment 'password',
    authorities varchar(400)    not null    comment 'authorities',
    primary key (id)
) engine=InnoDB;
