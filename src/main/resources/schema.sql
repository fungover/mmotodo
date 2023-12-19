DROP DATABASE IF EXISTS mmotodo;
CREATE DATABASE mmotodo;
USE mmotodo;

DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS team;


CREATE TABLE tag (
                     id int PRIMARY KEY AUTO_INCREMENT,
                     name varchar(255) NOT NULL,
                     description varchar(255),
                     created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
                          id int PRIMARY KEY AUTO_INCREMENT,
                          name varchar(255) NOT NULL,
                          description varchar(255),
                          created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE task (
                      id int PRIMARY KEY AUTO_INCREMENT,
                      title varchar(255) NOT NULL,
                      description varchar(255),
                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      time_estimation double NOT NULL,
                      due_date TIMESTAMP NOT NULL,
                      status ENUM ('unassigned', 'started', 'done', 'abandoned', 'paused'),
                      tag_id int NOT NULL,
                      category_id int NOT NULL,
                      FOREIGN KEY (tag_id) REFERENCES tag(id),
                      FOREIGN KEY (category_id) REFERENCES category(id)
);


CREATE TABLE user (
                      id int PRIMARY KEY AUTO_INCREMENT,
                      first_name varchar(255) NOT NULL,
                      last_name varchar(255) NOT NULL,
                      role ENUM('user', 'admin', 'teamleader') NOT NULL,
                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      tasks int NOT NULL,
                      github_id INT NOT NULL,
                      github_username VARCHAR(255) NOT NULL,
                      avatar_url VARCHAR(255) NOT NULL,
                      github_profile_url VARCHAR(255) NOT NULL,
                      github_email VARCHAR(255) DEFAULT NULL,
                      FOREIGN KEY (tasks) REFERENCES task(id)
);

CREATE TABLE team (
                      id int PRIMARY KEY AUTO_INCREMENT,
                      name varchar(255) NOT NULL,
                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      users int,
                      tasks int,
                      FOREIGN KEY (users) REFERENCES user(id),
                      FOREIGN KEY (tasks) REFERENCES task(id)
);

## -- On deplyment a seperate flyway account is needed --
#CREATE USER 'flyway'@'%' IDENTIFIED BY 'flyway';
#GRANT SELECT, INSERT, UPDATE, DELETE ON  mmotodo.* TO 'flyway'@'%';
#GRANT SELECT, CREATE, CREATE VIEW, UPDATE, DELETE, DROP, ALTER, INSERT ON  mmotodo.* TO 'flyway'@'%';
