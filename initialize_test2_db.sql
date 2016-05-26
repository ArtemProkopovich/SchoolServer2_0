-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: school_test_db
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classes` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` int(11) NOT NULL,
  `letter` char(1) NOT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES (1,5,'B'),(2,7,'A');
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lessons`
--

DROP TABLE IF EXISTS `lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lessons` (
  `lesson_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `schedule_number` int(11) NOT NULL,
  `homework` varchar(200) DEFAULT NULL,
  `room` int(11) DEFAULT NULL,
  `subject_id` int(11) NOT NULL,
  PRIMARY KEY (`lesson_id`),
  KEY `FK_lessons_subjects` (`subject_id`),
  CONSTRAINT `FK_lessons_subjects` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessons`
--

LOCK TABLES `lessons` WRITE;
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
INSERT INTO `lessons` VALUES (1,'2016-05-23',1,'p1',101,1),(2,'2016-05-23',2,'p2',101,2),(3,'2016-05-24',1,'p3',102,1),(4,'2016-05-24',2,'p2',103,2),(5,'2016-05-24',2,'h1',104,3),(6,'2016-05-24',3,'h2',105,4),(7,'2016-05-25',1,NULL,101,1),(8,'2016-05-25',1,NULL,102,2),(9,'2016-05-25',2,NULL,103,3),(11,'2016-05-25',3,NULL,104,4),(12,'2016-05-25',3,NULL,105,5),(13,'2016-05-26',1,NULL,105,5),(14,'2016-05-26',1,NULL,104,2),(15,'2016-05-26',2,NULL,102,1),(16,'2016-05-26',2,NULL,103,4),(17,'2016-05-26',3,NULL,101,3),(18,'2016-05-27',1,NULL,102,1),(19,'2016-05-27',2,NULL,102,2),(20,'2016-05-27',2,NULL,101,3),(21,'2016-05-27',3,NULL,104,4),(22,'2016-05-27',3,NULL,105,5),(23,'2016-05-23',1,NULL,102,4),(24,'2016-05-23',2,NULL,102,5),(25,'2016-05-23',3,NULL,104,3);
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marks`
--

DROP TABLE IF EXISTS `marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marks` (
  `mark_id` int(11) NOT NULL AUTO_INCREMENT,
  `mark` int(11) NOT NULL,
  `pupil_id` int(11) NOT NULL,
  `lesson_id` int(11) NOT NULL,
  PRIMARY KEY (`mark_id`),
  KEY `FK_marks_lessons` (`lesson_id`),
  KEY `FK_marks_pupils` (`pupil_id`),
  CONSTRAINT `FK_marks_lessons` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_marks_pupils` FOREIGN KEY (`pupil_id`) REFERENCES `pupils` (`pupil_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marks`
--

LOCK TABLES `marks` WRITE;
/*!40000 ALTER TABLE `marks` DISABLE KEYS */;
INSERT INTO `marks` VALUES (1,5,1,1),(2,6,1,2),(3,8,1,3),(4,3,2,1),(5,5,2,2),(6,6,2,3),(7,5,3,4),(8,7,1,5),(9,8,4,6),(10,-1,4,4),(11,6,3,6);
/*!40000 ALTER TABLE `marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pupils`
--

DROP TABLE IF EXISTS `pupils`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pupils` (
  `pupil_id` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pupil_id`),
  KEY `FK_pupils_classes` (`class_id`),
  KEY `FK_pupils_users` (`user_id`),
  CONSTRAINT `FK_pupils_classes` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_pupils_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pupils`
--

LOCK TABLES `pupils` WRITE;
/*!40000 ALTER TABLE `pupils` DISABLE KEYS */;
INSERT INTO `pupils` VALUES (1,'Ivanov','Ivan',2,1),(2,'Sidorov','Sidor',3,1),(3,'Pupkin','Vasya',5,2),(4,'Stalin','Iosya',6,2),(5,'Undefined','Undef',8,NULL),(6,'Titova','Polina',12,1),(7,'Novik','Artyr',13,2),(8,'Kolesnilov','Nikita',14,1),(9,'Keda','Nastia',15,2),(10,'Konev','Pavel',16,1),(11,'Bolduk','Mikhail',17,2);
/*!40000 ALTER TABLE `pupils` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'teacher'),(3,'pupil');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `lesson_count` int(11) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`subject_id`),
  KEY `FK_subjects_classes` (`class_id`),
  KEY `FK_subjects_teachers` (`teacher_id`),
  CONSTRAINT `FK_subjects_classes` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_subjects_teachers` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`teacher_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'Maths',20,1,2),(2,'Literature',10,2,1),(3,'Physics',10,1,4),(4,'Biology',10,2,5),(5,'English',20,1,3);
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teachers` (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(100) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `FK_teachers_users` (`user_id`),
  CONSTRAINT `FK_teachers_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'Petrov','Petr','super_teacher',4),(2,'Kuznecov','Kuznec','maths_teacher',7),(3,'Ivanova','Natalia','english_teacher',9),(4,'Hmeleva','Maria','physics_teacher',10),(5,'Molotov','Igor','biology_teacher',11);
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_users_role` (`role_id`),
  CONSTRAINT `FK_users_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','admin@mail.com',1),(2,'ivan_user','ivan_pass','ivanov@mail.com',3),(3,'sidorov_user','sidor_pass','sidorov@mail.com',3),(4,'petr_user','petr_pass','petrov@mail.com',2),(5,'pup_user','pup_pass','pupkin@mail.com',3),(6,'sta_user','sta_pass','stalin@mail.com',3),(7,'kuz_user','kuz_pass','kuznecov@mail.com',2),(8,'und_user','und_pass','und@mail.com',3),(9,'ivanova_user','ivanova_pass','ivanova@mail.com',2),(10,'hmeleva_user','hmeleva_pass','petrova@mail.com',2),(11,'molotov_user','molotov_pass','molotov@mail.com',2),(12,'titova_user','titova_pass','titova@mail.com',3),(13,'novik_user','novik_pass','novik@mail.com',3),(14,'kolesnikov_user','kolesnikov_pass','kolesnikov@mail.com',3),(15,'keda_user','keda_pass','keda@mail.com',3),(16,'konev_user','konev_pass','konev@mail.com',3),(17,'bolduk_user','bolduk_pass','bolduk@mail.com',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-25 23:36:14
