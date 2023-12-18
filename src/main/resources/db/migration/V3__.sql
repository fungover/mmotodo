ALTER TABLE task
    ADD team_id INT NULL;

ALTER TABLE user
    ADD team_id INT NULL;

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (id);