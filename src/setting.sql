alter database autumnia_db;
character set = 'utf8mb4';
collate='utf8mb4_general_ci';

select @@time_zone, now();

create table test (
    id bigint(20) not null auto_increment,
    content varchar(255) default null,
    primary key(id)
) engine=InnoDB;