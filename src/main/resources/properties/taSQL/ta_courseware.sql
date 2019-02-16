/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_courseware
-- ----------------------------
DROP TABLE IF EXISTS `ta_courseware`;
CREATE TABLE `ta_courseware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_courseware
-- ----------------------------
INSERT INTO `ta_courseware` VALUES ('1', '2', 'downloadtest (7).doc', 'http://oss.yiqihappy.top/downloadtest%20%287%29.doc', '2019-01-21 15:00:19', '2019-01-21 15:52:09');
INSERT INTO `ta_courseware` VALUES ('2', '2', 'downloadtest (7)1.doc', 'http://oss.yiqihappy.top/downloadtest%20%287%29.doc', '2019-01-21 15:04:17', '2019-01-21 15:53:35');
INSERT INTO `ta_courseware` VALUES ('3', '2', 'chat.jpg', 'http://oss.yiqihappy.top/chat.jpg', '2019-02-16 02:59:28', null);
INSERT INTO `ta_courseware` VALUES ('4', '2', 'class.jpg', 'http://oss.yiqihappy.top/class.jpg', '2019-02-16 03:07:29', null);
