create schema jes_test;

alter schema jes_test owner to postgres;

create table if not exists tags
(
  id bigserial not null
    constraint tags_pk
      primary key,
  name varchar not null
);

alter table tags owner to postgres;

create unique index if not exists tags_name_uindex
  on tags (name);

create table if not exists certificates
(
  id bigserial not null
    constraint certificates_pk
      primary key,
  name varchar not null,
  description varchar not null,
  price numeric(12,2) not null,
  creation_date date not null,
  modification_date date,
  duration integer not null
);

alter table certificates owner to postgres;

create table if not exists certificates_tags
(
  certificate_id bigint not null
    constraint certificates_tags_certificates_id_fk
      references certificates
      on update cascade on delete cascade,
  tag_id bigint not null
    constraint certificates_tags_tags_id_fk
      references tags
      on update cascade on delete cascade
);

alter table certificates_tags owner to postgres;