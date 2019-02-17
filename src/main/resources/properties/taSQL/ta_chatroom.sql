/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-17 13:19:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_chatroom
-- ----------------------------
DROP TABLE IF EXISTS `ta_chatroom`;
CREATE TABLE `ta_chatroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `chats_count` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_chatroom
-- ----------------------------
INSERT INTO `ta_chatroom` VALUES ('2', '15', '语文第一节课的课后总结', '2019-02-13 19:18:47', '2019-02-22 14:58:47', '7', '2019-02-13 15:35:41');
INSERT INTO `ta_chatroom` VALUES ('7', '1', '321', '2019-02-16 16:34:13', '2019-02-16 16:35:13', '0', '2019-02-16 04:34:18');
INSERT INTO `ta_chatroom` VALUES ('8', '1', '31321', '2019-02-16 16:48:21', '2019-02-16 17:48:21', '0', '2019-02-16 04:48:25');
INSERT INTO `ta_chatroom` VALUES ('9', '1', '312', '2019-02-16 16:59:41', '2019-02-16 17:59:41', '0', '2019-02-16 04:59:17');
INSERT INTO `ta_chatroom` VALUES ('10', '1', 'AAA', '2019-02-16 17:15:27', '2019-02-16 18:15:27', '3', '2019-02-16 05:15:02');
