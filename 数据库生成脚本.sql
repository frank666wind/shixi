/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 22/09/2020 10:22:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `orderid` int NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `longitude` float NULL DEFAULT NULL,
  `latitude` float NULL DEFAULT NULL,
  `complete` int NOT NULL DEFAULT 0 COMMENT '0表示订单未完成，1表示完成',
  PRIMARY KEY (`orderid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 'zhangsan', '李四', '北京工业大学', '13066668888', 116.479, 39.8919, 1);
INSERT INTO `order` VALUES (2, 'xiaoming', '张三', '松榆南里', '13388886666', 116.479, 39.8919, 1);
INSERT INTO `order` VALUES (3, 'zhangsan', '小明', '北京工业大学', '13255550000', 116.479, 39.8919, 1);
INSERT INTO `order` VALUES (4, 'zhangsan', '小红', '合生汇', '13300001111', NULL, NULL, 0);
INSERT INTO `order` VALUES (5, 'zhangsan', '小刚', '平乐园99号', '13177778888', 116.479, 39.8919, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` int NOT NULL COMMENT '0为快递员，1为管理员',
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'rootroot', 1);
INSERT INTO `user` VALUES ('root', 'rootroot', 1);
INSERT INTO `user` VALUES ('xiaoming', '12345678', 0);
INSERT INTO `user` VALUES ('zhangsan', '12345678', 0);

SET FOREIGN_KEY_CHECKS = 1;
