-- Insert tags
INSERT INTO tag (name, description) VALUES
                                        ('Tag 1', 'Description for Tag 1'),
                                        ('Tag 2', 'Description for Tag 2'),
                                        ('Tag 3', 'Description for Tag 3');

-- Insert categories
INSERT INTO category (name, description) VALUES
                                             ('Category 1', 'Description for Category 1'),
                                             ('Category 2', 'Description for Category 2'),
                                             ('Category 3', 'Description for Category 3');



INSERT INTO team (name, users, tasks)
VALUES
    ('Team A', 5, 4),
    ('Team B', 6, 5),
    ('Team C', 4, 6);


-- Insert users
INSERT INTO user (first_name, last_name, role, tasks) VALUES
                                                          ('John', 'Doe', 'admin', 4),
                                                          ('Jane', 'Smith', 'user', 5),
                                                          ('Bob', 'Johnson', 'teamleader', 6);

-- Insert tasks
INSERT INTO task (title, description, time_estimation, due_date, status, tag_id, category_id) VALUES
                                                                                                  ('Task 1', 'Description for Task 1', 5.5, '2023-12-31 23:59:59', 'unassigned', 1, 1),
                                                                                                  ('Task 2', 'Description for Task 2', 8.0, '2023-12-30 18:00:00', 'started', 2, 2),
                                                                                                  ('Task 3', 'Description for Task 3', 3.5, '2024-01-15 12:00:00', 'done', 3, 1);
