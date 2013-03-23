CREATE DATABASE  IF NOT EXISTS `cvtracker` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `cvtracker`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: cvtracker
-- ------------------------------------------------------
-- Server version	5.5.28

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
-- Table structure for table `synonyms`
--

DROP TABLE IF EXISTS `synonyms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `synonyms` (
  `synonym_id` int(11) NOT NULL AUTO_INCREMENT,
  `synonym` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`synonym_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synonyms`
--

LOCK TABLES `synonyms` WRITE;
/*!40000 ALTER TABLE `synonyms` DISABLE KEYS */;
INSERT INTO `synonyms` VALUES (11,'Вінниця',1),(12,'Дніпропетровськ',2),(13,'Донецьк',3),(14,'Житомир',4),(15,'Запоріжжя',5),(16,'Івано-Франківськ',6),(17,'Київ',7),(18,'Кіровоград',8),(19,'Луганськ',9),(20,'Луцьк',10),(21,'Львів',11),(22,'Миколаїв',12),(23,'Одеса',13),(24,'Полтава',14),(25,'Рівне',15),(26,'Симферополь',16),(27,'Суми',17),(28,'Тернопіль',18),(29,'Ужгород',19),(30,'Харків',20),(31,'Херсон',21),(32,'Хмельницький',22),(33,'Черкаси',23),(34,'Чернігів',26),(35,'Чернівці',24),(36,'Винница',1),(37,'Днепропетровск',2),(38,'Донецк',3),(39,'Житомир',4),(40,'Запорожье',5),(41,'Ивано-Франковск',6),(42,'Киев',7),(43,'Кировоград',8),(44,'Луганск',9),(45,'Луцк',10),(46,'Львов',11),(47,'Николаев',12),(48,'Одесса',13),(49,'Полтава',14),(50,'Ровно',15),(51,'Симферополь',16),(52,'Сумы',17),(53,'Тернополь',18),(54,'Ужгород',19),(55,'Харьков',20),(56,'Херсон',21),(57,'Хмельницкий',22),(58,'Черкассы',23),(59,'Чернигов',26),(60,'Черновцы',24),(61,'Nikolaev',12),(62,'Odesa',13),(63,'Khmel\'nyts\'kyi',22);
/*!40000 ALTER TABLE `synonyms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-31  8:04:05
