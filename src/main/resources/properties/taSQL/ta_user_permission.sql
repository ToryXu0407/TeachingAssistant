/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:18:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `ta_user_permission`;
CREATE TABLE `ta_user_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_type` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_user_permission
-- ----------------------------
INSERT INTO `ta_user_permission` VALUES ('12', '0', 'admin');
INSERT INTO `ta_user_permission` VALUES ('13', '1', 'teacher');
INSERT INTO `ta_user_permission` VALUES ('14', '2', 'student');
