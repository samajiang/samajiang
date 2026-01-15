/*
 Navicat Premium Data Transfer

 Source Server         : VACCINE
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : vaccinedb

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 15/01/2026 16:34:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gonggaoinfo
-- ----------------------------
DROP TABLE IF EXISTS `gonggaoinfo`;
CREATE TABLE `gonggaoinfo`  (
  `gonggao_id` int NOT NULL AUTO_INCREMENT,
  `gonggao_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `gonggao_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`gonggao_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gonggaoinfo
-- ----------------------------
INSERT INTO `gonggaoinfo` VALUES (1, 'dwadw', '1744110313881');
INSERT INTO `gonggaoinfo` VALUES (3, 'hdukwagdwau', '1745001612660');
INSERT INTO `gonggaoinfo` VALUES (5, 'dhghb', '1746698276499');

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `order_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `order_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `order_vaccine` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `order_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES (36, 'ldd', '陈子浩', '2024.5.3', '43242', 'dwadwd', NULL);
INSERT INTO `orderinfo` VALUES (37, '李丹丹', '陈子浩', '2024.7.6', '1231245345', '狂犬疫苗', NULL);
INSERT INTO `orderinfo` VALUES (42, '浪费发给', 'ldd', '2025.6.5', '556587', 'dwadwd', '深圳市龙岗区人民医院');
INSERT INTO `orderinfo` VALUES (43, '是的个好办法', 'ldd', '2025.3.6', '855666', 'dwadwd', '南方科技大学医院');
INSERT INTO `orderinfo` VALUES (45, '不许出差', 'ldd', '586', '466868', '狂犬疫苗', '深圳市龙岗区人民医院');
INSERT INTO `orderinfo` VALUES (54, '高防御', 'ldd', '2022.3.6', '12658455', '狂犬疫苗', '广西医科大学第二附属医院');
INSERT INTO `orderinfo` VALUES (55, '程胜军', 'aaa', '5757376', '664754', '狂犬疫苗', '广西医科大学第二附属医院');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `root` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '否',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES (1, 'ldd', '111', '女', 100, '123', '/images/41274df7-acf9-4cc4-a84a-e004f2e9eb1a.jpg', '是');
INSERT INTO `userinfo` VALUES (2, 'FVHGHJV', '123456', '女', 67, '57658596', NULL, '否');
INSERT INTO `userinfo` VALUES (3, 'chenzihao', '123456', '男', 21, '213213123', NULL, '否');
INSERT INTO `userinfo` VALUES (5, 'jgyjyg', '123456', '男', 34, '45435', NULL, '否');
INSERT INTO `userinfo` VALUES (6, 'dddd', '123456', '男', 21, '2132132', NULL, '否');
INSERT INTO `userinfo` VALUES (7, '陈子浩', '123', '男', 21, '222', '/images/07510503-131c-42d4-a38f-69421c841bf8.jpg', '否');
INSERT INTO `userinfo` VALUES (8, 'adminroot', '123456', '男', 22, '111111', NULL, '是');
INSERT INTO `userinfo` VALUES (9, 'samajiang', '123456', NULL, NULL, NULL, NULL, '是');
INSERT INTO `userinfo` VALUES (10, 'samajiang12138', '123456', NULL, NULL, NULL, NULL, '否');
INSERT INTO `userinfo` VALUES (11, 'samadan', '123', '男', 66, '77646', NULL, '否');
INSERT INTO `userinfo` VALUES (13, 'aaa', '123', '男', 666, '123', NULL, '否');
INSERT INTO `userinfo` VALUES (14, 'admin', '123456', '男', 666, '23434556', NULL, '是');

-- ----------------------------
-- Table structure for vaccineinfo
-- ----------------------------
DROP TABLE IF EXISTS `vaccineinfo`;
CREATE TABLE `vaccineinfo`  (
  `vaccine_id` int NOT NULL AUTO_INCREMENT,
  `vaccine_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `vaccine_datil` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `bianhao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `vaccine_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `vaccine_count` int NULL DEFAULT NULL,
  PRIMARY KEY (`vaccine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vaccineinfo
-- ----------------------------
INSERT INTO `vaccineinfo` VALUES (1, '狂犬疫苗', 'fdss', '3424eds', NULL, 88);
INSERT INTO `vaccineinfo` VALUES (3, '乙肝疫苗', 'dawdwadaw', 'abc', NULL, 33);
INSERT INTO `vaccineinfo` VALUES (4, '水痘疫苗', 'sefes', 'dawdwa', NULL, 31);
INSERT INTO `vaccineinfo` VALUES (5, '新冠疫苗', 'dgrg', 'hyy', NULL, 76);
INSERT INTO `vaccineinfo` VALUES (6, '小儿麻痹疫苗', 'fdhyhjyt', 'hfghgsrf', NULL, 88);
INSERT INTO `vaccineinfo` VALUES (7, '麻风疫苗', 'sdfsregr', 'bgfhft', NULL, 55);

SET FOREIGN_KEY_CHECKS = 1;
