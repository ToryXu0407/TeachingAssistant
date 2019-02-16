/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_chat
-- ----------------------------
DROP TABLE IF EXISTS `ta_chat`;
CREATE TABLE `ta_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chatroom_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `is_teacher` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_chat
-- ----------------------------
