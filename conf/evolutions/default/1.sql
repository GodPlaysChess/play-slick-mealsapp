# --- !Ups

create table DISHES (
  DISH_ID BIGINT generated by default as identity(start with 1) not null primary key,
  DISH_NAME VARCHAR,
  CODE VARCHAR,
  WEIGTH INT,
  CALORIES VARCHAR,
  PROTEINS INT,
  CARBS INT,
  FAT INT,
  PRICE VARCHAR
  );

create table DISH_SCORES (
  USERNAME VARCHAR not null,
  DISH_ID BIGINT not null,
  VALUE DOUBLE not null,
  PRIMARY KEY (USERNAME, DISH_ID),
  FOREIGN KEY (DISH_ID) REFERENCES DISHES(DISH_ID)
 );
 
 create table USERS (
  USER_ID BIGINT generated by default as identity(start with 1) not null primary key,
  USERNAME VARCHAR not null,
  PASSWORD VARCHAR not null
 );


# --- !Downs

drop table DISHES;
drop table DISH_SCORES;
drop table USERS;
