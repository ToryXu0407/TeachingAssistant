/*
Navicat MySQL Data Transfer

Source Server         : aa
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : db_ta_log

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-10-15 13:44:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ta_visit_log`
-- ----------------------------
DROP TABLE IF EXISTS `ta_visit_log`;
CREATE TABLE `ta_visit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_time` date NOT NULL,
  `action` varchar(255) NOT NULL,
  `controller` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `remote_ip` varchar(255) NOT NULL,
  `filter` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_visit_log
-- ----------------------------
