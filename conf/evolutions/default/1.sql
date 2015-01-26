# --- !Ups

create table DISHES (
  DISH_ID bigint generated by default as identity(start with 1) not null primary key,
  DISH_NAME varchar not null,
  LIKES integer not null);

insert into DISHES (DISH_NAME, LIKES) values ('Schweinschnitzel Mailand', 0);
insert into DISHES (DISH_NAME, LIKES) values ('Hanhenbrust Zuricher Art', 0);
INSERT INTO DISHES (DISH_NAME, LIKES) VALUES ('Piccolinos crap', 0);
insert into DISHES (DISH_NAME, LIKES) values ('Potatoes with ', 0);
insert into DISHES (DISH_NAME, LIKES) values ('Elephant', 0);
insert into DISHES (DISH_NAME, LIKES) values ('Fish', 0);
insert into DISHES (DISH_NAME, LIKES) values ('Goat', 0);
insert into DISHES (DISH_NAME, LIKES) values ('Horse', 0);

create table DISH_SCORES (
  SCORE_ID bigint generated by default as identity(start with 1) not null primary key,
  DISH_ID bigint not null,
  VALUE integer not null,
  FOREIGN KEY (DISH_ID) REFERENCES DISHES(DISH_ID)
 )


# --- !Downs

drop table DISHES;
drop table DISH_SCORES;
