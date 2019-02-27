/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-02-27 21:14:55
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_user
-- ----------------------------
INSERT INTO `ta_user` VALUES ('1', 'admin', 'acb7a76895cfe68529a82a66c516ea7c', 'admin', '0', '2018-10-15', null);
INSERT INTO `ta_user` VALUES ('2', 'toryxu', '580f4a3ecfe7486e9827023a432716ca', '', '2', '2018-10-15', null);
INSERT INTO `ta_user` VALUES ('3', 'jakexu', '580f4a3ecfe7486e9827023a432716ca', 'jakexu', '2', '2018-10-17', 'http://oss.yiqihappy.top/de1fcb647b5ea2a9d28e40d087575adb1.jpg');
INSERT INTO `ta_user` VALUES ('11', 'user', '580f4a3ecfe7486e9827023a432716ca', 'toryxu', '2', '2018-10-22', 'http://oss.yiqihappy.top/10c2eb0f2be96bc1ae27bec2852f8e58.jpg');
INSERT INTO `ta_user` VALUES ('14', 'teacher1', '0b68198d9eea47129009fd4114b07e97', '许佳俊', '1', '2019-01-16', 'http://oss.yiqihappy.top/de1fcb647b5ea2a9d28e40d087575adb1.jpg');
INSERT INTO `ta_user` VALUES ('15', '321', '13f881b6a10886f491998bc0b3979b2c', '321', '1', '2019-02-13', null);
