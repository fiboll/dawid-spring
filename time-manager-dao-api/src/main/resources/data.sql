--USERS
DELETE FROM USERS;
INSERT INTO USERS(id,first_name, nickname, second_name, email, password, version) VALUES (1, 'Dawid', 'fiboll', 'Strembicki', 'fiboll@o2.pl', 'qaz12345', 0);
INSERT INTO USERS(id,first_name, nickname, second_name, email, password, version) VALUES (2 ,'Dawid2', 'fiboll2', 'Strembicki2', 'dawid@o2.pl', 'qaz12345', 0);

--TASKS
DELETE FROM TASKS;
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (1, 1, 'Test Task', 'Test Task Description', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (2, 1, 'Test Task 2', 'Test Task Description 2', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (3, 1, 'Test Task 3', 'Test Task Description 3', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (4, 1, 'Test Task 4', 'Test Task Description 4', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (5, 1, 'Test Task 5', 'Test Task Description 5', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (6, 1,'Test Task 6', 'Test Task Description 6', '2008-11-11',0, 0);
INSERT INTO TASKS(id, USER_ID, name, desc, DUE_DATE, is_done, version) VALUES (7, 1,'Test Task 7', 'Test Task Description 7', '2008-11-11', 1, 0);

--LABELS
--DELETE FROM LABELS;
--INSERT INTO LABELS(id,colour, description, version) VALUES (1,'RED', 'A1', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (2,'RED', 'A2', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (3,'RED', 'A3', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (4,'RED', 'A4', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (5,'RED', 'A5', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (6,'YELLOW', 'B1', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (7,'YELLOW', 'B2', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (8,'YELLOW', 'B3', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (9,'YELLOW', 'B4', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (10,'YELLOW', 'B5', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (11,'GREEN', 'C1', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (12,'GREEN', 'C2', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (13,'GREEN', 'C3', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (14,'GREEN', 'C4', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (15,'GREEN', 'C5', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (16,'BLUE', 'D1', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (17,'BLUE', 'D2', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (18,'BLUE', 'D3', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (19,'BLUE', 'D4', 0);
--INSERT INTO LABELS(id,colour, description, version) VALUES (20,'BLUE', 'D5', 0);

--TASK/LABELS
--DELETE FROM TASK_LABELS;
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (1,1);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (1,11);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (2,1);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (2,6);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (3,11);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (3,1);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (3,6);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (3,16);
--INSERT INTO TASK_LABELS (TASK_ID, LABEL_ID) values (4,16);