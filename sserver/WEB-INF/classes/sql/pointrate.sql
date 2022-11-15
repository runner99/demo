/*
 Navicat Premium Data Transfer

 Source Server         : 云12
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mrworlds

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 04/06/2020 12:06:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pointrate
-- ----------------------------
DROP TABLE IF EXISTS `pointrate`;
CREATE TABLE `pointrate`  (
  `rate` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `attri1` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pointrate
-- ----------------------------
INSERT INTO `pointrate` VALUES (10, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
