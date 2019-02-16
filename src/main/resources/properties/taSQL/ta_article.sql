/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_article
-- ----------------------------
DROP TABLE IF EXISTS `ta_article`;
CREATE TABLE `ta_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '文章创建时间',
  `update_time` datetime DEFAULT NULL,
  `is_sticky` varchar(255) NOT NULL COMMENT '是否加精',
  `label` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `brief` varchar(255) NOT NULL,
  `view_count` int(11) NOT NULL DEFAULT '0',
  `comment_count` int(11) NOT NULL DEFAULT '0',
  `vote_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_article
-- ----------------------------
INSERT INTO `ta_article` VALUES ('1', '11', '2019-01-03 14:19:13', null, 'Y', 'number1', 'number1 article', '第一篇帖子', '111', '1', '0');
INSERT INTO `ta_article` VALUES ('10', '11', '2019-01-14 18:12:59', null, 'N', 'fatie test7', '<p>fatie test1 fatie test1&nbsp;</p>', '<p>fatie test1 fatie', '0', '0', '0');
INSERT INTO `ta_article` VALUES ('11', '11', '2019-01-14 18:13:08', null, 'N', 'fatie test8', '<p>fatie test1 fatie test1&nbsp;</p>', '<p>fatie test1 fatie', '5', '78', '1');
