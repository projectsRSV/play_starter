# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product_my (
  id                            bigserial not null,
  brand                         varchar(255),
  model                         varchar(255),
  article                       varchar(255),
  release_date                  timestamptz,
  constraint pk_product_my primary key (id)
);


# --- !Downs

drop table if exists product_my cascade;

