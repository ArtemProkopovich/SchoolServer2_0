DROP TABLE IF EXISTS `pupils` CASCADE
;

DROP TABLE IF EXISTS `teachers` CASCADE
;

DROP TABLE IF EXISTS `users` CASCADE
;

DROP TABLE IF EXISTS `classes` CASCADE
;

DROP TABLE IF EXISTS `subjects` CASCADE
;

DROP TABLE IF EXISTS `lessons` CASCADE
;

DROP TABLE IF EXISTS `marks` CASCADE
;

DROP TABLE IF EXISTS `roles` CASCADE
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
	FOREIGN KEY (`pupil_id`) REFERENCES `pupils` (`pupil_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `pupils` 
 ADD CONSTRAINT `FK_pupils_classes`
	FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `pupils` 
 ADD CONSTRAINT `FK_pupils_users`
	FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `subjects` 
 ADD CONSTRAINT `FK_subjects_classes`
	FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `subjects` 
 ADD CONSTRAINT `FK_subjects_teachers`
	FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`teacher_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `teachers` 
 ADD CONSTRAINT `FK_teachers_users`
	FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE `users` 
 ADD CONSTRAINT `FK_users_role`
	FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE Cascade ON UPDATE Cascade
;

INSERT INTO `school_test_db`.`roles` (`role_id`, `type`) VALUES ('1', 'admin');
INSERT INTO `school_test_db`.`roles` (`role_id`, `type`) VALUES ('2', 'teacher');
INSERT INTO `school_test_db`.`roles` (`role_id`, `type`) VALUES ('3', 'pupil');
INSERT INTO `school_test_db`.`users` (`login`, `password`, `email`, `role_id`) VALUES ('admin', 'admin', 'admin@mail.com', '1');
INSERT INTO `school_test_db`.`users` (`login`, `password`, `email`, `role_id`) VALUES ('ivan_user', 'ivan_pass', 'ivanov@mail.com', '3');
INSERT INTO `school_test_db`.`users` (`login`, `password`, `email`, `role_id`) VALUES ('sidorov_user', 'sidor_pass', 'sidorov@mail.com', '3');
INSERT INTO `school_test_db`.`users` (`login`, `password`, `email`, `role_id`) VALUES ('petr_user', 'petr_pass', 'petrov@mail.com', '2');
INSERT INTO `school_test_db`.`teachers` (`surname`, `name`, `type`, `user_id`) VALUES ('Petrov', 'Petr', 'super_teacher', '4');
INSERT INTO `school_test_db`.`classes` (`grade`, `letter`) VALUES ('5', 'B');
INSERT INTO `school_test_db`.`pupils` (`surname`, `name`, `user_id`, `class_id`) VALUES ('Ivanov', 'Ivan', '2', '1');
INSERT INTO `school_test_db`.`pupils` (`surname`, `name`, `user_id`, `class_id`) VALUES ('Sidorov', 'Sidor', '3', '1');
INSERT INTO `school_test_db`.`subjects` (`name`, `lesson_count`, `class_id`, `teacher_id`) VALUES ('Maths', '20', '1', '1');
INSERT INTO `school_test_db`.`lessons` (`date`, `schedule_number`, `homework`, `room`, `subject_id`) VALUES ('2015-01-01', '1', 'dfgdfgdfg', '101', '1');
INSERT INTO `school_test_db`.`lessons` (`date`, `schedule_number`, `homework`, `room`, `subject_id`) VALUES ('2015-01-01', '2', 'sdfsdfdsf', '101', '1');
INSERT INTO `school_test_db`.`lessons` (`date`, `schedule_number`, `homework`, `room`, `subject_id`) VALUES ('2015-01-02', '1', 'dfgdfgfdg', '102', '1');
INSERT INTO `school_test_db`.`marks` (`mark`, `pupil_id`, `lesson_id`) VALUES ('5', '1', '1');
INSERT INTO `school_test_db`.`marks` (`mark`, `pupil_id`, `lesson_id`) VALUES ('6', '1', '2');
INSERT INTO `school_test_db`.`marks` (`mark`, `pupil_id`, `lesson_id`) VALUES ('8', '1', '3');
INSERT INTO `school_test_db`.`marks` (`mark`, `pupil_id`, `lesson_id`) VALUES ('3', '2', '1');
INSERT INTO `school_test_db`.`marks` (`mark`, `pupil_id`, `lesson_id`) VALUES ('5', '2', '2');
INSERT INTO `school_test_db`.`marks` (`mark`, `pupil_id`, `lesson_id`) VALUES ('6', '2', '3');