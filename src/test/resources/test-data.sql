-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: startup
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

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
-- Table structure for table 'authorities'
--

DROP TABLE IF EXISTS 'authorities';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'authorities' (
  'id' bigint(20) NOT NULL AUTO_INCREMENT,
  'authority' varchar(255) NOT NULL,
  'username' varchar(50) NOT NULL,
  PRIMARY KEY ('id'),
  UNIQUE KEY 'username' ('username','authority'),
  KEY 'FK2B0F1321D6E1593C' ('username'),
  CONSTRAINT 'FK2B0F1321D6E1593C' FOREIGN KEY ('username') REFERENCES 'users' ('username')
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'authorities'
--

LOCK TABLES 'authorities' WRITE;
/*!40000 ALTER TABLE 'authorities' DISABLE KEYS */;
INSERT INTO 'authorities' VALUES (3,'ROLE_USER','chris'),(1,'ROLE_ADMIN','jay'),(2,'ROLE_USER','jay'),(4,'ROLE_USER','test');
/*!40000 ALTER TABLE 'authorities' ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table 'item'
--

DROP TABLE IF EXISTS 'item';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'item' (
  'id' bigint(20) NOT NULL,
  'description' varchar(250) NOT NULL,
  'name' varchar(25) NOT NULL,
  'postDate' datetime DEFAULT NULL,
  'value' int(11) NOT NULL,
  'purchaseOrder_id' bigint(20) DEFAULT NULL,
  PRIMARY KEY ('id'),
  KEY 'FK317B137A0E5F26' ('purchaseOrder_id'),
  CONSTRAINT 'FK317B137A0E5F26' FOREIGN KEY ('purchaseOrder_id') REFERENCES 'purchaseorder' ('id')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'item'
--

LOCK TABLES 'item' WRITE;
/*!40000 ALTER TABLE 'item' DISABLE KEYS */;
INSERT INTO 'item' VALUES (1,'30','ipad','2012-05-07 21:12:21',100,NULL),(3,'tv man','apple tv','2012-04-21 16:00:29',10,NULL),(4,'good condition','Trumpet','2012-04-21 16:32:24',4,1),(5,'from 2008, but works great, good for email, facebook, etc','desktop computer','2012-04-21 16:45:21',10,NULL),(6,'good condition','tv','2012-04-22 12:57:58',5,NULL),(7,'this is an ipad 1','ipad','2012-04-25 07:53:32',10,NULL),(8,'this is an ipad 1   ','ipad','2012-04-25 07:54:06',10,NULL),(9,'30','ipad','2012-04-27 08:28:03',16,NULL);
/*!40000 ALTER TABLE 'item' ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table 'karmakash'
--

DROP TABLE IF EXISTS 'karmakash';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'karmakash' (
  'code' varchar(16) NOT NULL,
  'createdOn' datetime DEFAULT NULL,
  'redeemedOn' datetime DEFAULT NULL,
  'value' int(11) NOT NULL,
  'username' varchar(50) DEFAULT NULL,
  PRIMARY KEY ('code'),
  KEY 'FKA2FC0A9BD6E1593C' ('username'),
  CONSTRAINT 'FKA2FC0A9BD6E1593C' FOREIGN KEY ('username') REFERENCES 'users' ('username')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'karmakash'
--

LOCK TABLES 'karmakash' WRITE;
/*!40000 ALTER TABLE 'karmakash' DISABLE KEYS */;
INSERT INTO 'karmakash' VALUES ('0061963195707970','2012-04-22 13:11:33',NULL,1,NULL),('0142406272439424','2012-04-22 13:11:33',NULL,1,NULL),('0314052106328396','2012-04-22 13:11:34',NULL,1,NULL),('0562076601036181','2012-04-22 13:11:34',NULL,1,NULL),('0717051250485050','2012-04-22 13:13:40',NULL,1,NULL),('1082156438976178','2012-04-22 13:11:34',NULL,1,NULL),('1197979038605383','2012-04-22 12:32:51','2012-04-22 12:36:25',1,'jay'),('1221888464837440','2012-04-22 12:32:51','2012-04-22 12:36:46',1,'jay'),('1750077028949606','2012-04-22 12:32:51','2012-04-22 12:41:41',1,'jay'),('2233941942962183','2012-04-22 13:11:33',NULL,1,NULL),('2737836392950749','2012-04-22 12:32:51','2012-04-22 12:57:06',1,'test'),('2867375867919873','2012-04-22 13:11:33',NULL,1,NULL),('2980959795139136','2012-04-22 13:11:33',NULL,1,NULL),('3243335887279763','2012-04-22 13:11:34',NULL,1,NULL),('3558671989098349','2012-04-22 13:13:40',NULL,1,NULL),('3818515910619824','2012-04-22 13:11:33',NULL,1,NULL),('4052706900935218','2012-04-22 13:13:40',NULL,1,NULL),('4192564662545122','2012-04-22 13:11:33',NULL,1,NULL),('4527085357947250','2012-04-22 12:32:51',NULL,1,NULL),('4552190186733677','2012-04-22 13:11:34',NULL,1,NULL),('4732836357854229','2012-04-22 13:13:40',NULL,1,NULL),('5097194205750241','2012-04-22 13:13:40',NULL,1,NULL),('5171263522458218','2012-04-22 13:11:33',NULL,1,NULL),('5308187563798047','2012-04-22 13:13:40',NULL,1,NULL),('5354496584920137','2012-04-22 13:11:34',NULL,1,NULL),('5819966576984907','2012-04-22 13:11:34',NULL,1,NULL),('5918355283055243','2012-04-22 13:11:34',NULL,1,NULL),('6357134915253927','2012-04-22 13:13:40',NULL,1,NULL),('6505566261150519','2012-04-22 13:13:40',NULL,1,NULL),('6621487103624074','2012-04-22 13:11:33',NULL,1,NULL),('6800493108067365','2012-04-22 13:11:33',NULL,1,NULL),('7062690792953950','2012-04-22 13:13:40',NULL,1,NULL),('7915646921241718','2012-04-22 13:11:34',NULL,1,NULL),('8106239248952500','2012-04-22 13:11:34',NULL,1,NULL),('8245369900821333','2012-04-22 12:32:51',NULL,1,NULL),('8634968381848743','2012-04-22 12:32:51',NULL,1,NULL),('8659503451315496','2012-04-22 12:32:51',NULL,1,NULL),('9213326068413871','2012-04-22 13:13:40',NULL,1,NULL),('9309226987622425','2012-04-22 12:32:51',NULL,1,NULL),('9932943095193132','2012-04-22 12:32:51',NULL,1,NULL);
/*!40000 ALTER TABLE 'karmakash' ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table 'purchaseorder'
--

DROP TABLE IF EXISTS 'purchaseorder';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'purchaseorder' (
  'id' bigint(20) NOT NULL,
  'date' datetime NOT NULL,
  'status' varchar(255) NOT NULL,
  'username' varchar(50) NOT NULL,
  PRIMARY KEY ('id'),
  KEY 'FKB1F87AEDB586ECFF' ('username'),
  CONSTRAINT 'FKB1F87AEDB586ECFF' FOREIGN KEY ('username') REFERENCES 'users' ('username')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'purchaseorder'
--

LOCK TABLES 'purchaseorder' WRITE;
/*!40000 ALTER TABLE 'purchaseorder' DISABLE KEYS */;
INSERT INTO 'purchaseorder' VALUES (1,'2012-05-13 13:45:45','UNFULFILLED','jay');
/*!40000 ALTER TABLE 'purchaseorder' ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table 'users'
--

DROP TABLE IF EXISTS 'users';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'users' (
  'username' varchar(50) NOT NULL,
  'email' varchar(255) DEFAULT NULL,
  'enabled' bit(1) NOT NULL,
  'password' varchar(32) NOT NULL,
  'balance' int(11) NOT NULL,
  PRIMARY KEY ('username'),
  UNIQUE KEY 'username' ('username')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'users'
--

LOCK TABLES 'users' WRITE;
/*!40000 ALTER TABLE 'users' DISABLE KEYS */;
INSERT INTO 'users' VALUES ('chris','','','6b34fe24ac2ff8103f6fce1f0da2ef57',0),('jay','thinkbigthings@gmail.com','','baba327d241746ee0829e7e88117d4d5',21),('test','','','098f6bcd4621d373cade4e832627b4f6',1);
/*!40000 ALTER TABLE 'users' ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-05-13 14:22:20
