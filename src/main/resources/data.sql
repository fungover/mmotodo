USE mmotodo;

INSERT INTO category(id, name, description)
VALUES  (1, 'default', '');


INSERT INTO tag(id, name, description )
VALUES  (1, 'none', '');

INSERT INTO task (title, description, time_estimation, due_date, status, tag_id, category_id)
VALUES ('Test Task', 'This is a test task description', 2.5, '2023-12-31 23:59:59', 'unassigned', 1, 1);