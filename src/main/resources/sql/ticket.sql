/*
 Navicat Premium Data Transfer

 Source Server         : chan
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : localhost
 Source Database       : train

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : utf-8

 Date: 02/09/2017 11:20:28 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_users`
-- ----------------------------
DROP TABLE IF EXISTS `admin_users`;
CREATE TABLE `admin_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `sha_password` varchar(255) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `card_no` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `admin_users`
-- ----------------------------
BEGIN;
INSERT INTO `admin_users` VALUES ('1', '2017-01-23 15:24:11', 'e10adc3949ba59abbe56e057f20f883e', '2017-02-09 11:11:30', 'admin', '111', '11', 'chenli', '11111'), ('3', '2017-02-09 10:49:06', '698d51a19d8a121ce581499d7b701668', '2017-02-09 11:01:03', 'cdt', '123123', '12@qq.com', '陈德婷1', '123123');
COMMIT;

-- ----------------------------
--  Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `admin_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6aomm4hku6y1hu8nculyhhsq4` (`admin_user_id`),
  CONSTRAINT `FK_6aomm4hku6y1hu8nculyhhsq4` FOREIGN KEY (`admin_user_id`) REFERENCES `admin_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `roles`
-- ----------------------------
BEGIN;
INSERT INTO `roles` VALUES ('1', '2017-01-23 15:24:55', '0', '2017-01-23 15:25:01', '1'), ('2', '2017-02-09 10:49:06', '0', null, '3');
COMMIT;

-- ----------------------------
--  Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_no` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `departure` varchar(255) DEFAULT NULL,
  `departure_date` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `train_no` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `seat_type` int(11) DEFAULT NULL,
  `train_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_order`
-- ----------------------------
BEGIN;
INSERT INTO `t_order` VALUES ('9', '3213123', '2017-02-09 10:56:39', '成都东', '2017-02-09 11:34:05', '重庆', '陈立', '13567854588', 'D2260', null, '1', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `train`
-- ----------------------------
DROP TABLE IF EXISTS `train`;
CREATE TABLE `train` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `hard_seat` int(11) DEFAULT NULL,
  `hard_sleeper` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `no_seat` int(11) DEFAULT NULL,
  `soft_seat` int(11) DEFAULT NULL,
  `soft_sleeper` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `train`
-- ----------------------------
BEGIN;
INSERT INTO `train` VALUES ('1', '2017-02-07 11:33:02', '50', '19', 'D2260', '100', '30', '38', '2017-02-07 11:33:06');
COMMIT;

-- ----------------------------
--  Table structure for `trips`
-- ----------------------------
DROP TABLE IF EXISTS `trips`;
CREATE TABLE `trips` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `departure` varchar(255) DEFAULT NULL,
  `departure_date` datetime DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `train_no` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `train_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_430osrdi5t2xoaeqjf4s5rhc6` (`train_id`),
  CONSTRAINT `FK_430osrdi5t2xoaeqjf4s5rhc6` FOREIGN KEY (`train_id`) REFERENCES `train` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `trips`
-- ----------------------------
BEGIN;
INSERT INTO `trips` VALUES ('1', '2017-02-07 11:33:50', '成都东', '2017-02-09 11:34:05', '重庆', 'D2260', '2017-02-07 13:34:35', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
