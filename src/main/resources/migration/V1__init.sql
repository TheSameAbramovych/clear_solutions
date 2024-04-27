create table if not exists users
(
    id           varchar(255) not null primary key,
    email        varchar(255) not null unique,
    first_name   varchar(255) not null,
    second_name  varchar(255) not null,
    phone_number varchar(255),
    birthday     date         not null
);

create table if not exists address
(
    id        varchar(255) not null primary key,
    line1     varchar(255) not null,
    line2     varchar(255) not null,
    line3     varchar(255) not null,
    post_code varchar(255) not null,
    user_id   varchar(255) not null,
    constraint foreign key (user_id) references users (id) ON DELETE CASCADE
);

insert into users
values ("a", 'test@test.com', 'Bohdan', 'God', '+380971829431', '1997-01-01'),
       ("s", 'tesst@test.com', 'test', 'test', '+380971829431', '1999-01-01'),
       ("sd", 'tessdt@test.com', 'test', 'test', '+380971829431', '2003-01-01'),
       ("ds", 'testsdds@test.com', 'test', 'test', '+380971829431', '2005-01-01'),
       ("dsf", 'tesdsvt@test.com', 'test', 'test', '+380971829431', '1994-01-01'),
       ("dsa", 'testsdv@test.com', 'test', 'test', '+380971829431', '1990-01-01'),
       ("sdfg", 'testsdsv@test.com', 'test', 'test', '+380971829431', '2000-01-01');

insert into address
values ("sas", "tds", "tds", "tds", "tds", "a"),
       ("sasqwdd", "tds", "tds", "tds", "tds", "s"),
       ("sadss", "tds", "tds", "tds", "tds", "sd"),
       ("saqwds", "tds", "tds", "tds", "tds", "ds"),
       ("saasds", "tds", "tds", "tds", "tds", "dsf"),
       ("asdas", "tds", "tds", "tds", "tds", "dsa")


