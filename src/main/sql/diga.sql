CREATE DATABASE  IF NOT EXISTS `bddigaservidor` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bddigaservidor`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bddigaservidor
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.32-MariaDB

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `catCodigo` int(11) NOT NULL AUTO_INCREMENT,
  `catNome` varchar(60) NOT NULL,
  `catCor` int(11) NOT NULL,
  PRIMARY KEY (`catCodigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Ambiental',0),(2,'Transito',0),(3,'Eletricidade',0),(4,'Estrutura',0);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrencia`
--

DROP TABLE IF EXISTS `ocorrencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocorrencia` (
  `ocoCodigo` int(11) NOT NULL AUTO_INCREMENT,
  `ocoTitulo` varchar(100) NOT NULL,
  `ocoDescricao` varchar(255) DEFAULT NULL,
  `ocoLatitude` double NOT NULL,
  `ocoLongitude` double NOT NULL,
  `ocoEndereco` varchar(200) DEFAULT NULL,
  `ocoFotoOcorrencia` blob,
  `ocoDataPostagem` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ocoDataResolvida` date DEFAULT NULL,
  `ocoFotoResolvida` blob,
  `ocoNumCurtidas` int(11) NOT NULL,
  `ocoNumReports` int(11) NOT NULL,
  `oco_catCodigo` int(11) NOT NULL,
  `oco_sitCodigo` int(11) NOT NULL,
  `oco_usuCodigo` int(11) NOT NULL,
  PRIMARY KEY (`ocoCodigo`),
  KEY `fk_Ocorrencia_Categoria1_idx` (`oco_catCodigo`),
  KEY `fk_Ocorrencia_Situacao1_idx` (`oco_sitCodigo`),
  KEY `fk_Ocorrencia_Usuario1_idx` (`oco_usuCodigo`),
  CONSTRAINT `fk_Ocorrencia_Categoria1` FOREIGN KEY (`oco_catCodigo`) REFERENCES `categoria` (`catCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ocorrencia_Situacao1` FOREIGN KEY (`oco_sitCodigo`) REFERENCES `situacao` (`sitCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ocorrencia_Usuario1` FOREIGN KEY (`oco_usuCodigo`) REFERENCES `usuario` (`usuCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrencia`
--

LOCK TABLES `ocorrencia` WRITE;
/*!40000 ALTER TABLE `ocorrencia` DISABLE KEYS */;
INSERT INTO `ocorrencia` VALUES (1,'Buraco na rua ','Tem um buraco na rua bem em frente à minha casa.',12.5874,48.9965,'Rua 1, Av. 1, Nº 1',NULL,'2018-06-23 20:10:56','2018-01-29',NULL,5,0,4,3,1),(2,'Alagamento na minha rua','A chuva do dia anterior deixou um alagamento na minha rua.',84.4477,65.2584,'Rua 2, Av. 2, Nº 2',NULL,'2018-06-23 20:10:56',NULL,NULL,3,1,1,2,1),(3,'Lixo jogado incorretamente','Na minha rua tem um monte de lixo jogado de forma incorreta.',11.4455,22.1548,'Rua 3, Av. 3, Nº 3',NULL,'2018-06-23 20:10:56',NULL,NULL,1,0,1,1,1),(4,'Bueiro entupido ','Bem em frente Ã  minha casa tem um bueiro entupido que estÃ¡ fedendo muito',44.3322,33.2211,'Rua 4, Av. 4, Nº 4',NULL,'2018-06-23 20:10:56',NULL,NULL,0,0,1,1,1);
/*!40000 ALTER TABLE `ocorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrencia_possui_tags`
--

DROP TABLE IF EXISTS `ocorrencia_possui_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocorrencia_possui_tags` (
  `opt_ocoCodigo` int(11) NOT NULL,
  `opt_tagCodigo` int(11) NOT NULL,
  PRIMARY KEY (`opt_ocoCodigo`,`opt_tagCodigo`),
  KEY `fk_Ocorrencia_has_Tags_Tags1_idx` (`opt_tagCodigo`),
  KEY `fk_Ocorrencia_has_Tags_Ocorrencia1_idx` (`opt_ocoCodigo`),
  CONSTRAINT `fk_Ocorrencia_has_Tags_Ocorrencia1` FOREIGN KEY (`opt_ocoCodigo`) REFERENCES `ocorrencia` (`ocoCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ocorrencia_has_Tags_Tags1` FOREIGN KEY (`opt_tagCodigo`) REFERENCES `tag` (`tagCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrencia_possui_tags`
--

LOCK TABLES `ocorrencia_possui_tags` WRITE;
/*!40000 ALTER TABLE `ocorrencia_possui_tags` DISABLE KEYS */;
INSERT INTO `ocorrencia_possui_tags` VALUES (1,5),(2,6),(3,4),(3,7);
/*!40000 ALTER TABLE `ocorrencia_possui_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situacao`
--

DROP TABLE IF EXISTS `situacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `situacao` (
  `sitCodigo` int(11) NOT NULL AUTO_INCREMENT,
  `sitNome` varchar(70) NOT NULL,
  PRIMARY KEY (`sitCodigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situacao`
--

LOCK TABLES `situacao` WRITE;
/*!40000 ALTER TABLE `situacao` DISABLE KEYS */;
INSERT INTO `situacao` VALUES (1,'Pendente'),(2,'Em processamento'),(3,'Resolvida');
/*!40000 ALTER TABLE `situacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tagCodigo` int(11) NOT NULL AUTO_INCREMENT,
  `tagNome` varchar(50) NOT NULL,
  `tagImportancia` int(11) NOT NULL COMMENT 'Soma de importancia da tag, exemplo: acidente, depravação de patrimônio, etc. Aparecerá com prioridade ',
  PRIMARY KEY (`tagCodigo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'BUEIRO ENTUPIDO',3),(2,'ACIDENTE DE CARRO',5),(3,'ARVORE CAIDA',5),(4,'ENTULHO',1),(5,'BURACO NA RUA',2),(6,'ALAGAMENTO',4),(7,'LIXO JOGADO',1);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipousuario` (
  `tusCodigo` int(11) NOT NULL AUTO_INCREMENT,
  `tusNome` varchar(50) NOT NULL,
  PRIMARY KEY (`tusCodigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipousuario`
--

LOCK TABLES `tipousuario` WRITE;
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` VALUES (1,'ADM');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `usuCodigo` int(11) NOT NULL AUTO_INCREMENT,
  `usuNome` varchar(50) NOT NULL,
  `usuNomeUsuario` varchar(20) NOT NULL,
  `usuSenha` varchar(20) NOT NULL,
  `usuLatitudeResidencia` double NOT NULL,
  `usuLongitudeResidencia` double NOT NULL,
  `usuEnderecoCompleto` varchar(200) NOT NULL,
  `usuIsInBlacklist` tinyint(1) NOT NULL,
  `usuNumStrikes` int(11) NOT NULL,
  `usu_tusCodigo` int(11) NOT NULL,
  PRIMARY KEY (`usuCodigo`),
  KEY `fk_Usuario_tipoUsuario1_idx` (`usu_tusCodigo`),
  CONSTRAINT `fk_Usuario_tipoUsuario1` FOREIGN KEY (`usu_tusCodigo`) REFERENCES `tipousuario` (`tusCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Adm Adm','adm','mda',12.3456,65.4321,'Rua do Adm',0,0,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_curte_ocorrencia`
--

DROP TABLE IF EXISTS `usuario_curte_ocorrencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_curte_ocorrencia` (
  `uso_usuCodigo` int(11) NOT NULL,
  `uso_ocoCodigo` int(11) NOT NULL,
  `usoLatitude` double NOT NULL,
  `usoLongitude` double NOT NULL,
  PRIMARY KEY (`uso_usuCodigo`,`uso_ocoCodigo`),
  KEY `fk_Usuario_has_Ocorrencia_Ocorrencia1_idx` (`uso_ocoCodigo`),
  KEY `fk_Usuario_has_Ocorrencia_Usuario_idx` (`uso_usuCodigo`),
  CONSTRAINT `fk_Usuario_has_Ocorrencia_Ocorrencia1` FOREIGN KEY (`uso_ocoCodigo`) REFERENCES `ocorrencia` (`ocoCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Ocorrencia_Usuario` FOREIGN KEY (`uso_usuCodigo`) REFERENCES `usuario` (`usuCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_curte_ocorrencia`
--

LOCK TABLES `usuario_curte_ocorrencia` WRITE;
/*!40000 ALTER TABLE `usuario_curte_ocorrencia` DISABLE KEYS */;
INSERT INTO `usuario_curte_ocorrencia` VALUES (1,1,11,22),(1,2,33,44);
/*!40000 ALTER TABLE `usuario_curte_ocorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_reporta_ocorrencia`
--

DROP TABLE IF EXISTS `usuario_reporta_ocorrencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_reporta_ocorrencia` (
  `uro_usuCodigo` int(11) NOT NULL,
  `uro_ocoCodigo` int(11) NOT NULL,
  `uroLatitude` double NOT NULL,
  `uroLongitude` double NOT NULL,
  PRIMARY KEY (`uro_usuCodigo`,`uro_ocoCodigo`),
  KEY `fk_Usuario_has_Ocorrencia_Ocorrencia2_idx` (`uro_ocoCodigo`),
  KEY `fk_Usuario_has_Ocorrencia_Usuario1_idx` (`uro_usuCodigo`),
  CONSTRAINT `fk_Usuario_has_Ocorrencia_Ocorrencia2` FOREIGN KEY (`uro_ocoCodigo`) REFERENCES `ocorrencia` (`ocoCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Ocorrencia_Usuario1` FOREIGN KEY (`uro_usuCodigo`) REFERENCES `usuario` (`usuCodigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_reporta_ocorrencia`
--

LOCK TABLES `usuario_reporta_ocorrencia` WRITE;
/*!40000 ALTER TABLE `usuario_reporta_ocorrencia` DISABLE KEYS */;
INSERT INTO `usuario_reporta_ocorrencia` VALUES (1,3,11,22),(1,4,33,44);
/*!40000 ALTER TABLE `usuario_reporta_ocorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bddigaservidor'
--

--
-- Dumping routines for database 'bddigaservidor'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-23 21:07:23
