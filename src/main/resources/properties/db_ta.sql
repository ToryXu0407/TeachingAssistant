/*
Navicat MySQL Data Transfer

Source Server         : aa
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-10-19 16:43:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ta_user`
-- ----------------------------
DROP TABLE IF EXISTS `ta_user`;
CREATE TABLE `ta_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `type` int(11) NOT NULL COMMENT '0：管理员 1：教师 2：学生',
  `create_time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_user
-- ----------------------------
INSERT INTO `ta_user` VALUES ('1', 'admin', 'admin', '0', '2018-10-15');
INSERT INTO `ta_user` VALUES ('2', 'toryxu', '123456', '2', '2018-10-15');
INSERT INTO `ta_user` VALUES ('3', 'jakexu', '123456', '2', '2018-10-17');
INSERT INTO `ta_user` VALUES ('4', 'fuckbitch', '123456', '2', '2018-10-17');
INSERT INTO `ta_user` VALUES ('5', 'fuckbitch2', '123456', '2', '2018-10-17');
INSERT INTO `ta_user` VALUES ('6', 'fuckbitch23', '321', '2', '2018-10-17');
INSERT INTO `ta_user` VALUES ('7', 'toryxu123', '123456', '2', '2018-10-17');
INSERT INTO `ta_user` VALUES ('8', 'toryxu321', '123456', '2', '2018-10-17');
INSERT INTO `ta_user` VALUES ('9', 'toryxu46581', '123456', '2', '2018-10-19');
INSERT INTO `ta_user` VALUES ('10', 'toryxu465812', '123456', '2', '2018-10-19');

-- ----------------------------
-- Table structure for `ta_user_permission`
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
