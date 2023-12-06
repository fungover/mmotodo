CREATE TABLE category
(
    id            INT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)       NOT NULL,
    `description` VARCHAR(255)       NULL,
    created       datetime           NULL,
    updated       datetime           NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id            INT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)       NOT NULL,
    `description` VARCHAR(255)       NULL,
    created       datetime           NULL,
    updated       datetime           NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);

CREATE TABLE task
(
    id              INT AUTO_INCREMENT NOT NULL,
    title           VARCHAR(255)       NOT NULL,
    `description`   VARCHAR(255)       NULL,
    created         datetime           NULL,
    updated         datetime           NULL,
    time_estimation DOUBLE             NOT NULL,
    due_date        datetime           NOT NULL,
    status          LONGTEXT           NULL,
    tag_id          INT                NOT NULL,
    category_id     INT                NOT NULL,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE team
(
    id      INT AUTO_INCREMENT NOT NULL,
    name    VARCHAR(255)       NOT NULL,
    created datetime           NULL,
    updated datetime           NULL,
    users   INT                NULL,
    tasks   INT                NULL,
    CONSTRAINT pk_team PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         INT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)       NOT NULL,
    last_name  VARCHAR(255)       NOT NULL,
    `role`     LONGTEXT           NOT NULL,
    created    datetime           NULL,
    updated    datetime           NULL,
    tasks      INT                NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag (id);

ALTER TABLE team
    ADD CONSTRAINT FK_TEAM_ON_TASKS FOREIGN KEY (tasks) REFERENCES task (id);

ALTER TABLE team
    ADD CONSTRAINT FK_TEAM_ON_USERS FOREIGN KEY (users) REFERENCES user (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_TASKS FOREIGN KEY (tasks) REFERENCES task (id);