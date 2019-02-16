/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_notice
-- ----------------------------
DROP TABLE IF EXISTS `ta_notice`;
CREATE TABLE `ta_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `course_id` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_notice
-- ----------------------------
INSERT INTO `ta_notice` VALUES ('1', 'math notice 1', '2', 'fuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck math', '2019-01-20 23:37:47', '2019-01-20 23:47:18', 'http://oss.yiqihappy.top/math.jpg');
INSERT INTO `ta_notice` VALUES ('2', 'math notice2', '2', 'fuck mathfuck mathfuck mathfuck mathfuck mathfuck mathfuck math', '2019-01-20 23:45:44', '2019-01-20 23:47:19', 'http://oss.yiqihappy.top/math.jpg');
INSERT INTO `ta_notice` VALUES ('3', '3', '2', 'fuck mathfuck mathfuck math', '2019-01-20 23:45:59', '2019-01-20 23:47:19', 'http://oss.yiqihappy.top/math.jpg');
INSERT INTO `ta_notice` VALUES ('4', '4', '2', '<p>dsaasdsa<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\"></p>', '2019-01-20 23:46:05', '2019-01-21 00:14:28', 'http://oss.yiqihappy.top/math.jpg');
