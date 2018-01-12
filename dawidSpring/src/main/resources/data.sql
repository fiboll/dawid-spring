--USERS
DELETE FROM USERS;
INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Dawid', 'fiboll', 'Strembicki',    0);
INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Dawid2', 'fiboll2', 'Strembicki2', 0);

--TASKS
DELETE FROM TASKS;
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE,is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task', 'Test Task Description', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE,is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task 2', 'Test Task Description 2', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE,is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task 3', 'Test Task Description 3', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE,is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task 4', 'Test Task Description 4', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE,is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task 5', 'Test Task Description 5', '2008-11-11',0, 0);
 INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE,is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task 6', 'Test Task Description 6', '2008-11-11',0, 0);
 INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where nickname = 'fiboll'),
    'Test Task 7', 'Test Task Description 7', '2008-11-11', 1, 0);

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