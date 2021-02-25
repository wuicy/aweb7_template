-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: aweb_background
-- ------------------------------------------------------
-- Server version	5.7.31-log

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
-- Table structure for table `AWEB_AUTHORITY`
--

DROP TABLE IF EXISTS `AWEB_AUTHORITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_AUTHORITY` (
  `ID` varchar(50) NOT NULL COMMENT '权限id',
  `CREATE_TIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '创建者id',
  `CREATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '创建者用户名',
  `UPDATE_TIME` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '更新者id',
  `UPDATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '更新者用户名',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `NAME` varchar(255) NOT NULL COMMENT '权限名称，驼峰式全英文，唯一',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '父权限id',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_f0cmfe8wqlt27hx76n0h9o2yf` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_AUTHORITY`
--

LOCK TABLES `AWEB_AUTHORITY` WRITE;
/*!40000 ALTER TABLE `AWEB_AUTHORITY` DISABLE KEYS */;
INSERT INTO `AWEB_AUTHORITY` VALUES ('auth-d2CHlFWd','1577254488052','usr-admin','admin','1577254488052','usr-admin','admin','管理权限的权限','authorityManagement',NULL),('auth-hg1Iu2lG','1577254471036','usr-admin','admin','1577254471036','usr-admin','admin','管理角色的权限','roleManagement',NULL),('auth-l4ZqCT4J','1578452859460','usr-admin','admin','1578452859460','usr-admin','admin','管理菜单的权限','menuManagement',NULL),('auth-OLVmaPOj','1577254457368','usr-admin','admin','1577254457368','usr-admin','admin','管理用户的权限','userManagement',NULL);
/*!40000 ALTER TABLE `AWEB_AUTHORITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AWEB_MENU`
--

DROP TABLE IF EXISTS `AWEB_MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_MENU` (
  `ID` varchar(50) NOT NULL COMMENT '菜单id',
  `CREATE_TIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '创建者id',
  `CREATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '创建者用户名',
  `UPDATE_TIME` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '更新者id',
  `UPDATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '更新者用户名',
  `NAME` varchar(255) NOT NULL COMMENT '菜单名称，驼峰式全英文，唯一',
  `ORDER_` int(11) DEFAULT NULL COMMENT '排序',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '父菜单id',
  `TITLE` varchar(50) NOT NULL COMMENT '菜单显示名称',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '菜单描述',
  `ICON` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `PATH` varchar(400) DEFAULT NULL COMMENT '接口路径',
  `DEPLOY` bit(1) DEFAULT NULL COMMENT '是否部署',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_oyl0vtq40kyv8fdfjuade2yqb` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_MENU`
--

LOCK TABLES `AWEB_MENU` WRITE;
/*!40000 ALTER TABLE `AWEB_MENU` DISABLE KEYS */;
INSERT INTO `AWEB_MENU` VALUES ('file-362KK0Ra','1578991960257','usr-admin','admin','1581050117269','usr-admin','admin','userList',2,'file-RaEbwYZ3','用户管理','用户列表','el-icon-setting','user/userList',_binary '\0'),('file-4DQOBJY9','1580805691150','usr-admin','admin','1596007301082','usr-admin','admin','menuList',5,'file-RaEbwYZ3','菜单管理','','el-icon-setting','menu/menuList',_binary '\0'),('file-nuxpnaiU','1580815008102','usr-admin','admin','1581050080711','usr-admin','admin','authorityList',4,'file-RaEbwYZ3','权限管理','','el-icon-setting','authority/authorityList',_binary '\0'),('file-PNmjz9PU','1578991896698','usr-admin','admin','1581050122226','usr-admin','admin','roleList',3,'file-RaEbwYZ3','角色管理','角色列表','el-icon-setting','role/roleList',_binary '\0'),('file-RaEbwYZ3','1581049773800','usr-admin','admin','1581999128764','usr-admin','admin','userAuthorityManagement',1,'','用户权限管理','','el-icon-user-solid','',_binary '\0');
/*!40000 ALTER TABLE `AWEB_MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AWEB_ROLE`
--

DROP TABLE IF EXISTS `AWEB_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_ROLE` (
  `ID` varchar(50) NOT NULL COMMENT '角色id',
  `CREATE_TIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '创建者id',
  `CREATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '创建者用户名',
  `UPDATE_TIME` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '更新者id',
  `UPDATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '更新者用户名',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `NAME` varchar(255) NOT NULL COMMENT '角色名称，ROLE_开头，后接全英文，唯一',
  `PERMISSIONS` varchar(1024) DEFAULT NULL COMMENT '前端权限',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_1jgik2hgsmfm7g4ee2nf5m6h3` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_ROLE`
--

LOCK TABLES `AWEB_ROLE` WRITE;
/*!40000 ALTER TABLE `AWEB_ROLE` DISABLE KEYS */;
INSERT INTO `AWEB_ROLE` VALUES ('rol-QAv9DcTN','1577254344223','usr-admin','admin','1595490005856','usr-admin','admin','管理员，拥有管理用户、角色、权限、菜单这四者的权限','ROLE_ADMIN',NULL);
/*!40000 ALTER TABLE `AWEB_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AWEB_ROLE_AUTHORITY`
--

DROP TABLE IF EXISTS `AWEB_ROLE_AUTHORITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_ROLE_AUTHORITY` (
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色id',
  `AUTHORITY_ID` varchar(50) NOT NULL COMMENT '权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色和用户权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_ROLE_AUTHORITY`
--

LOCK TABLES `AWEB_ROLE_AUTHORITY` WRITE;
/*!40000 ALTER TABLE `AWEB_ROLE_AUTHORITY` DISABLE KEYS */;
INSERT INTO `AWEB_ROLE_AUTHORITY` VALUES ('rol-QAv9DcTN','auth-d2CHlFWd'),('rol-QAv9DcTN','auth-hg1Iu2lG'),('rol-QAv9DcTN','auth-l4ZqCT4J'),('rol-QAv9DcTN','auth-OLVmaPOj');
/*!40000 ALTER TABLE `AWEB_ROLE_AUTHORITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AWEB_ROLE_MENU`
--

DROP TABLE IF EXISTS `AWEB_ROLE_MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_ROLE_MENU` (
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色id',
  `MENU_ID` varchar(50) NOT NULL COMMENT '菜单id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色和用户菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_ROLE_MENU`
--

LOCK TABLES `AWEB_ROLE_MENU` WRITE;
/*!40000 ALTER TABLE `AWEB_ROLE_MENU` DISABLE KEYS */;
INSERT INTO `AWEB_ROLE_MENU` VALUES ('rol-QAv9DcTN','file-362KK0Ra'),('rol-QAv9DcTN','file-PNmjz9PU'),('rol-QAv9DcTN','file-nuxpnaiU'),('rol-QAv9DcTN','file-4DQOBJY9'),('rol-QAv9DcTN','file-RaEbwYZ3');
/*!40000 ALTER TABLE `AWEB_ROLE_MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AWEB_USER`
--

DROP TABLE IF EXISTS `AWEB_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_USER` (
  `ID` varchar(50) NOT NULL COMMENT '用户id',
  `CREATE_TIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '创建者id',
  `CREATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '创建者用户名',
  `UPDATE_TIME` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER_ID` varchar(255) DEFAULT NULL COMMENT '更新者id',
  `UPDATE_USER_NAME` varchar(255) DEFAULT NULL COMMENT '更新者用户名',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '用户描述',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '用户邮箱地址',
  `NAME` varchar(50) NOT NULL COMMENT '用户名',
  `NICKNAME` varchar(200) DEFAULT NULL COMMENT '用户昵称',
  `PASSWORD` varchar(128) NOT NULL COMMENT '用户密码，需要加密',
  `PHONE` varchar(18) DEFAULT NULL COMMENT '用户电话号码',
  `STATUS` varchar(2) DEFAULT NULL COMMENT '用户状态，属性值详见枚举类UserStatus',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_kpjtmcyew2jx0jn8ouw1jmw2n` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_USER`
--

LOCK TABLES `AWEB_USER` WRITE;
/*!40000 ALTER TABLE `aweb_user` DISABLE KEYS */;
INSERT INTO `AWEB_USER` VALUES ('usr-admin','','','','1581050800138','usr-admin','admin','','','admin','管理员','$2a$10$852p2TT7sJjZEHE64liF.ObcvwIYTfJ4FYE4s1c4Z/.IuwWD89QDa','','1');
/*!40000 ALTER TABLE `AWEB_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AWEB_USER_ROLE`
--

DROP TABLE IF EXISTS `AWEB_USER_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AWEB_USER_ROLE` (
  `USER_ID` varchar(50) NOT NULL COMMENT '用户id',
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AWEB_USER_ROLE`
--

LOCK TABLES `AWEB_USER_ROLE` WRITE;
/*!40000 ALTER TABLE `AWEB_USER_ROLE` DISABLE KEYS */;
INSERT INTO `AWEB_USER_ROLE` VALUES ('usr-admin','rol-QAv9DcTN');
/*!40000 ALTER TABLE `AWEB_USER_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'aweb_background'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-20 15:13:05
