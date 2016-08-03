-- MySQL dump 10.13  Distrib 5.6.17, for osx10.7 (x86_64)
--
-- Host: localhost    Database: cpr
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `Reservation`
--

DROP TABLE IF EXISTS `Reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reservation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SESSION_ID` int(11) DEFAULT NULL,
  `CONTACT_EMAIL` varchar(128) DEFAULT NULL,
  `STUDENT` varchar(32768) DEFAULT NULL,
  `CONFIRMATION_CODE` varchar(32) DEFAULT NULL,
  `PAYMENT_STATUS` int(11) DEFAULT NULL,
  `PAYMENT_CONFIRMATION_CODE` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Reservation_SessionId` (`SESSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservation`
--

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;
INSERT INTO `Reservation` VALUES (1,3,'',NULL,'[B@7198b40d',0,''),(2,2,'',NULL,'[B@5ae97f86',0,''),(3,2,'',NULL,'[B@54dc3fd3',0,''),(4,2,'',NULL,'[B@1e136c6',0,''),(5,2,'',NULL,'[B@3e6e464c',0,''),(6,2,'',NULL,'[B@6d9da84f',0,''),(7,2,'',NULL,'[B@b13a410',0,''),(8,2,'',NULL,'[B@1207e54',0,''),(9,2,'',NULL,'[B@43f07d56',0,''),(10,2,'',NULL,'[B@2cda5432',0,''),(11,2,'',NULL,'[B@e88e50',0,''),(12,2,'',NULL,'[B@5abce6eb',0,''),(13,2,'',NULL,'[B@2473dd81',0,''),(14,2,'',NULL,'[B@1526c1c8',0,''),(15,2,'',NULL,'[B@16306a2d',0,''),(16,2,'','{\"student\": [{\"id\": 0,\"name\": \"Eddie Mayfield\",\"email\": \"eddiemay@gmail.com\",\"result\": 0},{\"id\": 0,\"name\": \"Aniya Mayfield\",\"email\": \"aniyamayfield@gmail.com\",\"result\": 0},{\"id\": 0,\"name\": \"Amir Mayfield\",\"email\": \"amirmayfield@gmail.com\",\"result\": 0},{\"id\": 0,\"name\": \"Amira Mayfield\",\"email\": \"amira@gmail.com\",\"result\": 0}]}','[B@581b8a56',0,''),(17,2,'','{\"student\": [{\"name\": \"Eddie Mayfield\",\"email\": \"eddiemay@gmail.com\",\"result\": 0},{\"name\": \"Amir Mayfield\",\"email\": \"amir@gmail.com\",\"result\": 0},{\"name\": \"Amira Mayfield\",\"email\": \"amira@gmail.com\",\"result\": 0}]}','[B@5c3327f',0,'');
/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Session`
--

DROP TABLE IF EXISTS `Session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Session` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRAINNING_ID` int(11) DEFAULT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `Duration_MINS` int(11) DEFAULT NULL,
  `COST` double DEFAULT NULL,
  `ADDRESS` varchar(128) DEFAULT NULL,
  `UNIT` varchar(32) DEFAULT NULL,
  `LATITUDE` double DEFAULT NULL,
  `LONGITUDE` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TrainningSession_TrainningId` (`TRAINNING_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Session`
--

LOCK TABLES `Session` WRITE;
/*!40000 ALTER TABLE `Session` DISABLE KEYS */;
INSERT INTO `Session` VALUES (1,1,'2016-05-09 08:30:00',60,5.25,NULL,NULL,NULL,NULL),(2,1,'2016-05-17 10:15:00',60,5.25,NULL,NULL,NULL,NULL),(3,1,'2016-05-09 12:30:00',60,5.25,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `Session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Trainning`
--

DROP TABLE IF EXISTS `Trainning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Trainning` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(128) DEFAULT NULL,
  `DESCRIPTION` varchar(512) DEFAULT NULL,
  `Duration_MINS` int(11) DEFAULT NULL,
  `COST` double DEFAULT NULL,
  `ADDRESS` varchar(128) DEFAULT NULL,
  `UNIT` varchar(32) DEFAULT NULL,
  `LATITUDE` double DEFAULT NULL,
  `LONGITUDE` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trainning`
--

LOCK TABLES `Trainning` WRITE;
/*!40000 ALTER TABLE `Trainning` DISABLE KEYS */;
INSERT INTO `Trainning` VALUES (1,'CPR 101','Introduction to CPR',60,5.25,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `Trainning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Type` int(11) DEFAULT NULL,
  `USER_NAME` varchar(32) DEFAULT NULL,
  `EMAIL` varchar(64) NOT NULL,
  `FIRST_NAME` varchar(20) NOT NULL,
  `LAST_NAME` varchar(20) NOT NULL,
  `DISABLED` tinyint(1) DEFAULT NULL,
  `READ_ONLY` tinyint(1) DEFAULT NULL,
  `PASSWORD` varchar(128) DEFAULT NULL,
  `NOTES` varchar(256) DEFAULT NULL,
  `LAST_LOGIN` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,1,'eddiemay','eddiemay@gmail.com','Eddie','Mayfield',NULL,NULL,NULL,NULL,'2016-05-10 13:06:09');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-02 23:20:41
