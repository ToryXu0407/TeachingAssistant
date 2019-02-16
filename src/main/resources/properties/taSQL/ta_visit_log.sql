/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta_log

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:18:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_visit_log
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
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_visit_log
-- ----------------------------
INSERT INTO `ta_visit_log` VALUES ('1', '2018-10-15', '/loginSubmit', '/', 'admin', '0:0:0:0:0:0:0:1', 'username=admin  password=admin  ');
INSERT INTO `ta_visit_log` VALUES ('2', '2018-10-15', '/loginSubmit', '/', 'admin', '0:0:0:0:0:0:0:1', 'username=admin  password=admin  ');
INSERT INTO `ta_visit_log` VALUES ('3', '2018-10-15', '/loginSubmit', '/', 'admin', '0:0:0:0:0:0:0:1', 'username=admin  password=admin  ');
INSERT INTO `ta_visit_log` VALUES ('4', '2018-10-15', '/Student', '/Student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('5', '2018-10-15', '/Student', '/Student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('6', '2018-10-15', '/', '/', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('7', '2018-10-15', '/', '/', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('8', '2018-10-15', '/student', '/student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('9', '2018-10-15', '/student', '/student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('10', '2018-10-15', '/student', '/student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('11', '2018-10-15', '/loginSubmit', '/', 'admin', '0:0:0:0:0:0:0:1', 'username=admin  password=admin  ');
INSERT INTO `ta_visit_log` VALUES ('12', '2018-10-15', '/student', '/student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('13', '2018-10-15', '/student', '/student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('14', '2018-10-15', '/student', '/student', 'admin', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('15', '2018-10-15', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('16', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('17', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('18', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('19', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('20', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('21', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('22', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('23', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('24', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('25', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('26', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('27', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('28', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('29', '2018-10-16', '/', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('30', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('31', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('32', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('33', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('34', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('35', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('36', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('37', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('38', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('39', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('40', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('41', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('42', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('43', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('44', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('45', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('46', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('47', '2018-10-16', '/loginSubmit', '/', 'toryxu', '0:0:0:0:0:0:0:1', 'username=toryxu  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('48', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('49', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('50', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('51', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('52', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('53', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('54', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('55', '2018-10-16', '/student', '/student', 'toryxu', '127.0.0.1', '');
INSERT INTO `ta_visit_log` VALUES ('56', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('57', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('58', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('59', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('60', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('61', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('62', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('63', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('64', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('65', '2018-10-16', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('66', '2018-10-17', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('67', '2018-10-17', '/', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('68', '2018-10-17', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('69', '2018-10-17', '/', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('70', '2018-10-17', '/', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('71', '2018-10-17', '/', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('72', '2018-10-17', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('73', '2018-10-17', '/student', '/student', 'jakexu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('74', '2018-10-17', '/student', '/student', 'jakexu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('75', '2018-10-17', '/', '/', 'jakexu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('76', '2018-10-17', '/', '/', 'jakexu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('77', '2018-10-17', '/register/RegisterSubmit', '/register', 'fuckbitch', '0:0:0:0:0:0:0:1', 'username=fuckbitch2  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('78', '2018-10-17', '/student', '/student', 'fuckbitch2', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('79', '2018-10-17', '/register/register', '/register', 'fuckbitch2', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('80', '2018-10-17', '/register/RegisterSubmit', '/register', 'fuckbitch2', '0:0:0:0:0:0:0:1', 'username=fuckbitch23  password=321  ');
INSERT INTO `ta_visit_log` VALUES ('81', '2018-10-17', '/student', '/student', 'fuckbitch23', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('82', '2018-10-17', '/register/register', '/register', 'fuckbitch23', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('83', '2018-10-17', '/login', '/', 'fuckbitch23', '0:0:0:0:0:0:0:1', 'redirect=/  ');
INSERT INTO `ta_visit_log` VALUES ('84', '2018-10-17', '/Toregister', '/', 'fuckbitch23', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('85', '2018-10-17', '/register/register', '/register', 'fuckbitch23', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('86', '2018-10-17', '/register/RegisterSubmit', '/register', 'fuckbitch23', '0:0:0:0:0:0:0:1', 'username=toryxu123  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('87', '2018-10-17', '/student', '/student', 'toryxu123', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('88', '2018-10-17', '/student', '/student', 'toryxu321', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('89', '2018-10-17', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('90', '2018-10-17', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('91', '2018-10-17', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('92', '2018-10-19', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('93', '2018-10-19', '/Toregister', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('94', '2018-10-19', '/register/register', '/register', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('95', '2018-10-19', '/register/RegisterSubmit', '/register', 'toryxu', '0:0:0:0:0:0:0:1', 'username=toryxu  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('96', '2018-10-19', '/register/RegisterSubmit', '/register', 'toryxu', '0:0:0:0:0:0:0:1', 'username=toryxu1325  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('97', '2018-10-19', '/student', '/student', 'toryxu1325', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('98', '2018-10-19', '/login', '/', 'toryxu1325', '0:0:0:0:0:0:0:1', 'redirect=/student  ');
INSERT INTO `ta_visit_log` VALUES ('99', '2018-10-19', '/loginSubmit', '/', 'toryxu1325', '0:0:0:0:0:0:0:1', 'username=toryxu  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('100', '2018-10-19', '/student', '/student', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('101', '2018-10-19', '/Toregister', '/', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('102', '2018-10-19', '/register/register', '/register', 'toryxu', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('103', '2018-10-19', '/register/RegisterSubmit', '/register', 'toryxu', '0:0:0:0:0:0:0:1', 'username=toryxu4658  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('104', '2018-10-19', '/student', '/student', 'toryxu4658', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('105', '2018-10-19', '/login', '/', 'toryxu4658', '0:0:0:0:0:0:0:1', 'redirect=/student  ');
INSERT INTO `ta_visit_log` VALUES ('106', '2018-10-19', '/student', '/student', 'toryxu4658', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('107', '2018-10-19', '/login', '/', 'toryxu4658', '0:0:0:0:0:0:0:1', 'redirect=/student  ');
INSERT INTO `ta_visit_log` VALUES ('108', '2018-10-19', '/student', '/student', 'toryxu46581', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('109', '2018-10-19', '/student', '/student', 'toryxu46581', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('110', '2018-10-19', '/student', '/student', 'toryxu465812', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('111', '2018-10-19', '/student', '/student', 'toryxu465812', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('112', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('113', '2018-10-22', '/login', '/', 'user', '0:0:0:0:0:0:0:1', 'redirect=/student  ');
INSERT INTO `ta_visit_log` VALUES ('114', '2018-10-22', '/loginSubmit', '/', 'user', '0:0:0:0:0:0:0:1', 'username=user  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('115', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('116', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('117', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('118', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('119', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('120', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('121', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('122', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('123', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('124', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('125', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('126', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('127', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('128', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('129', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('130', '2018-10-22', '/loginSubmit', '/', 'user', '0:0:0:0:0:0:0:1', 'username=user  password=123456  ');
INSERT INTO `ta_visit_log` VALUES ('131', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('132', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('133', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('134', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('135', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('136', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('137', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('138', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('139', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('140', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('141', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('142', '2018-10-22', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('143', '2018-11-25', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('144', '2018-12-16', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('145', '2018-12-16', '/', '/', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('146', '2018-12-16', '/', '/', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('147', '2018-12-16', '/', '/', 'user', '0:0:0:0:0:0:0:1', '');
INSERT INTO `ta_visit_log` VALUES ('148', '2018-12-16', '/student', '/student', 'user', '0:0:0:0:0:0:0:1', '');
