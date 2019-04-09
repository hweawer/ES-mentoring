create schema jes_test;

alter schema jes_test owner to postgres;

create table if not exists jes_test.tags
(
  id bigserial
    constraint tags_pk
      primary key,
  name varchar not null
);

alter table jes_test.tags owner to postgres;

create unique index if not exists tags_name_uindex
  on jes_test.tags (name);

create table if not exists jes_test.certificates
(
  id bigserial
    constraint certificates_pk
      primary key,
  name varchar not null,
  description varchar not null,
  price numeric(12,2) not null,
  creation_date date not null,
  modification_date date,
  duration integer not null
);

alter table jes_test.certificates owner to postgres;

create table if not exists jes_test.certificates_tags
(
  certificate_id bigint not null
    constraint certificates_tags_certificates_id_fk
      references jes_test.certificates
      on update cascade on delete cascade,
  tag_id bigint not null
    constraint certificates_tags_tags_id_fk
      references jes_test.tags
      on update cascade on delete cascade
);

alter table jes_test.certificates_tags owner to postgres;

create schema jes_dev;

alter schema jes_dev owner to postgres;

create table if not exists jes_dev.tags
(
  id bigserial
    constraint tags_pk
      primary key,
  name varchar not null
);

alter table jes_dev.tags owner to postgres;

create unique index if not exists tags_name_uindex
  on jes_dev.tags (name);

create table if not exists jes_dev.certificates
(
  id bigserial
    constraint certificates_pk
      primary key,
  name varchar not null,
  description varchar not null,
  price numeric(12,2) not null,
  creation_date date not null,
  modification_date date,
  duration integer not null
);

alter table jes_dev.certificates owner to postgres;

create table if not exists jes_dev.certificates_tags
(
  certificate_id bigint not null
    constraint certificates_tags_certificates_id_fk
      references jes_dev.certificates
      on update cascade on delete cascade,
  tag_id bigint not null
    constraint certificates_tags_tags_id_fk
      references jes_dev.tags
      on update cascade on delete cascade
);

alter table jes_dev.certificates_tags owner to postgres;