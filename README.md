#监控社交网络状态爬虫
##功能说明
本程序实现监控知乎，简书等社交网络的账号状态更新的功能，一旦有状态更新则会发邮件通知.
##使用说明
* `crawler.properties`文件定义了爬虫需要抓取的网页，可在相应的url处改为自己需要抓取的url，saveDir表示日志文件存储的路径
* `database.properties` 文件定义了Mysql数据库的信息
数据库的sql脚本如下：
```
CREATE DATABASE  IF NOT EXISTS `crawler4social` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `crawler4social`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: crawler4social
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `social`
--

DROP TABLE IF EXISTS `social`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `website` varchar(15) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social`
--

LOCK TABLES `social` WRITE;
/*!40000 ALTER TABLE `social` DISABLE KEYS */;
INSERT INTO `social` VALUES (131,'zhihu','Fri May 29 19:57:37 CST 2015','vino','Feb 关注了问题 如何通俗的解释cgi、fastcgi、php-fpm之间的关系？','www.zhihu.com/question/30672017'),(132,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 赞同了回答 如何通俗的解释cgi、fastcgi、php-fpm之间的关系？','www.zhihu.com/question/30672017/answer/48981733'),(133,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 赞同了回答 葫芦娃的剧情拍成 EVA 的风格会是什么样子？','www.zhihu.com/question/30668264/answer/49284221'),(134,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 赞同了回答 不带手机大便时，可以做哪些有意义的事情？','www.zhihu.com/question/28876652/answer/42892334'),(135,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 关注了收藏夹 電影','www.zhihu.comhttp://www.zhihu.com/collection/19631076'),(136,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 赞同了回答 如何评价中南大学研究生答辩未通过跳楼自杀并留下五千字遗书事件？','www.zhihu.com/question/30607078/answer/48738692'),(137,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 关注了专栏 稀土','www.zhihu.comhttp://zhuanlan.zhihu.com/xitucircle'),(138,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 关注了话题 云平台','www.zhihu.com/topic/19553667'),(139,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 关注了话题 Heroku','www.zhihu.com/topic/19553844'),(140,'zhihu','Fri May 29 19:57:38 CST 2015','vino','Feb 赞同了回答 PHP、Java、Python、C、C++ 这几种编程语言都各有什么特点或优点？','www.zhihu.com/question/25038841/answer/44396770'),(141,'jianshu','Fri May 29 19:57:39 CST 2015','vinooo7','vinooo7 喜欢了文章 我推荐的——技术文章，好的工具，好的网站，好的故事（一） 5月29日 18:27','www.jianshu.com/p/4f722af978dc'),(142,'jianshu','Fri May 29 19:57:39 CST 2015','vinooo7','vinooo7 关注了作者 宋小君 05月18日 11:07','www.jianshu.com/users/2870cb3c6f77'),(143,'jianshu','Fri May 29 19:57:39 CST 2015','vinooo7','vinooo7 关注了作者 Sam_Lau 05月18日 11:07','www.jianshu.com/users/256fb15baf75'),(144,'jianshu','Fri May 29 19:57:39 CST 2015','vinooo7','vinooo7 关注了作者 Tikitoo 05月18日 11:06','www.jianshu.com/users/c35153600475'),(145,'jianshu','Fri May 29 19:57:39 CST 2015','vinooo7','vinooo7 关注了作者 vino007 05月18日 11:06','www.jianshu.com/users/cb88473f0c89'),(146,'jianshu','Fri May 29 19:57:39 CST 2015','vinooo7','vinooo7 加入简书 2015.05.18 11:05','www.jianshu.com/users/cb88473f0c89');
/*!40000 ALTER TABLE `social` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-29 20:17:39

```

