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
    '', 'Test Task Description', '2008-11-11', 0);
INSERT INTO TASKS(id, TABLE_COLUMN, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from TABLE_COLUMNS where title = 'backlog'),
    'Test Task 2', 'Test Task Description 2', '2008-11-11', 0);
INSERT INTO TASKS(id, TABLE_COLUMN, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from TABLE_COLUMNS where title = 'nextToDo'),
    'Test Task 3', 'Test Task Description 3', '2008-11-11', 0);
INSERT INTO TASKS(id, TABLE_COLUMN, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from TABLE_COLUMNS where title = 'backlog'),
    'Test Task 4', 'Test Task Description 4', '2008-11-11', 0);
--
--USERS
DELETE FROM USERS;
INSERT INTO USERS(id,first_name, nickname, second_name, table_id, version) VALUES (nextval('USER_SEQUENCE'),'Dawid', 'fiboll', 'Strembicki',
    (select id from user_table where title = 'dawid table'),
    0);

--LABELS
DELETE FROM LABELS;
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'RED', 'B', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'BLUE', 'D', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'GREEN', 'C', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'GREEN', 'A', 0);


--TASK/LABELS
DELETE FROM TASK_LABELS;
INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
    (SELECT ID FROM LABELS WHERE description = 'A')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
	(SELECT ID FROM TASKS WHERE name = 'Test Task'),
	(SELECT ID FROM LABELS WHERE description = 'C')
);

--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
--    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
--    (SELECT ID FROM LABELS WHERE description = 'A')
--);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task 2'),
    (SELECT ID FROM LABELS WHERE description = 'A')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task 2'),
    (SELECT ID FROM LABELS WHERE description = 'B')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task 3'),
    (SELECT ID FROM LABELS WHERE description = 'C')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task 3'),
    (SELECT ID FROM LABELS WHERE description = 'A')
 );
 INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
	(SELECT ID FROM TASKS WHERE name = 'Test Task 3'),
	(SELECT ID FROM LABELS WHERE description = 'B')
);

 INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
	(SELECT ID FROM TASKS WHERE name = 'Test Task 3'),
	(SELECT ID FROM LABELS WHERE description = 'D')
);

 INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
	(SELECT ID FROM TASKS WHERE name = 'Test Task 4'),
	(SELECT ID FROM LABELS WHERE description = 'D')
);