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
-- Table structure for table `vacancy_patterns`
--

DROP TABLE IF EXISTS `vacancy_patterns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vacancy_patterns` (
  `vp_id` int(11) NOT NULL AUTO_INCREMENT,
  `vacancy_pattern` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vp_id`),
  KEY `lp_id` (`lp_id`),
  CONSTRAINT `vacancy_patterns_ibfk_1` FOREIGN KEY (`lp_id`) REFERENCES `vacancy_letter_patterns` (`lp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacancy_patterns`
--

LOCK TABLES `vacancy_patterns` WRITE;
/*!40000 ALTER TABLE `vacancy_patterns` DISABLE KEYS */;
INSERT INTO `vacancy_patterns` VALUES (1,'Компания&#46&#43&#62&#40&#46&#43&#41&#60&#47a&#62&#60&#47b&#62',1),(2,'p class&#61&#34salaryJob&#46&#42&#62&#40&#92d&#43&#41&#92s&#42&#40&#46&#43&#41&#60&#47p&#62&#46&#42&#60div class&#61&#34shortInfo',1),(3,'onclick&#61&#34&#46&#43signJSA&#46&#43search&#39&#58&#39&#40&#46&#43&#41&#39&#92S&#42&#44&#46&#42job&#46&#42&#60&#47div&#62&#60div id&#61&#34sign_err',1),(4,'dt&#62Город&#58&#60&#47dt&#62&#60dd&#62&#40location&#41&#60&#47dd&#62&#46&#42&#60dt&#62Вид занятости',1),(5,'href&#46&#43&#34&#62&#40&#46&#43&#41&#60&#47a&#62&#92s&#42&#60&#47span&#62&#92s&#42&#60&#47div&#62&#92s&#42&#60div class&#61&#34parameters',2),(6,'span class&#61&#34salary&#92&#34&#62&#92s&#42&#60b&#62&#40&#46&#43&#41&#60&#47b&#62&#92s&#42&#40&#46&#42&#41&#47мес&#46&#43&#60span class&#61&#34companyName',2),(7,'div style&#61&#34padding&#46&#43&#60h1&#62&#40&#46&#43&#41&#60&#47h1&#62&#46&#42&#60span class&#61&#34companyName',2),(8,'class&#61&#34fieldName&#34&#62Регион&#46&#43fieldValue&#34&#62&#40location&#41&#60&#47span&#62&#92s&#42&#60a href&#46&#43class&#61&#34smallLink',2),(9,'class&#61&#34viewcontcenter&#34&#62&#92s&#42&#60li&#62&#60a href&#46&#43&#62&#40&#46&#43&#41&#60&#47a&#62&#60&#47li&#62&#92s&#42&#60li&#62&#60a&#92s&#42href',3),(10,'class&#61&#34viewcontcenter&#34&#62&#46&#43&#60li&#62&#60span&#62&#40&#92d&#42&#41&#92s&#42&#40&#46&#43&#41&#60&#47span&#62&#60&#47li&#62',3),(11,'viewcontcenter&#46&#43&#60li&#62&#40&#46&#43&#41&#60&#47li&#62&#92s&#42&#60&#47ul&#62&#92s&#42&#46&#43class&#61&#34viewcontright',3),(12,'class&#61&#34viewcontcenter&#46&#43&#60li&#62&#60a&#92s&#42href&#61&#34&#47city&#46&#43&#34&#62&#40location&#41&#60&#47a&#62&#60&#47li&#62&#46&#43class&#61&#34viewcontright',3);
/*!40000 ALTER TABLE `vacancy_patterns` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-31  8:03:42
