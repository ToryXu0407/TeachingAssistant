/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_user
-- ----------------------------
DROP TABLE IF EXISTS `ta_user`;
CREATE TABLE `ta_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `type` int(11) NOT NULL COMMENT '0：管理员 1：教师 2：学生',
  `create_time` date NOT NULL,
  `head_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_user
-- ----------------------------
INSERT INTO `ta_user` VALUES ('1', 'admin', 'admin', '', '0', '2018-10-15', null);
INSERT INTO `ta_user` VALUES ('2', 'toryxu', '123456', '', '2', '2018-10-15', null);
INSERT INTO `ta_user` VALUES ('3', 'jakexu', '123456', 'jakexu', '2', '2018-10-17', null);
INSERT INTO `ta_user` VALUES ('4', 'fuckbitch', '123456', '', '2', '2018-10-17', null);
INSERT INTO `ta_user` VALUES ('5', 'fuckbitch2', '123456', '', '2', '2018-10-17', null);
INSERT INTO `ta_user` VALUES ('6', 'fuckbitch23', '321', '', '2', '2018-10-17', null);
INSERT INTO `ta_user` VALUES ('7', 'toryxu123', '123456', '', '2', '2018-10-17', null);
INSERT INTO `ta_user` VALUES ('8', 'toryxu321', '123456', '', '2', '2018-10-17', null);
INSERT INTO `ta_user` VALUES ('9', 'toryxu46581', '123456', '', '2', '2018-10-19', null);
INSERT INTO `ta_user` VALUES ('10', 'toryxu465812', '123456', '', '2', '2018-10-19', null);
INSERT INTO `ta_user` VALUES ('11', 'user', '123456', 'toryxu', '2', '2018-10-22', 'http://oss.yiqihappy.top/aa.jpg');
INSERT INTO `ta_user` VALUES ('12', 'fuck', '123456', 'fuck', '2', '2019-01-16', null);
INSERT INTO `ta_user` VALUES ('13', 'fuck1', '123456', 'fuck1', '2', '2019-01-16', null);
INSERT INTO `ta_user` VALUES ('14', 'dsa', '123', 'sbsb4sb', '2', '2019-01-16', null);
