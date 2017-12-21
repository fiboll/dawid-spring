DELETE FROM table_columns;

INSERT INTO table_columns(id,title, version) VALUES (nextval('COLUMNS_SEQUENCE'),'backlog', 0);
INSERT INTO table_columns(id,title, version) VALUES (nextval('COLUMNS_SEQUENCE'),'nextToDo', 0);
INSERT INTO table_columns(id,title, version) VALUES (nextval('COLUMNS_SEQUENCE'),'doing', 0);
INSERT INTO table_columns(id,title, version) VALUES (nextval('COLUMNS_SEQUENCE'),'done', 0);

INSERT INTO user_table(id, title, backlog, next_do_do, doing, done, version) VALUES (nextval('TABLE_SEQUENCE'),
        'dawid table',
        (select id from table_columns where title = 'backlog'),
        (select id from table_columns where title = 'nextToDo'),
        (select id from table_columns where title = 'doing'),
        (select id from table_columns where title = 'done'),
        0);

--TASKS
DELETE FROM TASKS;
INSERT INTO TASKS(id, TABLE_COLUMN, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from TABLE_COLUMNS where title = 'backlog'),
    'Test Task', 'Test Task Description', '2008-11-11', 0);
INSERT INTO TASKS(id, TABLE_COLUMN, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from TABLE_COLUMNS where title = 'backlog'),
    'Test Task 2', 'Test Task Description 2', '2008-11-11', 0);
INSERT INTO TASKS(id, TABLE_COLUMN, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from TABLE_COLUMNS where title = 'backlog'),
    'Test Task 3', 'Test Task Description 3', '2008-11-11', 0);
--
--USERS
DELETE FROM USERS;
INSERT INTO USERS(id,first_name, nickname, second_name, table_id, version) VALUES (nextval('USER_SEQUENCE'),'Dawid', 'fiboll', 'Strembicki',
    (select id from user_table where title = 'dawid table'),
    0);

--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Adam', 'nowy','Nowak', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Marian', 'menda', 'Paźdioch', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Ferdynad', 'kanalia', 'Kiepski', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Arnold', 'gruby', 'Boczek', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Edzio', 'pedzio', 'Listonosz', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Wadluś', 'cyc', 'Kiepski', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Prezes', 'Prezes', 'Kozłowski', 0);


--LABELS
DELETE FROM LABELS;
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'#FFFFFF', 'A', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'#FFFF00', 'B', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'#FFFF00', 'C', 0);

--TASK/LABELS
DELETE FROM TASK_LABELS;
INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
    (SELECT ID FROM LABELS WHERE description = 'A')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
    (SELECT ID FROM LABELS WHERE description = 'B')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task 2'),
    (SELECT ID FROM LABELS WHERE description = 'A')
);
