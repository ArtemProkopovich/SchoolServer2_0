DROP TABLE IF EXISTS `classes` CASCADE
;

DROP TABLE IF EXISTS `lessons` CASCADE
;

DROP TABLE IF EXISTS `marks` CASCADE
;

DROP TABLE IF EXISTS `pupils` CASCADE
;

DROP TABLE IF EXISTS `roles` CASCADE
;

DROP TABLE IF EXISTS `subjects` CASCADE
;

DROP TABLE IF EXISTS `teachers` CASCADE
;

DROP TABLE IF EXISTS `users` CASCADE
;

CREATE TABLE `classes`
(
	`class_id` INT NOT NULL AUTO_INCREMENT ,
	`grade` INT NOT NULL,
	`letter` CHAR(1) NOT NULL,
	CONSTRAINT `PK_classes` PRIMARY KEY (`class_id`)
)
;

CREATE TABLE `lessons`
(
	`lesson_id` INT NOT NULL AUTO_INCREMENT ,
	`date` DATE NOT NULL,
	`schedule_number` INT NOT NULL,
	`homework` VARCHAR(200),
	`room` INT,
	`subject_id` INT NOT NULL,
	CONSTRAINT `PK_lessons` PRIMARY KEY (`lesson_id`)
)
;

CREATE TABLE `marks`
(
	`mark_id` INT NOT NULL AUTO_INCREMENT ,
	`mark` INT NOT NULL,
	`pupil_id` INT NOT NULL,
	`lesson_id` INT NOT NULL,
	CONSTRAINT `PK_marks` PRIMARY KEY (`mark_id`)
)
;

CREATE TABLE `pupils`
(
	`pupil_id` INT NOT NULL AUTO_INCREMENT ,
	`surname` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`user_id` INT,
	`class_id` INT NOT NULL,
	CONSTRAINT `PK_pupils` PRIMARY KEY (`pupil_id`)
)
;

CREATE TABLE `roles`
(
	`role_id` INT NOT NULL AUTO_INCREMENT ,
	`type` VARCHAR(50) NOT NULL,
	CONSTRAINT `PK_role` PRIMARY KEY (`role_id`)
)
;

CREATE TABLE `subjects`
(
	`subject_id` INT NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(50) NOT NULL,
	`lesson_count` INT NOT NULL,
	`class_id` INT NOT NULL,
	`teacher_id` INT NOT NULL,
	CONSTRAINT `PK_subjects` PRIMARY KEY (`subject_id`)
)
;

CREATE TABLE `teachers`
(
	`teacher_id` INT NOT NULL AUTO_INCREMENT ,
	`surname` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`type` VARCHAR(100),
	`user_id` INT,
	CONSTRAINT `PK_teachers` PRIMARY KEY (`teacher_id`)
)
;

CREATE TABLE `users`
(
	`user_id` INT NOT NULL AUTO_INCREMENT ,
	`login` VARCHAR(50) NOT NULL,
	`password` VARCHAR(64) NOT NULL,
	`email` VARCHAR(50),
	`role_id` INT NOT NULL,
	CONSTRAINT `PK_users` PRIMARY KEY (`user_id`)
)
;

ALTER TABLE `lessons` 
 ADD CONSTRAINT `FK_lessons_subjects`
	FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `marks` 
 ADD CONSTRAINT `FK_marks_lessons`
	FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `marks` 
 ADD CONSTRAINT `FK_marks_pupils`
	FOREIGN KEY (`pupil_id`) REFERENCES `pupils` (`pupil_id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `pupils` 
 ADD CONSTRAINT `FK_pupils_classes`
	FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE Restrict ON UPDATE Cascade
;

ALTER TABLE `pupils` 
 ADD CONSTRAINT `FK_pupils_users`
	FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `subjects` 
 ADD CONSTRAINT `FK_subjects_classes`
	FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE Restrict ON UPDATE Cascade
;

ALTER TABLE `subjects` 
 ADD CONSTRAINT `FK_subjects_teachers`
	FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`teacher_id`) ON DELETE Restrict ON UPDATE Cascade
;

ALTER TABLE `teachers` 
 ADD CONSTRAINT `FK_teachers_users`
	FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `users` 
 ADD CONSTRAINT `FK_users_role`
	FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE Restrict ON UPDATE Restrict
;
