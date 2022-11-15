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

 Date: 04/06/2020 12:00:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for systemconfig
-- ----------------------------
DROP TABLE IF EXISTS `systemconfig`;
CREATE TABLE `systemconfig`  (
  `field` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fieldValue` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`field`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of systemconfig
-- ----------------------------
INSERT INTO `systemconfig` VALUES ('DEFAULTMIXERPRODUCTID', '1104');
INSERT INTO `systemconfig` VALUES ('DEFAULTPLUSSOFTWARE', '1103');
INSERT INTO `systemconfig` VALUES ('DEFAULTSOFTWAREID', '1102');
INSERT INTO `systemconfig` VALUES ('DOWNLOADSOFTWARETTYPE', '7');
INSERT INTO `systemconfig` VALUES ('PRODUCTDEFAULTID', '95');
INSERT INTO `systemconfig` VALUES ('UNLOCKVIDEOPRODUCTID', '110');
INSERT INTO `systemconfig` VALUES ('UNLOCKVIDEOPRODUCTTYPE', '6');

SET FOREIGN_KEY_CHECKS = 1;
