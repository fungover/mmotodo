-- Insert sample data into the 'tag' table
INSERT INTO tag (name, description) VALUES
                                        ('Work', 'Tasks related to work'),
                                        ('Personal', 'Personal tasks'),
                                        ('Urgent', 'Tasks that need immediate attention');

-- Insert sample data into the 'category' table
INSERT INTO category (name, description) VALUES
                                             ('Development', 'Tasks related to software development'),
                                             ('Design', 'Tasks related to graphic design'),
                                             ('Meetings', 'Tasks related to team meetings');

-- Insert sample data into the 'team' table
INSERT INTO team (name) VALUES
                            ('Development Team'),
                            ('Design Team'),
                            ('Management Team');

-- Insert sample data into the 'task' table
INSERT INTO task (title, description, time_estimation, due_date, status, tag_id, category_id, team_id) VALUES
                                                                                                  ('Project XYZ', 'Develop new features', 10.5, '2023-01-15 12:00:00', 'unassigned', 1, 1, 2),
                                                                                                  ('Design Mockups', 'Create UI/UX mockups', 5.0, '2023-01-20 15:30:00', 'started', 2, 2,1),
                                                                                                  ('Team Meeting', 'Discuss project progress', 1.5, '2023-01-25 10:00:00', 'done', 3, 3, 3);


-- Insert sample data into the 'user' table with task assignments
INSERT INTO user (first_name, last_name, role, tasks, team_id) VALUES
                                                            ('John', 'Doe', 'admin', 1, 2),
                                                            ('Jane', 'Smith', 'user', 2, 1),
                                                            ('Bob', 'Johnson', 'teamleader', 3, 3);

