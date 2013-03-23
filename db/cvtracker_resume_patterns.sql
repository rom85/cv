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
-- Table structure for table `resume_patterns`
--

DROP TABLE IF EXISTS `resume_patterns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resume_patterns` (
  `rp_id` int(11) NOT NULL AUTO_INCREMENT,
  `resume_pattern` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rp_id`),
  KEY `lp_id` (`lp_id`),
  CONSTRAINT `resume_patterns_ibfk_1` FOREIGN KEY (`lp_id`) REFERENCES `resume_letter_patterns` (`lp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume_patterns`
--

LOCK TABLES `resume_patterns` WRITE;
/*!40000 ALTER TABLE `resume_patterns` DISABLE KEYS */;
INSERT INTO `resume_patterns` VALUES (1,'a&#92sname&#61&#34info&#34&#46&#43&#60h1&#62&#40&#46&#43&#41&#60&#47h1&#62',1),(2,'a&#92sname&#61&#34info&#34&#46&#43&#60&#47h1&#62&#46&#43&#38nbsp&#59&#40&#46&#43&#41&#38nbsp&#59&#40&#46&#43&#41&#47мес&#46&#43&#60div class&#61&#34shortInfo',1),(3,'span&#62Контактная информация&#46&#43&#91&#94a-zA-Z0-9&#93&#40technology&#41&#91&#47&#40&#60&#92s&#59&#44&#46&#93&#46&#43&#60div class&#61&#34bottom',1),(4,'div&#92sclass&#61&#34shortInfo&#34&#46&#43Город&#58&#46&#43&#60dd&#62&#46&#42&#40location&#41&#46&#42&#60&#47dd&#62',1),(5,'',2),(6,'div&#92sclass&#61&#34payment&#34&#46&#43&#60h2&#62&#40&#46&#43&#41&#60span&#62&#40&#46&#43&#41&#47мес&#60&#47span&#62',2),(7,'Ключевая информация&#46&#43&#91&#94a-zA-Z0-9&#93&#40technology&#41&#91&#47&#40&#60&#92s&#59&#44&#46&#93&#46&#43График работы',2),(8,'Командировки&#46&#43&#60div class&#61&#34value&#34&#62&#46&#42&#40location&#41&#46&#42&#60&#47div&#62&#46&#43Ищу работу в городе&#58&#60&#47span&#62',2),(9,'ul&#92sclass&#61&#34viewcontcenter&#46&#43&#60li&#62&#40&#46&#43&#41&#60&#47li&#62&#46&#43&#60li&#62&#92d&#43&#92&#46&#92d&#43&#46&#43&#60&#47li&#62',3),(10,'ul&#92sclass&#61&#34viewcontcenter&#46&#43&#60li&#62&#92d&#43&#92&#46&#92d&#43&#46&#43&#60&#47li&#62&#46&#43&#60li&#62&#40&#92d&#43&#41&#92s&#42&#40&#46&#42&#41&#60&#47li&#62&#46&#43&#60&#47li&#62&#46&#43div class&#61&#34viewcontright',3),(11,'h2&#62Профессиональные навыки&#46&#43&#91&#94a-zA-Z0-9&#93&#40technology&#41&#91&#47&#40&#60&#92s&#59&#44&#46&#93&#46&#43&#60h2&#62Образование',3),(12,'div&#92sclass&#61&#34post_vac&#46&#43Резюме&#46&#43&#92&#40&#40location&#41&#92&#41&#60&#47h1&#62&#46&#43&#60div class&#61&#34view',3);
/*!40000 ALTER TABLE `resume_patterns` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-31  8:06:44
