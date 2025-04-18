DROP DATABASE IF EXISTS abstract_project;

CREATE DATABASE abstract_project;

USE `abstract_project`;

-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: abstract_project
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abstract`
--

DROP TABLE IF EXISTS `abstract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abstract` (
  `abstractID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`abstractID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstract`
--

LOCK TABLES `abstract` WRITE;
/*!40000 ALTER TABLE `abstract` DISABLE KEYS */;
INSERT INTO `abstract` VALUES (1,'Professor Abstract','NewProfessor NewLast','This is my abstract'),(2,'Two Authors','Professor Lastname, Another Professor','We both wrote this abstract');
/*!40000 ALTER TABLE `abstract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `facultyID` int DEFAULT NULL,
  `studentID` int DEFAULT NULL,
  `publicUserID` int DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `facultyID` (`facultyID`),
  KEY `publicUserID` (`publicUserID`),
  KEY `studentID` (`studentID`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`facultyID`) REFERENCES `faculty` (`facultyID`),
  CONSTRAINT `account_ibfk_2` FOREIGN KEY (`publicUserID`) REFERENCES `publicuser` (`publicUserID`),
  CONSTRAINT `account_ibfk_3` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('abc123','6c55803d6f1d7a177a0db3eb4b343b0d50f9c111',NULL,2,NULL),('msw7476','91dfd9ddb4198affc5c194cd8ce6d338fde470e2',NULL,1,NULL),('public','12dea96fec20593566ab75692c9949596833adc9',NULL,NULL,1),('username','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8',1,NULL,NULL),('username2','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8',2,NULL,NULL),('username3','de2a4d5751ab06dc4f987142db57c26d50925c8a',3,NULL,NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `college` (
  `collegeID` int NOT NULL,
  `cName` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`collegeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES (1,'College of Art and Design'),(2,'Saunders College of Business'),(3,'Golisano College of Computing and Information Sciences'),(4,'Kate Gleason College of Engineering'),(5,'College of Engineering Technology'),(6,'College of Health and Sciences and Technology'),(7,'College of Liberal Arts'),(8,'National Technical Institute for the Deaf'),(9,'College of Science'),(10,'Golisano Institute for Sustainability'),(11,'School of Individualized Study');
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collegefaculty`
--

DROP TABLE IF EXISTS `collegefaculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collegefaculty` (
  `collegeID` int NOT NULL,
  `facultyID` int NOT NULL,
  PRIMARY KEY (`collegeID`,`facultyID`),
  KEY `facultyID` (`facultyID`),
  CONSTRAINT `collegefaculty_ibfk_1` FOREIGN KEY (`collegeID`) REFERENCES `college` (`collegeID`),
  CONSTRAINT `collegefaculty_ibfk_2` FOREIGN KEY (`facultyID`) REFERENCES `faculty` (`facultyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collegefaculty`
--

LOCK TABLES `collegefaculty` WRITE;
/*!40000 ALTER TABLE `collegefaculty` DISABLE KEYS */;
INSERT INTO `collegefaculty` VALUES (2,1),(3,1),(4,2),(2,3),(3,3);
/*!40000 ALTER TABLE `collegefaculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `facultyID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `officeNum` varchar(50) DEFAULT NULL,
  `buildingName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`facultyID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Professor','LastName','pl@g.rit.edu','1234','Golisano Hall'),(2,'NewProfessor','NewLast','nn@g.rit.edu','3456','Gleason Hall'),(3,'Another','Professor','ap@rit.edu','3214','Golisano Hall');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facultyabstract`
--

DROP TABLE IF EXISTS `facultyabstract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facultyabstract` (
  `facultyID` int NOT NULL,
  `abstractID` int NOT NULL,
  PRIMARY KEY (`facultyID`,`abstractID`),
  KEY `abstractID` (`abstractID`),
  CONSTRAINT `facultyabstract_ibfk_1` FOREIGN KEY (`facultyID`) REFERENCES `faculty` (`facultyID`),
  CONSTRAINT `facultyabstract_ibfk_2` FOREIGN KEY (`abstractID`) REFERENCES `abstract` (`abstractID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facultyabstract`
--

LOCK TABLES `facultyabstract` WRITE;
/*!40000 ALTER TABLE `facultyabstract` DISABLE KEYS */;
INSERT INTO `facultyabstract` VALUES (2,1),(1,2),(3,2);
/*!40000 ALTER TABLE `facultyabstract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facultyinterest`
--

DROP TABLE IF EXISTS `facultyinterest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facultyinterest` (
  `facultyID` int NOT NULL,
  `interestID` int NOT NULL,
  PRIMARY KEY (`facultyID`,`interestID`),
  KEY `interestID` (`interestID`),
  CONSTRAINT `facultyinterest_ibfk_1` FOREIGN KEY (`facultyID`) REFERENCES `faculty` (`facultyID`),
  CONSTRAINT `facultyinterest_ibfk_2` FOREIGN KEY (`interestID`) REFERENCES `interest` (`interestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facultyinterest`
--

LOCK TABLES `facultyinterest` WRITE;
/*!40000 ALTER TABLE `facultyinterest` DISABLE KEYS */;
INSERT INTO `facultyinterest` VALUES (1,1),(2,1),(3,1),(1,2),(3,2),(1,3),(3,3),(2,4),(2,5);
/*!40000 ALTER TABLE `facultyinterest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest` (
  `interestID` int NOT NULL AUTO_INCREMENT,
  `content` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`interestID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES (1,'python'),(2,'java'),(3,'sql'),(4,'calculus'),(5,'construction');
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publicuser`
--

DROP TABLE IF EXISTS `publicuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publicuser` (
  `publicUserID` int NOT NULL AUTO_INCREMENT,
  `organizationName` varchar(50) DEFAULT NULL,
  `contactInfo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`publicUserID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publicuser`
--

LOCK TABLES `publicuser` WRITE;
/*!40000 ALTER TABLE `publicuser` DISABLE KEYS */;
INSERT INTO `publicuser` VALUES (1,'MVCC Library','123-456-7890');
/*!40000 ALTER TABLE `publicuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publicuserinterest`
--

DROP TABLE IF EXISTS `publicuserinterest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publicuserinterest` (
  `publicUserID` int NOT NULL,
  `interestID` int NOT NULL,
  PRIMARY KEY (`publicUserID`,`interestID`),
  KEY `interestID` (`interestID`),
  CONSTRAINT `publicuserinterest_ibfk_1` FOREIGN KEY (`publicUserID`) REFERENCES `publicuser` (`publicUserID`),
  CONSTRAINT `publicuserinterest_ibfk_2` FOREIGN KEY (`interestID`) REFERENCES `interest` (`interestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publicuserinterest`
--

LOCK TABLES `publicuserinterest` WRITE;
/*!40000 ALTER TABLE `publicuserinterest` DISABLE KEYS */;
/*!40000 ALTER TABLE `publicuserinterest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `studentID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Michael','Williams','msw7476@g.rit.edu'),(2,'Test','User','abc123@g.rit.edu');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentinterest`
--

DROP TABLE IF EXISTS `studentinterest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentinterest` (
  `studentID` int NOT NULL,
  `interestID` int NOT NULL,
  PRIMARY KEY (`studentID`,`interestID`),
  KEY `interestID` (`interestID`),
  CONSTRAINT `studentinterest_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`),
  CONSTRAINT `studentinterest_ibfk_2` FOREIGN KEY (`interestID`) REFERENCES `interest` (`interestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentinterest`
--

LOCK TABLES `studentinterest` WRITE;
/*!40000 ALTER TABLE `studentinterest` DISABLE KEYS */;
INSERT INTO `studentinterest` VALUES (1,1),(2,1),(1,2),(1,3),(2,4),(2,5);
/*!40000 ALTER TABLE `studentinterest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentmajor`
--

DROP TABLE IF EXISTS `studentmajor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentmajor` (
  `collegeID` int NOT NULL,
  `studentID` int NOT NULL,
  `major` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`collegeID`,`studentID`),
  KEY `studentID` (`studentID`),
  CONSTRAINT `studentmajor_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`),
  CONSTRAINT `studentmajor_ibfk_2` FOREIGN KEY (`collegeID`) REFERENCES `college` (`collegeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentmajor`
--

LOCK TABLES `studentmajor` WRITE;
/*!40000 ALTER TABLE `studentmajor` DISABLE KEYS */;
INSERT INTO `studentmajor` VALUES (3,1,'CIT'),(5,2,'Engineer');
/*!40000 ALTER TABLE `studentmajor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'abstract_project'
--
/*!50003 DROP PROCEDURE IF EXISTS `faculty_search_students` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp850 */ ;
/*!50003 SET character_set_results = cp850 */ ;
/*!50003 SET collation_connection  = cp850_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `faculty_search_students`(
    IN input_interest VARCHAR(50)
)
BEGIN
        SELECT CONCAT(s.firstName, ' ', s.lastName) AS Name, s.email FROM student s 
            JOIN studentinterest USING (studentID)
            JOIN interest i USING (interestID)
            WHERE i.content = input_interest;
    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `search_abstract_student` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp850 */ ;
/*!50003 SET character_set_results = cp850 */ ;
/*!50003 SET collation_connection  = cp850_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `search_abstract_student`(
    IN input_interest VARCHAR(50)
)
BEGIN
        SELECT a.title, a.author, a.content, GROUP_CONCAT(f.email SEPARATOR ' | ') AS Emails,
            GROUP_CONCAT(CONCAT("Bldg: ", f.buildingName," Office: ", f.officeNum) SEPARATOR ' | ') AS "Building and Offices"
            FROM abstract a
            JOIN facultyabstract USING (abstractID) 
            JOIN faculty f USING (facultyID)
            JOIN facultyinterest USING (facultyID)
            JOIN interest USING (interestID)
            WHERE interest.content = input_interest
            GROUP BY a.title, a.author, a.content;
    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-18 14:17:16
