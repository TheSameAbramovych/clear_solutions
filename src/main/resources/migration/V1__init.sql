create table if not exists coca_cola_account
(
    id   bigint       not null primary key auto_increment,
    name varchar(255) null
);

create table if not exists file
(
    id         bigint       not null primary key auto_increment,
    content    longblob     null,
    createTime datetime(6)  null,
    type       varchar(32)  null,
    name       varchar(255) null
);

create table if not exists route
(
    id   bigint       not null primary key auto_increment,
    name varchar(255) null unique
);

create table if not exists coca_cola_accounts_routes
(
    coca_cola_account_id bigint not null,
    routes_id            bigint not null,
    constraint foreign key (coca_cola_account_id) references coca_cola_account (id),
    constraint foreign key (routes_id) references route (id)
);

create table if not exists team
(
    id   bigint       not null primary key auto_increment,
    name varchar(255) null
);

create table if not exists coca_cola_accounts_teams
(
    coca_cola_account_id bigint not null,
    teams_id             bigint not null,
    constraint foreign key (teams_id) references team (id),
    constraint foreign key (coca_cola_account_id) references coca_cola_account (id)
);

create table if not exists telegram_profile
(
    id                   bigint not null primary key,
    coca_cola_account_id bigint null,
    constraint foreign key (coca_cola_account_id) references coca_cola_account (id)
);


insert into route value (1, 'Н1');
insert into route value (2, 'Н2');
insert into route value (3, 'Н3');
insert into route value (4, 'Н4');
insert into route value (5, 'Н5');
insert into route value (6, 'Н6');
insert into route value (7, 'В1');
insert into route value (8, 'В2');
insert into route value (9, 'В3');
insert into route value (10, 'В4');
insert into route value (11, 'В5');
insert into route value (12, 'В6');
insert into route value (13, 'В7');
insert into route value (14, 'В8');

insert into team value (1, 'B39');
insert into team value (2, 'B40');

insert into coca_cola_account value (1, 'Топ Одмен');
insert into coca_cola_account value (2, 'Одмен');
insert into coca_cola_account value (3, 'Деня');
insert into coca_cola_account value (4, 'Лисюк Укрлан');
insert into coca_cola_account value (5, 'Вова');
insert into coca_cola_account value (6, 'Женя');
insert into coca_cola_account value (7, 'Тоха');
insert into coca_cola_account value (8, 'Артурчик');
insert into coca_cola_account value (9, 'Ванька');
insert into coca_cola_account value (10, 'Саня');
insert into coca_cola_account value (11, 'Макс Савчеенко');
insert into coca_cola_account value (12, 'Заруцький Укрлан');
insert into coca_cola_account value (13, 'Віталій Барановський');
insert into coca_cola_account value (14, 'Найкращий');

insert into telegram_profile value (2113256088, 1);
insert into telegram_profile value (487337049, 2);
insert into telegram_profile value (383271914, 3);
insert into telegram_profile value (939500617, 4);
insert into telegram_profile value (5175286396, 5);
insert into telegram_profile value (1347243539, 6);
insert into telegram_profile value (449476329, 7);
insert into telegram_profile value (573979571, 8);
insert into telegram_profile value (5270198258, 9);
insert into telegram_profile value (470817769, 10);
insert into telegram_profile value (2012532630, 10);
insert into telegram_profile value (5047462823, 11);
insert into telegram_profile value (5262665451, 12);
insert into telegram_profile value (5164601106, 13);
insert into telegram_profile value (5222532560, 14);

insert into coca_cola_accounts_routes value (1, 3);
insert into coca_cola_accounts_routes value (2, 3);
insert into coca_cola_accounts_routes value (3, 6);
insert into coca_cola_accounts_routes value (4, 5);
insert into coca_cola_accounts_routes value (5, 1);
insert into coca_cola_accounts_routes value (5, 2);
insert into coca_cola_accounts_routes value (5, 4);
insert into coca_cola_accounts_routes value (5, 5);
insert into coca_cola_accounts_routes value (5, 6);
insert into coca_cola_accounts_routes value (5, 8);
insert into coca_cola_accounts_routes value (6, 3);
insert into coca_cola_accounts_routes value (6, 7);
insert into coca_cola_accounts_routes value (6, 11);
insert into coca_cola_accounts_routes value (6, 13);
insert into coca_cola_accounts_routes value (6, 14);
insert into coca_cola_accounts_routes value (7, 13);
insert into coca_cola_accounts_routes value (8, 9);
insert into coca_cola_accounts_routes value (9, 4);
insert into coca_cola_accounts_routes value (10, 8);
insert into coca_cola_accounts_routes value (11, 2);
insert into coca_cola_accounts_routes value (12, 10);
insert into coca_cola_accounts_routes value (14, 7);

insert into coca_cola_accounts_teams value (1, 1);
insert into coca_cola_accounts_teams value (2, 1);
insert into coca_cola_accounts_teams value (3, 2);
insert into coca_cola_accounts_teams value (4, 2);
insert into coca_cola_accounts_teams value (5, 2);
insert into coca_cola_accounts_teams value (6, 1);
insert into coca_cola_accounts_teams value (7, 1);
insert into coca_cola_accounts_teams value (8, 1);
insert into coca_cola_accounts_teams value (8, 2);
insert into coca_cola_accounts_teams value (9, 2);
insert into coca_cola_accounts_teams value (10, 2);
insert into coca_cola_accounts_teams value (11, 2);
insert into coca_cola_accounts_teams value (12, 2);
insert into coca_cola_accounts_teams value (12, 1);
insert into coca_cola_accounts_teams value (13, 2);
insert into coca_cola_accounts_teams value (14, 1);


