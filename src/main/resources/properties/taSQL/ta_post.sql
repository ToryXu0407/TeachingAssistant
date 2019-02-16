/*
Navicat MySQL Data Transfer

Source Server         : 120.79.213.75
Source Server Version : 50557
Source Host           : 120.79.213.75:3306
Source Database       : db_ta

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2019-02-16 15:17:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ta_post
-- ----------------------------
DROP TABLE IF EXISTS `ta_post`;
CREATE TABLE `ta_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回帖id',
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL DEFAULT '0',
  `parent_post_id` int(11) NOT NULL DEFAULT '0' COMMENT '如果是回复某回帖则有父postId',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ta_post
-- ----------------------------
INSERT INTO `ta_post` VALUES ('1', '3', '11', '0', '0', '2019-01-12 14:44:27', null, '第一次评论有点小紧张', '1');
INSERT INTO `ta_post` VALUES ('2', '3', '11', '0', '0', '2019-01-12 14:45:27', null, '第二次评论', '2');
INSERT INTO `ta_post` VALUES ('3', '3', '11', '0', '0', '2019-01-13 18:17:58', null, '<p>guess what i just found<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\"></p>', '2');
INSERT INTO `ta_post` VALUES ('12', '3', '11', '0', '0', '2019-01-13 18:34:16', null, '<p>replay&nbsp;</p>', '2');
INSERT INTO `ta_post` VALUES ('13', '3', '11', '0', '0', '2019-01-13 18:35:51', null, '<p>add&nbsp;</p>', '2');
INSERT INTO `ta_post` VALUES ('14', '3', '11', '0', '0', '2019-01-13 18:35:55', null, '<p>add 2</p>', '2');
INSERT INTO `ta_post` VALUES ('15', '3', '11', '0', '0', '2019-01-13 18:35:58', null, '<p>add 3</p>', '2');
INSERT INTO `ta_post` VALUES ('16', '3', '11', '0', '0', '2019-01-13 18:36:02', null, '<p>add 4</p>', '2');
INSERT INTO `ta_post` VALUES ('17', '3', '11', '0', '0', '2019-01-13 18:36:06', null, '<p>add 5</p>', '2');
INSERT INTO `ta_post` VALUES ('18', '3', '11', '0', '0', '2019-01-13 18:36:10', null, '<p>add 6</p>', '2');
INSERT INTO `ta_post` VALUES ('19', '3', '11', '0', '0', '2019-01-13 18:36:16', null, '<p>add 7</p>', '2');
INSERT INTO `ta_post` VALUES ('20', '1', '11', '0', '0', '2019-01-15 11:49:05', null, '<p>wq dq&nbsp;</p>', '1');
INSERT INTO `ta_post` VALUES ('21', '11', '11', '0', '0', '2019-01-15 11:56:25', null, '<p>asd</p>', '2');
INSERT INTO `ta_post` VALUES ('49', '11', '3', '0', '0', '2019-01-15 16:48:35', null, '<p>冒个泡</p>', '3');
INSERT INTO `ta_post` VALUES ('50', '11', '3', '0', '0', '2019-01-15 16:52:40', null, '<p>冒泡+1</p>', '4');
INSERT INTO `ta_post` VALUES ('51', '11', '3', '0', '0', '2019-01-15 16:52:45', null, '<p>冒泡+2&nbsp;<br></p>', '5');
INSERT INTO `ta_post` VALUES ('52', '11', '3', '0', '0', '2019-01-15 16:52:49', null, '<p>冒泡+3&nbsp;<br></p>', '6');
INSERT INTO `ta_post` VALUES ('53', '11', '3', '0', '0', '2019-01-15 16:53:00', null, '<p>冒泡+4&nbsp;<br></p>', '7');
INSERT INTO `ta_post` VALUES ('54', '11', '3', '0', '0', '2019-01-15 16:53:05', null, '<p>冒泡+5&nbsp;<br></p>', '8');
INSERT INTO `ta_post` VALUES ('55', '11', '3', '0', '0', '2019-01-15 16:53:14', null, '<p>冒泡+6&nbsp;<br></p>', '9');
INSERT INTO `ta_post` VALUES ('56', '11', '3', '0', '0', '2019-01-15 16:53:18', null, '<p>冒泡+7&nbsp;<br></p>', '10');
INSERT INTO `ta_post` VALUES ('57', '11', '3', '0', '0', '2019-01-15 16:53:22', null, '<p>冒泡+8&nbsp;<br></p>', '11');
INSERT INTO `ta_post` VALUES ('58', '11', '3', '0', '0', '2019-01-15 16:53:29', null, '<p>冒泡+9&nbsp;<br></p>', '12');
INSERT INTO `ta_post` VALUES ('86', '11', '3', '3', '21', '2019-01-15 22:12:53', null, 'gaga', '13');
INSERT INTO `ta_post` VALUES ('87', '11', '3', '3', '49', '2019-01-15 22:12:57', null, 'gaga', '14');
INSERT INTO `ta_post` VALUES ('88', '11', '3', '3', '49', '2019-01-15 22:13:01', null, 'gagagaga', '15');
INSERT INTO `ta_post` VALUES ('89', '11', '11', '3', '21', '2019-01-15 22:14:37', null, 'dasdas', '16');
INSERT INTO `ta_post` VALUES ('90', '11', '11', '11', '21', '2019-01-15 22:19:43', null, 'dassda', '17');
INSERT INTO `ta_post` VALUES ('91', '11', '11', '11', '57', '2019-01-15 22:23:04', null, 'dasd', '18');
INSERT INTO `ta_post` VALUES ('92', '11', '11', '0', '0', '2019-01-15 22:23:19', null, '<p>dsad</p>', '19');
INSERT INTO `ta_post` VALUES ('93', '11', '11', '0', '0', '2019-01-15 22:24:15', null, '<p>dasdsa</p>', '20');
INSERT INTO `ta_post` VALUES ('94', '11', '11', '11', '49', '2019-01-15 22:25:55', null, 'das', '21');
INSERT INTO `ta_post` VALUES ('95', '11', '11', '11', '49', '2019-01-15 22:26:42', null, 'dasd', '22');
INSERT INTO `ta_post` VALUES ('96', '11', '11', '11', '49', '2019-01-15 22:36:05', null, 'dadsa', '23');
INSERT INTO `ta_post` VALUES ('97', '11', '11', '11', '49', '2019-01-15 22:41:47', null, 'sda', '24');
INSERT INTO `ta_post` VALUES ('98', '11', '11', '11', '49', '2019-01-15 22:42:24', null, 'dasda', '25');
INSERT INTO `ta_post` VALUES ('99', '11', '11', '11', '49', '2019-01-15 22:42:52', null, 'dasda', '26');
INSERT INTO `ta_post` VALUES ('100', '11', '11', '11', '49', '2019-01-15 22:43:23', null, 'dasd', '27');
INSERT INTO `ta_post` VALUES ('101', '11', '11', '11', '50', '2019-01-15 22:44:29', null, 'das', '28');
INSERT INTO `ta_post` VALUES ('102', '11', '11', '11', '51', '2019-01-15 22:45:00', null, 'asd', '29');
INSERT INTO `ta_post` VALUES ('103', '11', '11', '11', '50', '2019-01-15 22:45:06', null, 'dsadas', '30');
INSERT INTO `ta_post` VALUES ('104', '11', '11', '11', '50', '2019-01-15 22:45:13', null, 'dsadas', '31');
INSERT INTO `ta_post` VALUES ('105', '11', '11', '11', '50', '2019-01-15 22:45:45', null, 'das', '32');
INSERT INTO `ta_post` VALUES ('106', '11', '11', '11', '51', '2019-01-15 22:45:54', null, 'asd', '33');
INSERT INTO `ta_post` VALUES ('107', '11', '11', '11', '51', '2019-01-15 22:45:57', null, 'asdasd', '34');
INSERT INTO `ta_post` VALUES ('108', '11', '11', '11', '50', '2019-01-15 22:46:49', null, '大师大的撒', '35');
INSERT INTO `ta_post` VALUES ('109', '11', '11', '3', '21', '2019-01-15 22:47:53', null, '大声大声道', '36');
INSERT INTO `ta_post` VALUES ('110', '11', '11', '11', '21', '2019-01-15 22:47:58', null, '大大', '37');
INSERT INTO `ta_post` VALUES ('111', '11', '11', '11', '21', '2019-01-15 22:48:02', null, '大大', '38');
INSERT INTO `ta_post` VALUES ('112', '11', '11', '0', '0', '2019-01-21 00:12:47', null, '<p>dsaasdsa<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\"></p>', '39');
