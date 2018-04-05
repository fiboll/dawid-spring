DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
  id BIGINT NOT NULL UNIQUE,
  first_name varchar(255) DEFAULT NULL,
  second_name varchar(255) DEFAULT NULL,
  nickname varchar(255) NOT NULL UNIQUE,
  VERSION BIGINT NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS TASKS;
CREATE TABLE TASKS (
  id BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  USER_ID BIGINT NOT NULL,
  name varchar(255) DEFAULT NULL,
  desc varchar(255) DEFAULT NULL,
  DUE_DATE DATE,
  is_done INTEGER default 0 not null,
  PRIMARY KEY (id)
);

ALTER TABLE TASKS ADD FOREIGN KEY (USER_ID) REFERENCES USERS (id);

DROP TABLE IF EXISTS LABELS;
CREATE TABLE LABELS (
  id BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  colour varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS TASK_LABELS;
create table TASK_LABELS (
    TASK_ID bigint not null,
    LABEL_ID bigint not null,
    PRIMARY KEY (TASK_ID, LABEL_ID)
);
ALTER TABLE TASK_LABELS ADD FOREIGN KEY (LABEL_ID) REFERENCES LABELS (id);
ALTER TABLE TASK_LABELS ADD FOREIGN KEY (TASK_ID) REFERENCES TASKS (id);

DROP sequence IF EXISTS USER_SEQUENCE;
DROP sequence IF EXISTS TASKS_SEQUENCE;
DROP sequence IF EXISTS LABEL_SEQUENCE;


create sequence USER_SEQUENCE start with 1000 increment by 1;
create sequence TASKS_SEQUENCE start with 1000 increment by 1;
create sequence LABEL_SEQUENCE start with 1000 increment by 1;
