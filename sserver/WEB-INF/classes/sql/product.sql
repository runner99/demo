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

 Date: 04/06/2020 12:06:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` int(11) NOT NULL,
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `icon` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `video` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` decimal(10, 2) DEFAULT NULL,
  `purchase_quantity` int(11) DEFAULT NULL,
  `providerId` int(11) DEFAULT NULL,
  `point_limit` int(11) DEFAULT NULL,
  `gift_point` int(11) DEFAULT NULL,
  `product_type` int(11) DEFAULT NULL COMMENT '0 -素材\n            1-作品',
  `referenceId` int(11) DEFAULT NULL,
  `average_mark` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `product_cata` int(11) DEFAULT NULL,
  `product_tag` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `guidance` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `attribuation1` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `point` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (3, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `product` VALUES (62, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"ca00272d7dd0b0f5687adda36e75b815\",\"spaceID\":\"102\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (65, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"d2df88bf1e67c5289532bdc4d46afa03\",\"spaceID\":\"100\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (66, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"c6f038031a28d150c54abda71316f922\",\"spaceID\":\"100\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (67, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"c6f038031a28d150c54abda71316f922\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (68, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"dacc9f27f2ebe97e93754c7d38a508f3\",\"spaceID\":\"100\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (69, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"dacc9f27f2ebe97e93754c7d38a508f3\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (70, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"1aa0c978beeae846b19946b4d6addae6\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (71, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"a6b8b309a64d0161caa5bde05a9fe5e0\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (72, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"e69a5098e0bdacb3a942a2fe0ddad942\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (73, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"1aaff4278022e389d254f628ea7a4cb5\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (74, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (75, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"2a802d8dc1b1e9bb7fc59ed4befc2380\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (76, NULL, NULL, NULL, NULL, 10.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"65d345c103d4792f38e7597701c3a9fe\",\"spaceID\":\"148\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (77, NULL, NULL, NULL, NULL, 20.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"46bec5a567be8fb3873f5a559068e360\",\"spaceID\":\"117\",\"serverID\":\"4\"}', '170');
INSERT INTO `product` VALUES (78, NULL, NULL, NULL, NULL, 20.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"46bec5a567be8fb3873f5a559068e360\",\"spaceID\":\"148\",\"serverID\":\"4\"}', '200');
INSERT INTO `product` VALUES (79, NULL, NULL, NULL, NULL, 5.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"d3bf63e469ee7aa0158a7edc66b89e35\",\"spaceID\":\"148\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (80, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (81, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (82, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (83, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (84, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (85, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (86, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (87, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (88, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"acc5c82d80b3dace51fbbc1b51976314\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (89, NULL, NULL, NULL, NULL, 6.66, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"3736acd82561a016db209c9ebea51427\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (90, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"fa9625312fedbb432824a9b3682eebb3\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (91, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"a0e99668cb74f37f9c414d8ff3f8431e\",\"spaceID\":\"126\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (92, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"fd846388357b812f990400d47689c5c9\",\"spaceID\":\"103\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (93, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"6584cad859f393ead4e7f8f2934e2ef1\",\"spaceID\":\"103\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (94, NULL, NULL, NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"a70c569f93856c1a62554b5be387b7bb\",\"spaceID\":\"117\",\"serverID\":\"4\"}', NULL);
INSERT INTO `product` VALUES (95, NULL, '1积分', NULL, NULL, 0.10, NULL, NULL, NULL, NULL, 6, NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `product` VALUES (98, NULL, NULL, NULL, NULL, 6.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"1aa0c978beeae846b19946b4d6addae6\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '60');
INSERT INTO `product` VALUES (99, NULL, NULL, NULL, NULL, 18.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"2b605e07601b1efe89c51107bee8d8f2\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '180');
INSERT INTO `product` VALUES (100, NULL, NULL, NULL, NULL, 58.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"8f0574d806211cbd8fe9ee0fd82b5344\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '580');
INSERT INTO `product` VALUES (101, NULL, NULL, NULL, NULL, 38.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"0ee5f5e6d5535a49e414752e8de409fa\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '380');
INSERT INTO `product` VALUES (102, NULL, NULL, NULL, NULL, 58.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"fa9625312fedbb432824a9b3682eebb3\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '580');
INSERT INTO `product` VALUES (103, NULL, NULL, NULL, NULL, 68.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"86c2c03030ddc191409f2eceb8ce914c\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '680');
INSERT INTO `product` VALUES (104, NULL, NULL, NULL, NULL, 98.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"58c19f73c1ba472715e8cf4fc458e084\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '1980');
INSERT INTO `product` VALUES (105, NULL, NULL, NULL, NULL, 198.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"be51721ad8d8d0cff1b54ccc92230e6d\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '1980');
INSERT INTO `product` VALUES (106, NULL, NULL, NULL, NULL, 598.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"7891858fa4b2e9f9f11da5375331312f\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '5980');
INSERT INTO `product` VALUES (107, NULL, NULL, NULL, NULL, 198.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"cbf8128dd02593cdf6813036c6ddbd83\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '1980');
INSERT INTO `product` VALUES (108, NULL, NULL, NULL, NULL, 28.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"76f42b177c63b2f0c3cc657a7aa01e8d\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '280');
INSERT INTO `product` VALUES (109, NULL, NULL, NULL, NULL, 100.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '{\"worksID\":\"5e23906d6b453208ec7daeb9f6a007c2\",\"spaceID\":\"49\",\"serverID\":\"3\"}', '1000');
INSERT INTO `product` VALUES (110, NULL, NULL, NULL, NULL, 1.00, NULL, NULL, NULL, NULL, 6, NULL, NULL, NULL, NULL, NULL, '{\"num\":\"1\"}', '10');
INSERT INTO `product` VALUES (1102, '普通会员', '满足基本影像合成、绿幕抠像及虚拟演播间使用功能', NULL, '', 0.00, NULL, NULL, NULL, NULL, 7, NULL, NULL, NULL, NULL, NULL, '{\"color\":\"#32E2D4\",\"color1\":\"#88DFD8\",\"original_price\":\"1980\",\"productName\":\"Mixer-Public\",\"download_authority\":\"1001\",\"active_authority\":\"0002\"}', '1');
INSERT INTO `product` VALUES (1103, '白银会员', '在3D虚拟直播间中允许虚拟背景、植入素材及文字替换等个性化设置', NULL, NULL, 0.01, NULL, NULL, NULL, NULL, -7, NULL, NULL, NULL, NULL, NULL, '{\"color\":\"#1768E1\",\"color1\":\"#8AB2FC\",\"original_price\":\"4980\",\"productName\":\"Mixer-Plus\",\"download_authority\":\"1002\",\"active_authority\":\"0002\"}', '29800');
INSERT INTO `product` VALUES (1104, '黄金会员', '允许在3D虚拟直播间中提供产品营销，允许网红带货、文档展示及分享等高级功能', NULL, NULL, 0.01, NULL, NULL, NULL, NULL, 7, NULL, NULL, NULL, NULL, NULL, '{\"color\":\"#D5A27F\",\"color1\":\"#C3B0A3\",\"original_price\":\"8980\",\"productName\":\"Mixer\",\"download_authority\":\"1003\",\"active_authority\":\"0002\"}', '69800');

SET FOREIGN_KEY_CHECKS = 1;
