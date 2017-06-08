CREATE TABLE USERS (
  id BIGINT NOT NULL,
  first_name varchar(255) DEFAULT NULL,
  second_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE TASKS (
  id BIGINT NOT NULL,
  USER_ID BIGINT NOT NULL,
  name varchar(255) DEFAULT NULL,
  desc varchar(255) DEFAULT NULL,
  DUE_DATE DATE,
  PRIMARY KEY (id)
);

create sequence USER_SEQUENCE start with 10000 increment by 1;
create sequence TASKS_SEQUENCE start with 10000 increment by 1;