create table user
(
    id                 bigint auto_increment primary key,
    username           varchar(100) unique,
    encrypted_password varchar(100),
    avatar             varchar(200),
    created_at         datetime default now(),
    updated_at         datetime default now()
);