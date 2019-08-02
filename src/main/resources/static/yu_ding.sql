/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : woaipinche

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 19/07/2019 17:26:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yu_ding
-- ----------------------------
DROP TABLE IF EXISTS `yu_ding`;
CREATE TABLE `yu_ding`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `end_point` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `owner` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `price` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remaining_seats` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `set_out_date` datetime NULL DEFAULT NULL,
  `start_point` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of yu_ding
-- ----------------------------
INSERT INTO `yu_ding` VALUES ('0727d17c-0a1c-47f2-9e65-d89aa9565f51', '巴中', '1', '1', '15174361531', '1', '1', '1', '2019-07-19 17:08:00', '成都');
INSERT INTO `yu_ding` VALUES ('1032e6b1-df75-417a-98f8-7a9530abdc02', '巴中', '1', '1', '15174361531', '100', '1', '1', '2019-07-19 18:10:00', '成都');
INSERT INTO `yu_ding` VALUES ('1439d6c0-955a-4261-ae6b-3b87140c2255', '巴中', '1', '1', '15174361531', '1', '1', '1', '2019-07-19 17:08:00', '成都');
INSERT INTO `yu_ding` VALUES ('1ceff279-996a-4629-b6b0-3fb6c44c5746', '巴中', '1', '1', '15174361531', '100', '1', '1', '2019-07-19 18:10:00', '成都');
INSERT INTO `yu_ding` VALUES ('337ff4d2-85b8-4a64-aa34-1112da0ae4d5', '巴中', '1', '1', '15174361531', '1', '1', '1', '2019-07-19 17:08:00', '成都');

SET FOREIGN_KEY_CHECKS = 1;
