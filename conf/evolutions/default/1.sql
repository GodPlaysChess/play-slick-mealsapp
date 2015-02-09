# --- !Ups

create table DISHES (
  DISH_ID bigint generated by default as identity(start with 1) not null primary key,
  DISH_NAME varchar,
  CODE varchar,
  WEIGTH INT,
  CALORIES VARCHAR,
  PROTEINS INT,
  CARBS INT,
  FAT INT,
  PRICE varchar
  );

insert into DISHES (DISH_NAME) values ('Schweinschnitzel Mailand');
insert into DISHES (DISH_NAME) values ('Hanhenbrust Zuricher Art');
INSERT INTO DISHES (DISH_NAME) VALUES ('Piccolinos crap');
insert into DISHES (DISH_NAME) values ('Potatoes with ');
insert into DISHES (DISH_NAME) values ('Elephant');
insert into DISHES (DISH_NAME) values ('Fish');
insert into DISHES (DISH_NAME) values ('Goat');
insert into DISHES (DISH_NAME) values ('Horse');

create table DISH_SCORES (
  USERNAME varchar not null,
  DISH_ID bigint not null,
  VALUE integer not null,
  PRIMARY KEY (USERNAME, DISH_ID),
  FOREIGN KEY (DISH_ID) REFERENCES DISHES(DISH_ID)
 );
 
 create table USERS (
  USER_ID bigint generated by default as identity(start with 1) not null primary key,
  USERNAME VARCHAR not null,
  PASSWORD VARCHAR not null
 );


# --- !Downs

drop table DISHES;
drop table DISH_SCORES;
drop table USERS;
