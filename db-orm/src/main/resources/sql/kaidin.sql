/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.1.73 : Database - kaidin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kaidin` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `kaidin`;

/*Table structure for table `_member` */

DROP TABLE IF EXISTS `_member`;

CREATE TABLE `_member` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `_loginName` varchar(255) DEFAULT NULL,
  `_loginPwd` varchar(255) DEFAULT NULL,
  `_nickName` varchar(255) DEFAULT NULL,
  `_roleRank` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Data for the table `_member` */

insert  into `_member`(`_id`,`_loginName`,`_loginPwd`,`_nickName`,`_roleRank`) values (1,'yang','yang','yang',0),(12,'name_10','pwd_10','nick_10',2),(13,'name_11','pwd_11','nick_11',4),(14,'name_12','pwd_12','nick_12',2),(15,'name_13','pwd_13','nick_13',4),(16,'name_14','pwd_14','nick_14',2),(17,'name_15','pwd_15','nick_15',3),(18,'name_16','pwd_16','nick_16',2),(19,'name_17','pwd_17','nick_17',4),(20,'name_18','pwd_18','nick_18',2),(21,'name_19','pwd_19','nick_19',4),(22,'name_20','pwd_20','nick_20',2),(32,'名_10','密_10','昵_10',3),(33,'名_11','密_11','昵_11',2),(34,'名_12','密_12','昵_12',4),(35,'名_13','密_13','昵_13',2),(36,'名_14','密_14','昵_14',3),(37,'名_15','密_15','昵_15',2),(38,'名_16','密_16','昵_16',3),(39,'名_17','密_17','昵_17',2),(40,'名_18','密_18','昵_18',4),(41,'名_19','密_19','昵_19',2),(42,'名_20','密_20','昵_20',3);

/*Table structure for table `cfg_menu` */

DROP TABLE IF EXISTS `cfg_menu`;

CREATE TABLE `cfg_menu` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `url` varchar(128) NOT NULL COMMENT '菜单访问路径',
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `alias` varchar(64) DEFAULT NULL COMMENT '菜单显示名称',
  `level` int(16) DEFAULT NULL COMMENT '菜单等级',
  `parent_id` bigint(16) NOT NULL COMMENT '父级菜单id',
  `code` varchar(16) DEFAULT NULL COMMENT '菜单编码',
  `sort` int(16) DEFAULT NULL COMMENT '控制菜单从小到大显示顺序，',
  `status` varchar(8) DEFAULT NULL COMMENT '菜单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `cfg_menu` */

insert  into `cfg_menu`(`id`,`url`,`name`,`alias`,`level`,`parent_id`,`code`,`sort`,`status`) values (1,'index.do','网站首页','网站首页',1,-1,'0',1,'Y'),(2,'download.do','下载','下载',1,-1,'1',2,'Y'),(3,'','在线游戏','在线游戏',1,-1,'3',-1,'Y'),(4,'youxi1','游戏1',NULL,2,3,'4',NULL,'Y'),(5,'youxi2','游戏2',NULL,3,3,'4',NULL,'Y'),(6,'youxi3','游戏3',NULL,4,3,'4',NULL,'Y');

/*Table structure for table `cfg_role` */

DROP TABLE IF EXISTS `cfg_role`;

CREATE TABLE `cfg_role` (
  `id` bigint(16) NOT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `desc` varchar(128) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cfg_role` */

/*Table structure for table `cfg_user` */

DROP TABLE IF EXISTS `cfg_user`;

CREATE TABLE `cfg_user` (
  `id` bigint(16) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '登陆用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '登陆密码',
  `alias` varchar(32) DEFAULT NULL COMMENT '别名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `mail` varchar(128) DEFAULT NULL COMMENT '邮箱地址',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `telphone` varchar(32) DEFAULT NULL COMMENT '电话',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `password_change_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `description` varchar(128) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cfg_user` */

/*Table structure for table `rlt_user_role` */

DROP TABLE IF EXISTS `rlt_user_role`;

CREATE TABLE `rlt_user_role` (
  `id` bigint(16) NOT NULL,
  `user_id` bigint(16) NOT NULL,
  `role_id` bigint(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`user_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `cfg_role` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `cfg_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rlt_user_role` */

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET latin1 NOT NULL,
  `psw` varchar(45) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`name`,`psw`) values (2,'admin','admin123'),(3,'xuxiaobin','123456'),(4,'xiaobin0801','xiaobin0801'),(6,'xiaobin0911','xiaobin0911'),(7,'xb0918','333333'),(8,'xiaobin0922','xiaobin0922');

/*Table structure for table `t_areainfo` */

DROP TABLE IF EXISTS `t_areainfo`;

CREATE TABLE `t_areainfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) DEFAULT '0',
  `name` varchar(255) DEFAULT '0',
  `parentId` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

/*Data for the table `t_areainfo` */

insert  into `t_areainfo`(`id`,`level`,`name`,`parentId`,`status`) values (1,0,'中国',0,0),(2,0,'华北区',1,0),(3,0,'华南区',1,0),(4,0,'北京',2,0),(5,0,'海淀区',4,0),(6,0,'丰台区',4,0),(7,0,'朝阳区',4,0),(8,0,'北京XX区1',4,0),(9,0,'北京XX区2',4,0),(10,0,'北京XX区3',4,0),(11,0,'北京XX区4',4,0),(12,0,'北京XX区5',4,0),(13,0,'北京XX区6',4,0),(14,0,'北京XX区7',4,0),(15,0,'北京XX区8',4,0),(16,0,'北京XX区9',4,0),(17,0,'北京XX区10',4,0),(18,0,'北京XX区11',4,0),(19,0,'北京XX区12',4,0),(20,0,'北京XX区13',4,0),(21,0,'北京XX区14',4,0),(22,0,'北京XX区15',4,0),(23,0,'北京XX区16',4,0),(24,0,'北京XX区17',4,0),(25,0,'北京XX区18',4,0),(26,0,'北京XX区19',4,0),(27,0,'北京XX区1',4,0),(28,0,'北京XX区2',4,0),(29,0,'北京XX区3',4,0),(30,0,'北京XX区4',4,0),(31,0,'北京XX区5',4,0),(32,0,'北京XX区6',4,0),(33,0,'北京XX区7',4,0),(34,0,'北京XX区8',4,0),(35,0,'北京XX区9',4,0),(36,0,'北京XX区10',4,0),(37,0,'北京XX区11',4,0),(38,0,'北京XX区12',4,0),(39,0,'北京XX区13',4,0),(40,0,'北京XX区14',4,0),(41,0,'北京XX区15',4,0),(42,0,'北京XX区16',4,0),(43,0,'北京XX区17',4,0),(44,0,'北京XX区18',4,0),(45,0,'北京XX区19',4,0),(46,0,'xx省1',1,0),(47,0,'xx省2',1,0),(48,0,'xx省3',1,0),(49,0,'xx省4',1,0),(50,0,'xx省5',1,0),(51,0,'xx省6',1,0),(52,0,'xx省7',1,0),(53,0,'xx省8',1,0),(54,0,'xx省9',1,0),(55,0,'xx省10',1,0),(56,0,'xx省11',1,0),(57,0,'xx省12',1,0),(58,0,'xx省13',1,0),(59,0,'xx省14',1,0),(60,0,'xx省15',1,0),(61,0,'xx省16',1,0),(62,0,'xx省17',1,0),(63,0,'xx省18',1,0),(64,0,'xx省19',1,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
