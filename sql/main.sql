SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE SYS_USER
(
    id       int(11)      not null AUTO_INCREMENT,
    name     varchar(255) unique not null,
    password varchar(255) not null,
    wallet   int(5)       not null default 0,
    primary key (id)
) default charset = utf8;

insert into SYS_USER (name, password)
VALUES ('admin', '123'),
       ('a_user', '123');

DROP TABLE IF EXISTS SYS_ROLE;
create table SYS_ROLE
(
    id   int(11)      not null AUTO_INCREMENT,
    name varchar(255) not null,
    primary key (id)
) default charset = utf8;

insert into SYS_ROLE (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

DROP TABLE IF EXISTS SYS_AUTH;
CREATE TABLE SYS_AUTH
(
    id          int(11)      not null AUTO_INCREMENT,
    name        varchar(255) default null,
    url         varchar(255) not null,
    description varchar(255) default null,
    primary key (id)
) default charset = utf8;

insert into SYS_AUTH (name, url, description)
VALUES ('manage', '/manage', '管理');
insert into SYS_AUTH (name, url, description)
VALUES ('profile', '/profile', '个人资料');
insert into SYS_AUTH (name, url, description)
VALUES ('test', '/test', 'test');

DROP TABLE IF EXISTS SYS_USER_ROLE;
create table SYS_USER_ROLE
(
    user_id int(11) not null,
    role_id int(11) not null,
    primary key (user_id, role_id),
    KEY `kf_role_id_ur` (`role_id`),
    constraint `fk_role_id_ur` foreign key (role_id) references SYS_ROLE (id) on delete cascade on update cascade,
    constraint `fk_user_id_ur` foreign key (user_id) references SYS_USER (id) on delete cascade on update cascade
) default charset = utf8;

insert into SYS_USER_ROLE (user_id, role_id)
VALUES (1, 1),
       (2, 2);

DROP TABLE IF EXISTS SYS_ROLE_AUTH;
CREATE TABLE SYS_ROLE_AUTH
(
    role_id int(11) not null,
    auth_id int(11) not null,
    primary key (role_id, auth_id),
    constraint `fk_role_id_ra` foreign key (role_id) references SYS_ROLE (id) on delete cascade on update cascade,
    constraint `fk_auth_id_ra` foreign key (auth_id) references SYS_AUTH (id) on delete cascade on update cascade
) default charset = utf8;
insert into SYS_ROLE_AUTH (role_id, auth_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3);

DROP TABLE IF EXISTS PERSISTENT_LOGINS;
CREATE TABLE PERSISTENT_LOGINS
(
    username varchar(255) not null ,
    series varchar(64) not null ,
    token varchar(64) not null ,
    last_used timestamp not null default current_timestamp on update current_timestamp,
    primary key (series)
);

DROP TABLE IF EXISTS COURSE;
CREATE TABLE COURSE
(
    id int(11) not null auto_increment,
    name varchar(255) not null ,
    price int(4) not null ,
    primary key (id)
);

insert into COURSE (name, price)
values ('计算机网络', 20),
       ('计算机组成原理', 15),
       ('如何让孩子爱上学习', 100),
       ('Android开发从入门到放弃', 50),
       ('编程从入门到遁入空门', 0);

DROP TABLE IF EXISTS PURCHASE;
CREATE TABLE PURCHASE
(
    id int(20) not null auto_increment,
    user_id int(11) not null ,
    course_id int(11) not null ,
    primary key (id),
    constraint `fk_user_id_purchase` foreign key (user_id) references SYS_USER (id) on delete cascade on update cascade ,
    constraint `fk_course_id_purchase` foreign key (course_id) references COURSE (id) on delete cascade on update cascade
);
