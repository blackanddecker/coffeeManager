-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: shopmanager
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.16.04.1

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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `shop_id` int(11) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_employee_shop1_idx` (`shop_id`),
  CONSTRAINT `fk_employee_shop1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (0,'employee0_0','admin',0,'password','email'),(1,'empoyee4_1','admin',1,'password','email2'),(2,'user4','admin',0,'mypassword','myemail'),(3,'user5','employee',0,'password','employee'),(4,'vaggelis','employee',0,'1234','vaggelis@email.com');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `price` float DEFAULT NULL,
  `date_order` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `date_paid` varchar(45) DEFAULT NULL,
  `table_id` int(11) NOT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `date_delivered` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_table1_idx` (`employee_id`),
  CONSTRAINT `fk_order_table1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (0,1,NULL,'paid','2020-01-18 19:23:55.318510',0,1,NULL),(1,21.8,'2019-10-28 23:27:36','paid','2020-01-18 20:57:58.446344',1,0,'2019-11-14 22:31:14'),(2,43.6,'2019-10-28 23:33:29','paid','2020-01-06 21:09:24.954724',1,1,'2020-01-06 21:09:21'),(3,21.8,'2019-10-28 23:27:36','paid','2019-10-28 23:39:34.195567',1,1,'2019-10-28 23:38:06'),(4,NULL,'2020-01-18 14:42:27','pending',NULL,0,0,NULL),(5,NULL,'2020-01-18 14:48:40','pending',NULL,0,0,NULL),(6,NULL,'2020-01-18 14:56:28','paid','2020-01-18 20:44:48.964429',0,0,NULL),(7,NULL,'2020-01-18 14:56:48','paid','2020-01-18 20:58:01.738160',0,0,NULL),(8,NULL,'2020-01-18 18:41:23','paid','2020-01-18 20:36:52.394375',0,0,NULL),(9,NULL,'2020-01-18 18:41:35','paid','2020-01-18 20:24:56.866209',0,0,NULL),(10,9,'2020-01-18 20:58:09','delivered','2020-01-18 21:01:34.178996',0,0,'2020-01-25 13:34:59'),(11,5,'2020-01-18 21:01:42','delivered',NULL,0,0,'2020-01-24 18:56:54'),(12,14,'2020-01-18 21:01:46','delivered',NULL,0,0,'2020-01-24 18:57:00'),(13,8,'2020-01-18 21:02:02','delivered',NULL,0,0,'2020-01-24 18:56:59'),(14,16,'2020-01-18 21:02:03','delivered',NULL,0,0,'2020-01-24 18:56:50'),(15,25,'2020-01-18 21:02:04','delivered',NULL,0,0,'2020-01-24 18:56:57'),(16,35,'2020-01-18 21:02:06','paid','2020-01-26 22:16:19.242535',0,0,'2020-01-24 18:56:48'),(17,47,'2020-01-18 21:02:07','delivered',NULL,0,0,'2020-01-24 18:56:37'),(18,8,'2020-01-18 21:10:02','paid','2020-01-26 22:16:03.954457',0,0,'2020-01-24 18:56:57'),(19,5,'2020-01-21 00:20:14','delivered',NULL,0,0,'2020-01-24 18:56:32'),(20,5,'2020-01-21 00:20:25','paid','2020-01-26 22:05:46.678688',0,0,'2020-01-24 18:56:47'),(21,4,'2020-01-21 00:22:31','delivered',NULL,0,0,'2020-01-24 18:56:30'),(22,4,'2020-01-21 00:22:46','paid','2020-01-25 14:50:23.918113',0,0,'2020-01-21 02:02:54'),(23,5,'2020-01-21 01:14:58','paid','2020-01-26 22:14:15.388552',7,0,'2020-01-21 01:47:59'),(24,NULL,'2020-01-21 02:08:11','paid','2020-01-24 18:55:56.756667',9,0,NULL),(25,43.6,'2020-01-21 02:08:56','paid','2020-01-26 22:14:34.716687',0,0,'2020-01-24 18:55:54'),(26,43.6,'2020-01-21 02:10:55','paid','2020-01-21 02:12:11.601781',0,0,'2020-01-21 02:12:07'),(27,15,'2020-01-21 02:16:28','paid','2020-01-24 18:57:05.463989',7,0,'2020-01-24 18:56:45'),(28,43.6,'2020-01-21 02:19:05','delivered',NULL,0,0,'2020-01-24 18:56:28'),(29,43.6,'2020-01-21 02:19:40','delivered',NULL,0,0,'2020-01-24 18:56:55'),(30,7,'2020-01-21 02:19:59','paid','2020-01-21 02:40:49.851258',0,0,NULL),(31,11,'2020-01-21 02:20:34','delivered',NULL,7,0,'2020-01-24 18:56:26'),(32,11,'2020-01-24 18:08:57','paid','2020-01-24 18:49:20.403949',7,0,'2020-01-24 18:49:18'),(33,10,'2020-01-26 20:39:08','paid','2020-01-26 20:40:24.091373',7,0,NULL),(34,5,'2020-01-26 20:40:45','paid','2020-01-26 20:43:06.834557',9,0,NULL),(35,5,'2020-01-26 20:41:48','delivered',NULL,9,0,'2020-01-26 20:43:05'),(36,8,'2020-01-26 21:46:58','paid','2020-01-26 22:05:52.228854',9,0,NULL),(37,5,'2020-01-26 22:08:22','paid','2020-01-26 22:14:19.206299',0,0,NULL),(38,9,'2020-01-26 22:14:11','pending',NULL,5,0,NULL),(39,10,'2020-01-26 22:16:14','pending',NULL,9,0,NULL),(40,5,'2020-01-26 22:16:37','paid','2020-01-26 22:16:43.663770',6,0,NULL),(41,5,'2020-01-26 22:34:01','pending',NULL,1,0,NULL),(42,3,'2020-01-26 22:40:01','paid','2020-01-27 01:49:57.846029',5,0,NULL),(43,5,'2020-01-26 22:48:01','pending',NULL,8,0,NULL),(44,5,'2020-01-26 22:48:47','paid','2020-01-27 01:33:58.230676',8,0,NULL),(45,NULL,'2020-01-26 22:50:23','paid','2020-01-27 01:58:21.072158',8,0,NULL),(46,NULL,'2020-01-26 22:53:38','paid','2020-01-27 02:12:36.035388',0,0,NULL),(47,NULL,'2020-01-26 22:54:16','paid','2020-01-27 01:38:31.251645',0,0,NULL),(48,NULL,'2020-01-26 22:54:41','pending',NULL,0,0,NULL),(49,NULL,'2020-01-26 22:56:04','paid','2020-01-27 01:46:37.781559',0,0,NULL),(50,NULL,'2020-01-26 22:57:00','pending',NULL,0,0,NULL),(51,NULL,'2020-01-26 22:57:35','pending',NULL,0,0,NULL),(52,NULL,'2020-01-26 22:57:58','paid','2020-01-27 01:55:42.048956',0,0,NULL),(53,NULL,'2020-01-26 22:58:27','pending',NULL,0,0,NULL),(54,NULL,'2020-01-26 22:58:48','pending',NULL,0,0,NULL),(55,NULL,'2020-01-26 22:58:49','pending',NULL,0,0,NULL),(56,7,'2020-01-26 22:59:41','pending',NULL,0,0,NULL),(57,13,'2020-01-26 23:00:38','paid','2020-01-26 23:00:47.184213',9,0,NULL),(58,21.8,'2020-01-27 01:15:40','pending',NULL,2,1,NULL),(59,5,'2020-01-27 01:44:50','pending',NULL,8,0,NULL),(60,10,'2020-01-27 01:54:23','pending',NULL,8,0,NULL),(61,6,'2020-01-27 02:03:42','paid','2020-01-27 02:10:19.715216',8,0,NULL),(62,5,'2020-01-27 02:12:58','delivered',NULL,9,0,'2020-01-27 09:50:09'),(63,6,'2020-01-27 02:21:40','paid','2020-01-27 09:50:16.579046',7,0,NULL),(64,17,'2020-01-27 09:55:21','pending',NULL,8,0,NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderinfo`
--

DROP TABLE IF EXISTS `orderinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderinfo` (
  `id` int(11) NOT NULL,
  `details` varchar(45) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orderinfo_product1_idx` (`order_id`),
  CONSTRAINT `fk_orderinfo_product1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderinfo`
--

LOCK TABLES `orderinfo` WRITE;
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
INSERT INTO `orderinfo` VALUES (0,'details',3,0),(1,'de',1,0),(2,'sweet',3,1),(3,'sweet',3,2),(4,'bitter',2,2),(5,'bitter',2,1),(6,'bitter2',1,1),(7,'sweet',5,6),(8,'sweet',8,6),(9,'sweet',6,6),(10,'sweet',5,7),(11,'sweet',6,7),(12,'sweet',8,8),(13,'sweet',6,8),(14,'sweet',8,8),(15,'sweet',7,9),(16,'sweet',8,10),(17,'sweet',5,10),(18,'sweet',7,11),(19,'sweet',7,12),(20,'sweet',7,12),(21,'sweet',6,12),(22,'sweet',7,13),(23,'sweet',5,13),(24,'sweet',7,14),(25,'sweet',5,14),(26,'sweet',7,14),(27,'sweet',4,14),(28,'sweet',7,15),(29,'sweet',5,15),(30,'sweet',7,15),(31,'sweet',4,15),(32,'sweet',6,15),(33,'sweet',7,15),(34,'sweet',7,16),(35,'sweet',5,16),(36,'sweet',7,16),(37,'sweet',4,16),(38,'sweet',6,16),(39,'sweet',7,16),(40,'sweet',6,16),(41,'sweet',4,16),(42,'sweet',5,16),(43,'sweet',7,17),(44,'sweet',5,17),(45,'sweet',7,17),(46,'sweet',4,17),(47,'sweet',6,17),(48,'sweet',7,17),(49,'sweet',6,17),(50,'sweet',4,17),(51,'sweet',5,17),(52,'sweet',8,17),(53,'sweet',5,17),(54,'sweet',4,17),(55,'sweet',7,18),(56,'sweet',5,18),(57,'',7,19),(58,'',7,20),(59,'details',6,21),(60,'details',6,22),(61,'details',7,23),(62,'details',9,24),(63,'detailsgjk',9,24),(64,'detailsgjk',8,24),(65,'detailsgjk',9,24),(66,'sweet',3,25),(67,'bitter',2,25),(68,'sweet',3,26),(69,'bitter',2,26),(70,'details',8,27),(71,'details',7,27),(72,'details',6,27),(73,'sweet',3,28),(74,'bitter',2,28),(75,'sweet',3,29),(76,'bitter',2,29),(77,'sweet',5,30),(78,'bitter',6,30),(79,'details',8,31),(80,'details',7,31),(81,'',8,32),(82,'',7,32),(83,'details',7,33),(84,'details',9,33),(85,'details',9,34),(86,'details',9,35),(87,'deta',7,36),(88,'dep',4,36),(89,'cold',9,37),(90,'details',8,38),(91,'details',5,38),(92,'details',8,39),(93,'details',6,39),(94,'details',9,40),(95,'cold',9,41),(96,'details',4,42),(97,'details',9,43),(98,'details',9,44),(99,'details',9,45),(100,'sweet',5,46),(101,'sweet',5,47),(102,'sweet',5,48),(103,'sweet',5,49),(104,'sweet',5,50),(105,'sweet',5,51),(106,'sweet',5,52),(107,'sweet',5,53),(108,'sweet',5,54),(109,'sweet',5,55),(110,'sweet',5,56),(111,'bitter',6,56),(112,'details',9,57),(113,'details',4,57),(114,'details',7,57),(115,'cool',3,58),(116,'details',9,59),(117,'details',9,60),(118,'details',7,60),(119,'details',8,61),(120,'details',9,62),(121,'details',8,63),(122,'details',4,64),(123,'sugar',4,64),(124,'sugar',4,64),(125,'west',5,64),(126,'west',7,64);
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `details` varchar(45) DEFAULT NULL,
  `shop_id` int(11) NOT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_shop1_idx` (`shop_id`),
  CONSTRAINT `fk_product_shop1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'product3',21.8,'details',1,12),(2,'product2',21.8,'details',1,12),(3,'product3',21.8,'details',1,11),(4,'lemonada',3,'details',0,16),(5,'coca cola',3,'details',0,15),(6,'product3',4,'details',0,11),(7,'product2',5,'details',0,9),(8,'product1',6,'details',0,3),(9,'portokalada',5,'cold',0,8);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (0,'shop0'),(1,'shop1'),(2,'shop3');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table`
--

DROP TABLE IF EXISTS `table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table` (
  `id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `no_table` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_table_shop1_idx` (`shop_id`),
  CONSTRAINT `fk_table_shop1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table`
--

LOCK TABLES `table` WRITE;
/*!40000 ALTER TABLE `table` DISABLE KEYS */;
INSERT INTO `table` VALUES (0,0,1),(1,0,2),(2,1,2),(3,1,3),(4,1,4),(5,0,2),(6,0,3),(7,0,5),(8,0,6),(9,0,7),(10,0,8);
/*!40000 ALTER TABLE `table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'shopmanager'
--
/*!50003 DROP PROCEDURE IF EXISTS `order_search` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_search`(
IN start_time datetime,
IN end_time datetime,
IN shop_id integer,
IN tables VARCHAR(255),
IN _status VARCHAR(255),
IN employees VARCHAR(255),
IN products VARCHAR(255)
)
BEGIN
SET @sql = CONCAT('
SELECT  o.id, o.table_id, o.employee_id, o.status, o.table_id,
o.date_order, o.date_paid, o.date_delivered, o.price, t.no_table
FROM shopmanager.`order` o
	INNER JOIN shopmanager.orderinfo oi ON oi.order_id = o.id
	INNER JOIN employee e ON o.employee_id = e.id
	INNER JOIN shopmanager.`table` t ON o.table_id = t.id
WHERE ((o.date_order >=  \'',start_time,'\')
and   (o.date_order <=  \'',end_time,'\'))
and t.shop_id = ', shop_id,
(CASE WHEN tables != '' THEN (CONCAT(' and t.id IN (',tables,')')) ELSE ('') END),
(CASE WHEN employees != '' THEN (CONCAT(' and e.id IN (',employees,')')) ELSE ('') END),
(CASE WHEN _status != '' THEN (CONCAT(' and o.status IN (',_status,')')) ELSE ('') END),
(CASE WHEN products != '' THEN (CONCAT(' and p.id IN (',products,')')) ELSE ('') END),
' order by o.date_order DESC;');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
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

-- Dump completed on 2020-01-29 22:15:23
