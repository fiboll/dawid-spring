--USERS
DELETE FROM USERS;
INSERT INTO USERS(id,first_name, nickname, second_name, table_id, version) VALUES (nextval('USER_SEQUENCE'),'Dawid', 'fiboll', 'Strembicki', 0);

DELETE FROM TASKS;
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where title = 'fiboll'),
    'Test Task', 'Test Task Description', '2008-11-11', 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where title = 'fiboll'),
    'Test Task 2', 'Test Task Description 2', '2008-11-11', 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where title = 'fiboll'),
    'Test Task 3', 'Test Task Description 3', '2008-11-11', 0);



--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Adam', 'nowy','Nowak', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Marian', 'menda', 'Paźdioch', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Ferdynad', 'kanalia', 'Kiepski', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Arnold', 'gruby', 'Boczek', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Edzio', 'pedzio', 'Listonosz', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Wadluś', 'cyc', 'Kiepski', 0);
--INSERT INTO USERS(id,first_name, nickname, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Prezes', 'Prezes', 'Kozłowski', 0);


--LABELS
DELETE FROM LABELS;
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFFFF', 'A1', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFFFF', 'A2', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFFFF', 'A3', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFFFF', 'A4', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFFFF', 'A5', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'B1', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'B2', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'B3', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'B4', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'B5', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'C1', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'C2', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'C3', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'C4', 0);
INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('LABEL_SEQUENCE'),'#FFFF00', 'C5', 0);

--TASK/LABELS
DELETE FROM TASK_LABELS;
INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
    (SELECT ID FROM LABELS WHERE description = 'A1')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
    (SELECT ID FROM LABELS WHERE description = 'B1')
);

INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
    (SELECT ID FROM TASKS WHERE name = 'Test Task 2'),
    (SELECT ID FROM LABELS WHERE description = 'A1')
);
