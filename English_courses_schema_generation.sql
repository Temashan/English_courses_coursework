create schema if not exists `english_courses`;

use `english_courses`;

CREATE TABLE IF NOT EXISTS `users`(
	`id` int NOT NULL  auto_increment,
	`login`  varchar(50) NOT NULL UNIQUE,
	`password` varchar(50) NOT NULL,
	`userType` int NOT NULL,
 CONSTRAINT `PK_users` PRIMARY KEY (`id` ASC)
);

CREATE TABLE IF NOT EXISTS `profiles`(
	`id` int NOT NULL  auto_increment,
	`surname`  varchar(50) NOT NULL,
	`name`  varchar(50) NOT NULL,
	`patronymic`  varchar(50) NOT NULL,
	`country` varchar(50) NOT NULL,
	`email` varchar(50) NOT NULL,
	`status` int NOT NULL,
	`userId` int NOT NULL,
 CONSTRAINT `PK_profiles` PRIMARY KEY (`id` ASC),
 CONSTRAINT `FK_profiles_users` foreign key (`userId` ASC) references `users`(`id`)
);

CREATE TABLE IF NOT EXISTS `courses`(
	`id` int NOT NULL  auto_increment,
	`name`  varchar(50) NOT NULL,
	`description` varchar(200) NOT NULL,
	`rate` int NOT NULL,
	`cost` float NOT NULL,
	`dataPath` varchar(255) NOT NULL,
 CONSTRAINT `PK_courses` PRIMARY KEY (`id` ASC)
);

CREATE TABLE IF NOT EXISTS `courses_topics`(
	`id` int NOT NULL  auto_increment,
    `courseId` int not null,
    `ordinalNumber` int not null,
    `name` varchar(50) not null,
 CONSTRAINT `PK_courses_topics` PRIMARY KEY (`id` ASC),
 CONSTRAINT `FK_courses_topics_courses` foreign key (`courseId` ASC) references `courses`(`id`)
);

CREATE TABLE IF NOT EXISTS `user_courses`(
    `userId` int not null,
    `courseId` int not null,
 CONSTRAINT `FK_user_courses_users` foreign key (`userId` ASC) references `users`(`id`),
 CONSTRAINT `FK_user_courses_courses` foreign key (`courseId` ASC) references `courses`(`id`)
);

CREATE TABLE IF NOT EXISTS `user_passed_topics`(
    `userId` int not null,
    `topicId` int not null,
 CONSTRAINT `FK_user_passed_topics_users` foreign key (`userId` ASC) references `users`(`id`),
 CONSTRAINT `FK_user_passed_topics_courses_topics` foreign key (`topicId` ASC) references `courses_topics`(`id`)
);