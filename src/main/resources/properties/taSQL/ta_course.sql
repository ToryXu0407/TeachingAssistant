/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_course
-- ----------------------------
DROP TABLE IF EXISTS `ta_course`;
CREATE TABLE `ta_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `view_count` int(11) NOT NULL DEFAULT '0',
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_course
-- ----------------------------
INSERT INTO `ta_course` VALUES ('1', '语文', '语文课，从幼儿园开始就要学的那种', '2019-01-18 14:18:58', '12', 'http://oss.yiqihappy.top/u=740184852,3822927207&fm=26&gp=0.jpg');
INSERT INTO `ta_course` VALUES ('2', '数学', '数学课，1+1=1的那种', '2019-01-18 14:20:56', '65', 'http://oss.yiqihappy.top/math.jpg');
