SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del` bit(1) NULL DEFAULT NULL COMMENT '是否删除，0、false 表示未删除',
  `description` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用描述',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_88vfgccvckwwip06k7tpf8uk3`(`name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app` VALUES (1, '2020-03-08 13:53:04', b'0', 'demo测试', 'demo', '2020-03-08 16:35:27');

-- ----------------------------
-- Table structure for plugin
-- ----------------------------
DROP TABLE IF EXISTS `plugin`;
CREATE TABLE `plugin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件地址',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件类名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del` bit(1) NULL DEFAULT NULL COMMENT '是否删除，0、false 表示未删除',
  `description` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件描述',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件名称',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_6mmx0wrhrev0ss1ky7jpo50e`(`class_name`) USING BTREE,
  UNIQUE INDEX `UK_eirpprluebvpjt0rl4i2er6ow`(`name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plugin
-- ----------------------------
INSERT INTO `plugin` VALUES (1, 'D:\\\\workspace\\\\github-lushstar\\\\pagoda\\\\site\\\\pagoda-embed-plugin-1.0.0-SNAPSHOT.jar', 'pers.masteryourself.lushstar.pagoda.sample.embed.plugin.ReturnPlugin', '2020-03-08 14:32:53', b'0', '打印返回值信息', 'ReturnPlugin', '2020-03-08 16:36:25');
INSERT INTO `plugin` VALUES (2, 'D:\\\\workspace\\\\github-lushstar\\\\pagoda\\\\site\\\\pagoda-embed-plugin-1.0.0-SNAPSHOT.jar', 'pers.masteryourself.lushstar.pagoda.sample.embed.plugin.ParamPlugin', '2020-03-08 14:33:34', b'0', '打印入参信息', 'ParamPlugin', '2020-03-08 14:42:27');

-- ----------------------------
-- Table structure for app_plugin
-- ----------------------------
DROP TABLE IF EXISTS `app_plugin`;
CREATE TABLE `app_plugin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NULL DEFAULT NULL COMMENT '是否激活，0、false 表示未激活, null 表示刚刚 install',
  `app_id` bigint(20) NULL DEFAULT NULL COMMENT '应用 id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del` bit(1) NULL DEFAULT NULL COMMENT '是否删除，0、false 表示未删除',
  `plugin_id` bigint(20) NULL DEFAULT NULL COMMENT '插件 id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of app_plugin
-- ----------------------------
INSERT INTO `app_plugin` VALUES (1, b'1', 1, '2020-03-08 18:49:02', b'0', 1, '2020-03-08 18:51:07');
INSERT INTO `app_plugin` VALUES (2, b'1', 1, '2020-03-08 18:49:03', b'0', 2, '2020-03-08 18:49:16');

SET FOREIGN_KEY_CHECKS = 1;