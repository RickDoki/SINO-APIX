/*
 Navicat Premium Data Transfer

 Source Server         : 47.103.109.225（sino apix）
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 47.103.109.225:30306
 Source Schema         : apix_auth

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/01/2022 15:52:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for client_app_secret
-- ----------------------------
DROP TABLE IF EXISTS `client_app_secret`;
CREATE TABLE `client_app_secret`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL COMMENT 'sys_client_id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT 'sys_user_id',
  `app_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'application_code',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端申请token的secretKey',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client_app_secret
-- ----------------------------

-- ----------------------------
-- Table structure for gateway_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `gateway_blacklist`;
CREATE TABLE `gateway_blacklist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '黑名单类型',
  `content` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for login_history
-- ----------------------------
DROP TABLE IF EXISTS `login_history`;
CREATE TABLE `login_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `login_time` datetime(0) NOT NULL,
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_history
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('04f230b1-1234444', NULL, '$2a$10$4HDQzBhy7cAF1uYBRNKqvuu2bqDRAYSDL6lg9ypxIidiyz8h0Auwq', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1234444\",\"lesseeCode\":\"04f230b1\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('0a3a9517-1234444', NULL, '$2a$10$rllZPaYIf4kmvn3G0dg27./PuVzx/bxXhZsyL8FMfn0c621f7QPAe', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1234444\",\"lesseeCode\":\"0a3a9517\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('1234-hwdtest03', NULL, '$2a$10$Ea/rSJwv7F8lplfQVTk0P.giNaHVbm48W9Ty4ilGbAYm6foGys36.', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"hwdtest03\",\"lesseeCode\":\"1234\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('1234444-1223', NULL, '$2a$10$66QrbJnWXYMJMQeQ6MKEOO0TLoqPzAP6jmqOm1ZeVlm7m1rE93r9a', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"1234444\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('1234444-1234444', NULL, '$2a$10$YqZflTDjdA6lM7OQIY8saOaz1lIWzPdROEUKng8CBrhvZByFRpeiO', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1234444\",\"lesseeCode\":\"1234444\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('1234444-6543', NULL, '$2a$10$Yo/meZ5XLLQ/5Xc2rXMeje8R81WkQleiUmF6m0xQqm4vcQxkzQDzK', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"6543\",\"lesseeCode\":\"1234444\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('1234444-d90de077', NULL, '$2a$10$se/f1nFhhtyy/gyoIt0hE.fbevAYM0bVgEnVRihrNzfNONosd2OVS', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"d90de077\",\"lesseeCode\":\"1234444\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('1234444-hwdtest03', NULL, '$2a$10$7ZI9A2rxQFMB5WYsjJ1F0.rJH344eUM.k0fsLA4x3a8om9mjW0zgG', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"hwdtest03\",\"lesseeCode\":\"1234444\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('12a3a180-12a3a180', NULL, '$2a$10$WIkBFHurJkkXIBlS/hG5teyspgDtvNGoWOK9.7vS39FgI/Lu3gRLG', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"12a3a180\",\"lesseeCode\":\"12a3a180\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('47b2c1a7-d0598f8c', NULL, '$2a$10$3ze8ZVs9ZuFb08pnZyxIlOqY.3GLAknkc1gbwZElzsGmpo.MdCNwS', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"d0598f8c\",\"lesseeCode\":\"47b2c1a7\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('4b8b4956-app_2', NULL, '$2a$10$Y/GcyvYEB1E5stxdshFcueZBAh5pwPNtyfMYqPKIHEKMXYOzcpFQK', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"app_2\",\"lesseeCode\":\"4b8b4956\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('4b8b4956-d90de077', NULL, '$2a$10$fPTlKXU/xnKlo/akNLR.9.Gkzuq0b2RTpmSg0dHcUTC.2nxxInJSO', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"d90de077\",\"lesseeCode\":\"4b8b4956\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('50530ac9-12a3a180', NULL, '$2a$10$PJ86pSjzR60fQRgupFDLquHhz2c29dvditDaMxHAD9IHqA/pXGV/S', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"12a3a180\",\"lesseeCode\":\"50530ac9\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('50530ac9-580e7e33', NULL, '$2a$10$P99pafqV2.aqIYdv8.sofeCcPK.R1WD2aWEXZfrIHpHmj0v3N1R6a', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"580e7e33\",\"lesseeCode\":\"50530ac9\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6178ea28-1223', NULL, '$2a$10$V6Dp7r0YeXUhjBq5VHFLyu2sZ.coiLL7ZvLZbJVEgCDLNRD9PyoTW', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"6178ea28\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6178ea28-6178ea28', NULL, '$2a$10$vQUzP41gYSZdoGVZ76pZh.UzSAdsdYeD9uP0B9jF19VRvTbF0CBR.', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"6178ea28\",\"lesseeCode\":\"6178ea28\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6495068e-1223', NULL, '$2a$10$u44IBEcMqmKHZh62uSaeau/Lf3sge.CjHKfimGodUGUtTaZzQLJGC', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"6495068e\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6543-1223', NULL, '$2a$10$7YNd28iZvPEeYkG3kRUS/.KTAUiiVAcl8T02wm/2TIVLFat9IuJLS', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"6543\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6543-1234444', NULL, '$2a$10$ISlaTfIKzkb0/7DR/rmXxe0jCFNJ12.ev1azqNTrp7At1saoA5FPO', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1234444\",\"lesseeCode\":\"6543\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6543-6543', NULL, '$2a$10$M4zfa2FdyzWHfYXeNAA.8ev6J0mg0XrGxHRkyafc1Cq1v.mXSKUwW', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"6543\",\"lesseeCode\":\"6543\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('6543-d90de077', NULL, '$2a$10$cwUB9uey5uqukWZAiR9ZN.t7Y4ohIObO7nloaNukL3hEtf.MlDLBi', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"d90de077\",\"lesseeCode\":\"6543\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('7117160b-7117160b', NULL, '$2a$10$0EZ8ItUFOeZUnB3V1Rz39efs/zRVhzCAxAkLzeZW4CwwQM1BgLFEm', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"7117160b\",\"lesseeCode\":\"7117160b\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('768a263c-d0598f8c', NULL, '$2a$10$xEDTC8FD.Kct1rl6nmt4seKLOT.P79ZOn/s2SJyJWaDDViFni6SDC', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"d0598f8c\",\"lesseeCode\":\"768a263c\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('965b6cb5-1223', NULL, '$2a$10$7NWmlMNn/ni.ZXjQhS7BW.F8kMNhWpfjqD5aqm5LQPZGltnU/TdZq', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"965b6cb5\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('965b6cb5-965b6cb5', NULL, '$2a$10$9MfGs3Tok2qztI.jsVugwe5bFvD0jBl0Dt52bF4dwGITEDxIz7f9i', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"965b6cb5\",\"lesseeCode\":\"965b6cb5\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('9720980b-1223', NULL, '$2a$10$bJux4411Vh8XpI0pTBsnjeD/2xEy6onvVjOpi0Y1Un9KLGe0rHVRu', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"9720980b\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('admin_client', NULL, '$2a$10$j9a2QX1GlGpKIYrrELjX1OJxeiJhs410P0WAsZmXFmrCTEfCAz6eS', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 2047483647, 2147483647, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('ai', '', '$2a$10$s/nYMcbZ9o48L.YDtfAF3O35XVyAn1VMG5G8DPvVMEvxGcdMotwOK', 'all', 'client_credentials,password,refresh_token', '', '', 7200, 36000, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('api_3-1234444', NULL, '$2a$10$t81RrOYUY7NgIF3SQhkhyeIuVkeNNoICdPY9oMxF3ZFlueeGrLaJe', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1234444\",\"lesseeCode\":\"api_3\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('app_2-6543', NULL, '$2a$10$6OsZZCuYBnLZG9oWm6bycOGb1B6vgndfzSUJFnbLyc8lE4JRc7xQG', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"6543\",\"lesseeCode\":\"app_2\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('app_2-7117160b', NULL, '$2a$10$IpF5GA.0ZcFl4kAmZ3OMauS.HalrmEv0AxA03zJ7gl5hluN2mFQZi', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"7117160b\",\"lesseeCode\":\"app_2\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('client_1', '', '$2a$10$rly1Yd3oJQ/uZqlUmjJetemXsmA1hKxbYCWIekpNWvRiEHtOMb4ka', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('compkg', NULL, '$2a$10$s/nYMcbZ9o48L.YDtfAF3O35XVyAn1VMG5G8DPvVMEvxGcdMotwOK', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('d36295fe-fc207218', NULL, '$2a$10$7xzB67zMnUBV5kSV8cAc5eM/TCABwa.9VdgRmF30.8TKbwCYTtc5C', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"fc207218\",\"lesseeCode\":\"d36295fe\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('d90de077-1223', NULL, '$2a$10$IcoorsO5zb5J4hvuCa.Ye.wXh/XenasAMSTTlDqmsIv5rRN/XdmpO', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"1223\",\"lesseeCode\":\"d90de077\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('d90de077-6543', NULL, '$2a$10$qAePEXv7sc9z0G0uTDql3.MkKz.3b92IBF67p7ZmxvmIy84QA7vP2', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"6543\",\"lesseeCode\":\"d90de077\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('d90de077-d90de077', NULL, '$2a$10$7qc8r4l2A6V6MyTf3XpWZevnLcmLJIEdGqOfpMq1auiNHCDZ5dUr2', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"d90de077\",\"lesseeCode\":\"d90de077\"}', NULL);
INSERT INTO `oauth_client_details` VALUES ('dbaas', NULL, '$2a$10$s/nYMcbZ9o48L.YDtfAF3O35XVyAn1VMG5G8DPvVMEvxGcdMotwOK', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('hwdtest04-hwdtest03', NULL, '$2a$10$v1sRnHkslOJqtmToUugMOeZzvnS.BA10bZ.zy2Yk1roi3d2gv56ZK', 'all', 'client_credentials,password,refresh_token', NULL, NULL, 7200, 36000, '{\"lessorCode\":\"hwdtest03\",\"lesseeCode\":\"hwdtest04\"}', NULL);

SET FOREIGN_KEY_CHECKS = 1;
