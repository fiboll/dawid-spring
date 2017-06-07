CREATE TABLE USERS (
  id BIGINT NOT NULL,
  first_name varchar(255) DEFAULT NULL,
  second_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

create sequence USER_SEQUENCE start with 10000 increment by 1