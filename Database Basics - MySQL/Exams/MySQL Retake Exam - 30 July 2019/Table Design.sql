CREATE DATABASE `colonial_blog_db`;

CREATE TABLE `users`
(
    `id`       INT(11)  PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(30) NOT NULL UNIQUE,
    `password` VARCHAR(30) NOT NULL,
    `email`    VARCHAR(50) NOT NULL
);

CREATE TABLE `categories`
(
    `id`       INT(11)  PRIMARY KEY AUTO_INCREMENT,
    `category` VARCHAR(30) NOT NULL
);

CREATE TABLE `articles`
(
    `id`          INT(11)  PRIMARY KEY AUTO_INCREMENT,
    `title`       VARCHAR(50) NOT NULL,
    `content`     TEXT        NOT NULL,
    `category_id` INT ,
    FOREIGN KEY (`category_id`) REFERENCES `users`(id)
);

CREATE TABLE `users_articles`(
    `user_id` INT(11)  ,
    `article_id` INT(11)  ,
    FOREIGN KEY (user_id) REFERENCES `users`(id),
    FOREIGN KEY (article_id) REFERENCES articles(id)
);

CREATE TABLE `comments`(
    `id`          INT(11)  PRIMARY KEY AUTO_INCREMENT,
    `comment` VARCHAR(255) NOT NULL ,
    `article_id` INT(11) ,
    `user_id` INT ,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (article_id) REFERENCES articles(id)
);

CREATE TABLE `likes`(
    `id`          INT(11) PRIMARY KEY AUTO_INCREMENT,
    `article_id` INT(11) ,
    `comment_id` INT(11) ,
    `user_id` INT ,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
);