/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 8.3.0 : Database - crmdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`crmdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `crmdb`;

/*Table structure for table `commentlist` */

DROP TABLE IF EXISTS `commentlist`;

CREATE TABLE `commentlist` (
  `productid` int NOT NULL,
  `userid` int NOT NULL,
  `text` char(100) NOT NULL,
  `commendate` char(30) NOT NULL,
  `evaluate` tinyint unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `commentlist` */

insert  into `commentlist`(`productid`,`userid`,`text`,`commendate`,`evaluate`) values 
(14,8,'very good!~~','2024-06-03',5),
(9,8,'垃圾','2024-06-04',4);

/*Table structure for table `feedbacklist` */

DROP TABLE IF EXISTS `feedbacklist`;

CREATE TABLE `feedbacklist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `staffid` int NOT NULL,
  `feedbackdate` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `orderlistid` int NOT NULL,
  `text` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint NOT NULL,
  `productname` char(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `feedbacklist` */

insert  into `feedbacklist`(`id`,`userid`,`staffid`,`feedbackdate`,`orderlistid`,`text`,`status`,`productname`) values 
(1,8,2,'2024-06-03',4,'太贵了，便宜点',2,'苹果'),
(2,8,4,'2024-06-03',6,'太便宜了，贵一点diandiandian',1,'树莓派'),
(3,1,4,'2024-06-03',8,'太贵了了了了',2,'电风扇'),
(4,1,4,'2024-06-03',10,'不想要了',1,'树莓派'),
(7,8,2,'2024-06-04',7,'asdfs',1,'短袖'),
(8,8,4,'2024-06-11',19,'16165',1,'树莓派');

/*Table structure for table `orderlist` */

DROP TABLE IF EXISTS `orderlist`;

CREATE TABLE `orderlist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `staffid` int NOT NULL,
  `purchasedate` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `productid` int NOT NULL,
  `buynumber` int NOT NULL,
  `totalamount` int NOT NULL,
  `status` tinyint NOT NULL DEFAULT '0',
  `productname` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orderlist` */

insert  into `orderlist`(`id`,`userid`,`staffid`,`purchasedate`,`productid`,`buynumber`,`totalamount`,`status`,`productname`) values 
(1,1,4,'2024-06-02',15,50,24950,1,'树莓派'),
(3,1,4,'2024-06-02',14,90,8910,1,'电风扇'),
(4,8,2,'2024-06-02',9,10,100,3,'苹果'),
(5,8,4,'2024-06-02',14,10,10,3,'电风扇'),
(6,8,4,'2024-06-02',15,5,999,2,'树莓派'),
(7,8,2,'2024-06-02',12,2,80,2,'短袖'),
(8,11,4,'2024-06-03',14,20,99,2,'电风扇'),
(9,11,2,'2024-06-03',12,8,320,1,'短袖'),
(10,11,4,'2024-06-03',15,5,2495,2,'树莓派'),
(11,8,2,'2024-06-03',10,10,50,2,'香蕉'),
(12,1,2,'2024-06-04',12,40,1600,1,'短袖'),
(15,1,2,'2024-06-04',11,80,480,1,'橙子'),
(19,8,4,'2024-06-11',15,90,44910,2,'树莓派');

/*Table structure for table `productlist` */

DROP TABLE IF EXISTS `productlist`;

CREATE TABLE `productlist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL,
  `total` int NOT NULL,
  `staffid` int NOT NULL,
  `price` int NOT NULL,
  `productiondate` char(30) NOT NULL,
  `type` char(5) NOT NULL,
  `text` char(100) NOT NULL,
  `imgurl` char(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `productlist` */

insert  into `productlist`(`id`,`name`,`total`,`staffid`,`price`,`productiondate`,`type`,`text`,`imgurl`) values 
(9,'苹果',90,2,10,'2024-06-01','水果','大苹果啊大苹果',''),
(11,'橙子',0,2,6,'2024-06-01','水果','大大大大橙橙橙子子子',''),
(12,'短袖',0,2,40,'2024-06-01','服装','先天穿的黑色短袖',''),
(14,'电风扇',870,4,99,'2024-06-02','其他','非常安全的电电风风风风扇扇扇',''),
(15,'树莓派',800,4,499,'2024-06-02','数码','正宗树莓派！它是一个派',''),
(16,'黄康',999,4,99,'2024-06-04','图书','黄康大傻逼',''),
(17,'HK',1000,4,1,'2024-06-04','图书','没用的东西',''),
(18,'大橙子',88,2,6,'2024-06-04','水果','大大大大橙橙橙子子子','');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` tinyint NOT NULL,
  `username` char(50) NOT NULL,
  `userpassword` char(20) NOT NULL,
  `age` tinyint NOT NULL,
  `sex` char(5) NOT NULL,
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` char(255) NOT NULL,
  `creditrating` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`role`,`username`,`userpassword`,`age`,`sex`,`phone`,`address`,`creditrating`) values 
(1,1,'kehu1','123456',18,'男','12345678901','北京',100),
(2,2,'员工1','123456',11,'男','12345678901','地球亚洲中国',10),
(3,3,'管理员1','123456',20,'男','12345678901','太阳系地球亚洲',10),
(4,2,'员工','123456',20,'男','123456789','江苏南京',10),
(5,3,'管理员2','123456',10,'男','12345678901','管理员地址',10),
(6,1,'Maker','123456',10,'男','12345678901','askdfja;',10),
(7,1,'alskfjw','123456',50,'男','12345678901','asdfasf',10),
(8,1,'黄康_','123456',5,'男','12345678901','江苏泰州',10),
(9,3,'流浪商人','123456',8,'男','01987654321','asdfsad',10),
(10,2,'员工2','123456',12,'男','12345678901','阿拉斯加',10),
(11,1,'user1','123456',20,'女','12345678901','家庭地址',10),
(12,1,'HKSB','123456',20,'男','12345678901','asdf',10),
(13,1,'asd','123456',3,'男','12345678901','asdfas',10),
(14,1,'aaa','123456',9,'男','12345678901','asdfgasdf',100),
(15,2,'FFF','123456',30,'男','123456789','上海',100),
(18,2,'bbb','123456',30,'男','123456789','上海',100),
(19,1,'kehu2','123456',5,'男','12345678901','asdfs',100);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
