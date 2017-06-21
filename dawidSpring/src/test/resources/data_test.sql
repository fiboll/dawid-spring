--USERS
DELETE FROM USERS;
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Dawid', 'Strembicki', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Adam', 'Nowak', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Marian', 'Paźdioch', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Ferdynad', 'Kiepski', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Arnold', 'Boczek', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Edzio', 'Listonosz', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Wadluś', 'Kiepski', 0);
INSERT INTO USERS(id,first_name, second_name, version) VALUES (nextval('USER_SEQUENCE'),'Prezes', 'Kozłowski', 0);

----TASKS
--DELETE FROM TASKS;
--INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where second_name = 'Strembicki'),
--    'Test Task', 'Test Task Description', '2008-11-11', 0);
--INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where second_name = 'Strembicki'),
--    'Test Task 2', 'Test Task Description 2', '2008-11-11', 0);
--INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, version) VALUES (nextval('TASKS_SEQUENCE'), (select id from USERS where second_name = 'Strembicki'),
--    'Test Task 3', 'Test Task Description 3', '2008-11-11', 0);
--
----LABELS
--DELETE FROM LABELS;
--INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'#FFFFFF', 'A', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'#FFFF00', 'B', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (nextval('USER_SEQUENCE'),'#FFFF00', 'C', 0);
--
----TASK/LABELS
--DELETE FROM TASK_LABELS;
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
--    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
--    (SELECT ID FROM LABELS WHERE description = 'A')
--);
--
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
--    (SELECT ID FROM TASKS WHERE name = 'Test Task'),
--    (SELECT ID FROM LABELS WHERE description = 'B')
--);
--
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values  (
--    (SELECT ID FROM TASKS WHERE name = 'Test Task 2'),
--    (SELECT ID FROM LABELS WHERE description = 'A')
--);