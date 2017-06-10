CREATE TABLE USERS (
  id BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  first_name varchar(255) DEFAULT NULL,
  second_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE TASKS (
  id BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  USER_ID BIGINT NOT NULL,
  name varchar(255) DEFAULT NULL,
  desc varchar(255) DEFAULT NULL,
  DUE_DATE DATE,
  PRIMARY KEY (id)
);

CREATE TABLE LABELS (
  id BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  colour varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

create table TASK_LABELS (
    TASK_ID bigint not null,
    LABEL_ID bigint not null
);


create sequence USER_SEQUENCE start with 10000 increment by 1;
create sequence TASKS_SEQUENCE start with 10000 increment by 1;
create sequence LABEL_SEQUENCE start with 10000 increment by 1;