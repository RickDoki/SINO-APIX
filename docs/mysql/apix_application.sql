/*
 Navicat Premium Data Transfer

 Source Server         : 47.103.109.225（sino apix）
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 47.103.109.225:30306
 Source Schema         : apix_application

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/01/2022 15:51:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'api名称',
  `description` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api描述',
  `markdown` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api版本号',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api请求路径',
  `request_method` enum('GET','POST','PUT','DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api请求方法',
  `is_published` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否发布',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `creation_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `last_updated_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者用户名',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `domain` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api服务地址',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api请求参数',
  `request_example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api请求示例',
  `response_example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api返回示例',
  `is_internal` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为中台内部接口',
  `prefix_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api前置请求路径',
  `response_params` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api返回参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api
-- ----------------------------
INSERT INTO `api` VALUES (92, 'discovery', '', NULL, '1.5', '/api/discovery/instances', 'GET', '60012', '2021-12-09 18:08:28', 1, 'admin', '2021-12-09 18:08:28', 0, NULL, 0, 'http://47.103.109.225:30000', '[]', '\"\"', '\"\"', 0, '', '[]');
INSERT INTO `api` VALUES (93, '百度新闻', 'http://news.baidu.com/widget?id=LocalNews&ajax=json&t=1639119700299', NULL, '1.0', '/widget', 'GET', '60012', '2021-12-10 15:04:04', 1, 'admin', '2021-12-10 15:04:04', 0, NULL, 0, 'http://news.baidu.com:80', '[{\"parame\":\"ajax\",\"type\":\"Object\",\"isHaveto\":\"\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_15\"},{\"parame\":\"id\",\"type\":\"String\",\"isHaveto\":\"\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_16\"}]', '\"\"', '\"\"', 0, '', '[]');
INSERT INTO `api` VALUES (94, 'bbb', 'test', NULL, '1.8', '/appbbb', 'POST', '60012', '2021-12-27 14:09:00', 1, 'admin', '2021-12-27 14:09:00', 0, NULL, 0, 'http://www.baidu.com:9090', '[]', '\"\"', '\"\"', 0, '/apiroundRobin', '[]');
INSERT INTO `api` VALUES (95, 'tttt', '', NULL, '1.test', '/list', 'POST', '60012', '2022-01-07 14:48:18', 1, 'admin', '2022-01-07 14:48:18', 0, NULL, 0, 'http://www.sinosdx.com:8080', '[]', '\"\"', '\"\"', 0, '', '[]');
INSERT INTO `api` VALUES (96, 'tttt01', '', NULL, '1.test', '/list011', 'POST', '60012', '2022-01-07 14:49:42', 0, NULL, '2022-01-07 14:57:53', 0, NULL, 0, 'www.sinosdx.com', '[]', '\"\"', '\"\"', 0, '', '[]');

-- ----------------------------
-- Table structure for api_template
-- ----------------------------
DROP TABLE IF EXISTS `api_template`;
CREATE TABLE `api_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'api名称',
  `description` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api描述',
  `markdown` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api请求路径',
  `request_method` enum('GET','POST','PUT','DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api请求方法',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api请求参数',
  `request_example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api请求示例',
  `response_example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api返回示例',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `creation_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `last_updated_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者用户名',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api_template
-- ----------------------------

-- ----------------------------
-- Table structure for api_version
-- ----------------------------
DROP TABLE IF EXISTS `api_version`;
CREATE TABLE `api_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_id` int(11) NOT NULL COMMENT 'api id',
  `api_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api名称',
  `description` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api描述',
  `markdown` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'api版本号',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api请求路径',
  `request_method` enum('GET','POST','PUT','DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api请求方法',
  `is_published` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否发布',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `creation_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `last_updated_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者用户名',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `domain` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api服务地址',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api请求参数',
  `request_example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api请求示例',
  `response_example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api返回示例',
  `is_internal` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为中台内部接口',
  `prefix_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api前置请求路径',
  `response_params` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'api返回参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api_version
-- ----------------------------
INSERT INTO `api_version` VALUES (88, 92, 'discovery', '', NULL, '1.5', '/api/discovery/instances', 'GET', '60012', '2021-12-09 18:08:28', 1, 'admin', '2021-12-09 18:08:28', 0, NULL, 0, 'http://47.103.109.225:30000', '[]', '\"\"', '\"\"', 0, '', '[]');
INSERT INTO `api_version` VALUES (89, 93, '百度新闻', 'http://news.baidu.com/widget?id=LocalNews&ajax=json&t=1639119700299', NULL, '1.0', '/widget', 'GET', '60012', '2021-12-10 15:04:04', 1, 'admin', '2021-12-10 15:04:04', 0, NULL, 0, 'http://news.baidu.com:80', '[{\"parame\":\"ajax\",\"type\":\"Object\",\"isHaveto\":\"\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_15\"},{\"parame\":\"id\",\"type\":\"String\",\"isHaveto\":\"\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_16\"}]', '\"\"', '\"\"', 0, '', '[]');
INSERT INTO `api_version` VALUES (90, 94, 'bbb', 'test', NULL, '1.8', '/appbbb', 'POST', '60012', '2021-12-27 14:09:00', 1, 'admin', '2021-12-27 14:09:00', 0, NULL, 0, 'http://www.baidu.com:9090', '[]', '\"\"', '\"\"', 0, '/apiroundRobin', '[]');

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `description` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用描述',
  `markdown` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档',
  `icon_url` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标地址',
  `code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编号',
  `is_published` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否发布到资源市场',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `creation_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `last_updated_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者用户名',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `product_id` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品编号',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用标签',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `app_code_index`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES (112, 'hwd', 'hwd', NULL, NULL, '12a3a180', '60005', '2021-12-09 18:07:14', 1, 'admin', '2022-01-08 15:46:41', 4, 'hcz', 0, '', '');
INSERT INTO `application` VALUES (113, 'sms', 'sms', NULL, NULL, '50530ac9', '60005', '2021-12-09 18:08:48', 1, 'admin', '2021-12-09 18:11:32', 1, 'admin', 0, '', '');
INSERT INTO `application` VALUES (114, 'test', 'test', NULL, NULL, '580e7e33', '60002', '2021-12-10 14:57:56', 1, 'admin', '2021-12-10 15:05:18', 1, 'admin', 0, '', '');
INSERT INTO `application` VALUES (115, 'aaaaa', 'aaaa', NULL, NULL, 'ef2ac47b', '60005', '2021-12-20 17:26:01', 1, 'admin', '2021-12-21 15:43:43', 1, 'admin', 0, '', 'aaa');
INSERT INTO `application` VALUES (116, 'aaa', 'aaaa', NULL, NULL, '27008420', '60001', '2022-01-07 16:49:16', 1, 'admin', '2022-01-07 16:49:16', 0, NULL, 0, '', '');
INSERT INTO `application` VALUES (117, 'aaa001', '001', NULL, NULL, '69f5c85b', '60001', '2022-01-07 16:49:58', 1, 'admin', '2022-01-07 16:49:58', 0, NULL, 0, '', '');
INSERT INTO `application` VALUES (118, 'aaa0011', '0011', NULL, NULL, '974434be', '60001', '2022-01-07 17:07:51', 1, 'admin', '2022-01-07 17:07:51', 0, NULL, 0, '', '');
INSERT INTO `application` VALUES (119, 'aaa00111222', '0011', '', NULL, 'dcd50a3e', '60001', '2022-01-07 17:08:29', 1, 'admin', '2022-01-07 17:31:03', 0, NULL, 0, '', '');
INSERT INTO `application` VALUES (120, '123', '123', '', NULL, '6ddf5d07', '60001', '2022-01-08 15:24:21', 4, 'hcz', '2022-01-08 15:45:33', 4, 'hcz', 0, '', '123');

-- ----------------------------
-- Table structure for application_api
-- ----------------------------
DROP TABLE IF EXISTS `application_api`;
CREATE TABLE `application_api`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `app_code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编号',
  `app_version_id` int(11) NOT NULL COMMENT '应用版本表id',
  `api_id` int(11) NOT NULL COMMENT 'apiId',
  `api_version_id` int(11) NULL DEFAULT NULL COMMENT 'api版本表id',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_api
-- ----------------------------
INSERT INTO `application_api` VALUES (76, 112, '12a3a180', 46, 92, 88, '2021-12-09 18:09:26', 0, '2021-12-09 18:09:26', 0, 0);
INSERT INTO `application_api` VALUES (77, 114, '580e7e33', 47, 93, 89, '2021-12-10 15:04:59', 0, '2021-12-10 15:04:59', 0, 0);
INSERT INTO `application_api` VALUES (92, 115, 'ef2ac47b', 56, 92, 88, '2021-12-21 09:54:22', 0, '2021-12-21 09:54:22', 0, 0);
INSERT INTO `application_api` VALUES (93, 115, 'ef2ac47b', 56, 93, 89, '2021-12-21 09:54:22', 0, '2021-12-21 09:54:22', 0, 0);
INSERT INTO `application_api` VALUES (94, 112, '12a3a180', 61, 93, NULL, '2021-12-29 16:29:30', 0, '2021-12-29 16:29:30', 0, 0);

-- ----------------------------
-- Table structure for application_api_gateway
-- ----------------------------
DROP TABLE IF EXISTS `application_api_gateway`;
CREATE TABLE `application_api_gateway`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url_code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用产品编号或应用编号',
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `api_id` int(11) NOT NULL COMMENT 'api id',
  `api_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'api url',
  `domain` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务地址',
  `gateway_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网关路由id',
  `is_internal` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为中台内部接口',
  `prefix_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api前置请求路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_api_gateway
-- ----------------------------
INSERT INTO `application_api_gateway` VALUES (38, '12a3a180', 112, 92, '/api/discovery/instances', 'http://47.103.109.225:30000', '12a3a180_31ceb00a', 0, '');
INSERT INTO `application_api_gateway` VALUES (39, '580e7e33', 114, 93, '/widget', 'http://news.baidu.com:80', '580e7e33_f2dfd4f9', 0, '');
INSERT INTO `application_api_gateway` VALUES (54, 'ef2ac47b', 115, 92, '/api/discovery/instances', 'http://47.103.109.225:30000', 'ef2ac47b_4f1951b7', 0, '');
INSERT INTO `application_api_gateway` VALUES (55, 'ef2ac47b', 115, 93, '/widget', 'http://news.baidu.com:80', 'ef2ac47b_feb9b94f', 0, '');

-- ----------------------------
-- Table structure for application_developer
-- ----------------------------
DROP TABLE IF EXISTS `application_developer`;
CREATE TABLE `application_developer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编号',
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `username` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开发者账号',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开发者手机号',
  `user_id` int(11) NOT NULL COMMENT '开发者id',
  `is_creator` tinyint(1) NULL DEFAULT 0 COMMENT '是否为应用创建者',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_developer
-- ----------------------------
INSERT INTO `application_developer` VALUES (68, '12a3a180', 112, 'admin', NULL, 1, 1, '2021-12-09 18:07:14', 0, '2021-12-09 18:07:14', 0, 0);
INSERT INTO `application_developer` VALUES (69, '50530ac9', 113, 'admin', NULL, 1, 1, '2021-12-09 18:08:48', 0, '2021-12-09 18:08:48', 0, 0);
INSERT INTO `application_developer` VALUES (70, '580e7e33', 114, 'admin', NULL, 1, 1, '2021-12-10 14:57:56', 0, '2021-12-10 14:57:56', 0, 0);
INSERT INTO `application_developer` VALUES (71, 'ef2ac47b', 115, 'admin', NULL, 1, 1, '2021-12-20 17:26:01', 0, '2021-12-20 17:26:01', 0, 0);
INSERT INTO `application_developer` VALUES (72, '27008420', 116, 'admin', NULL, 1, 1, '2022-01-07 16:49:16', 0, '2022-01-07 16:49:16', 0, 0);
INSERT INTO `application_developer` VALUES (73, '69f5c85b', 117, 'admin', NULL, 1, 1, '2022-01-07 16:49:58', 0, '2022-01-07 16:49:58', 0, 0);
INSERT INTO `application_developer` VALUES (74, '974434be', 118, 'admin', NULL, 1, 1, '2022-01-07 17:07:51', 0, '2022-01-07 17:07:51', 0, 0);
INSERT INTO `application_developer` VALUES (75, 'dcd50a3e', 119, 'admin', NULL, 1, 1, '2022-01-07 17:08:29', 0, '2022-01-07 17:08:29', 0, 0);
INSERT INTO `application_developer` VALUES (76, '6ddf5d07', 120, 'hcz', NULL, 4, 1, '2022-01-08 15:24:21', 0, '2022-01-08 15:24:21', 0, 0);

-- ----------------------------
-- Table structure for application_lease
-- ----------------------------
DROP TABLE IF EXISTS `application_lease`;
CREATE TABLE `application_lease`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_lessee_code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '承租应用编号',
  `app_lessee_id` int(11) NOT NULL COMMENT '承租应用id',
  `app_lessor_code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出租应用编号(获取token的client_id)',
  `app_lessor_id` int(11) NOT NULL COMMENT '出租应用id',
  `client_id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '获取token的client_id',
  `client_secret` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '获取token的client_secret',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `creation_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `last_updated_by_username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者用户名',
  `app_lessee_name` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '承租应用名称',
  `app_lessor_name` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出租应用名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_lease
-- ----------------------------
INSERT INTO `application_lease` VALUES (34, '50530ac9', 113, '12a3a180', 112, '50530ac9-12a3a180', '0f87ab4edce54e0a695ddd80a3517ca3', '2021-12-09 18:11:42', 1, '2021-12-09 18:11:42', 0, 0, 'admin', NULL, 'sms', 'hwd');
INSERT INTO `application_lease` VALUES (35, '50530ac9', 113, '580e7e33', 114, '50530ac9-580e7e33', 'f2fd99986de3f8f5553cc946d45f4369', '2021-12-10 15:05:53', 1, '2021-12-10 15:05:53', 0, 0, 'admin', NULL, 'sms', 'test');

-- ----------------------------
-- Table structure for application_plugin
-- ----------------------------
DROP TABLE IF EXISTS `application_plugin`;
CREATE TABLE `application_plugin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `app_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用编号',
  `plugin_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件类型',
  `plugin_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '插件参数',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用 0-启用 1-关闭',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_plugin
-- ----------------------------

-- ----------------------------
-- Table structure for application_plugin_client
-- ----------------------------
DROP TABLE IF EXISTS `application_plugin_client`;
CREATE TABLE `application_plugin_client`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_client_id` int(11) NOT NULL COMMENT 'client_id',
  `app_plugin_id` int(11) NULL DEFAULT NULL COMMENT '插件Id',
  `plugin_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件类型',
  `plugin_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '插件参数',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_plugin_client
-- ----------------------------

-- ----------------------------
-- Table structure for application_subscribe
-- ----------------------------
DROP TABLE IF EXISTS `application_subscribe`;
CREATE TABLE `application_subscribe`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subscribe_client_id` int(11) NOT NULL COMMENT '订阅者Id',
  `app_subscribed_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被订阅应用编号',
  `app_subscribed_id` int(11) NULL DEFAULT NULL COMMENT '被订阅应用Id',
  `app_client_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成一段订阅code用于网关定位',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_subscribe
-- ----------------------------
INSERT INTO `application_subscribe` VALUES (1, 1, '12a3a180', 112, 'adfadf', '2021-12-29 13:33:17', 1, '2021-12-29 13:33:26', 1, 0);

-- ----------------------------
-- Table structure for application_version
-- ----------------------------
DROP TABLE IF EXISTS `application_version`;
CREATE TABLE `application_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `app_code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用编号',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用版本',
  `description` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用版本说明',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_version
-- ----------------------------
INSERT INTO `application_version` VALUES (46, 112, '12a3a180', '1.0', '', '2021-12-09 18:09:26', 0, '2021-12-09 18:09:26', 0, 0);
INSERT INTO `application_version` VALUES (47, 114, '580e7e33', '2.0', '百度新闻', '2021-12-10 15:04:59', 0, '2021-12-10 15:04:59', 0, 0);
INSERT INTO `application_version` VALUES (56, 115, 'ef2ac47b', '111', '1111', '2021-12-21 09:54:22', 0, '2021-12-21 09:54:22', 0, 0);
INSERT INTO `application_version` VALUES (61, 112, '12a3a180', 'smsTest', 'smsTest', '2021-12-29 16:29:30', 0, '2021-12-29 16:29:30', 0, 0);
INSERT INTO `application_version` VALUES (62, 112, '12a3a180', 'smsTest333', 'smsTest333', '2022-01-07 16:16:19', 0, '2022-01-07 16:19:05', 0, 0);
INSERT INTO `application_version` VALUES (63, 112, '12a3a180', 'smsTest666', 'smsTest666', '2022-01-07 16:36:30', 0, '2022-01-07 16:36:30', 0, 0);

-- ----------------------------
-- Table structure for platform_markdown
-- ----------------------------
DROP TABLE IF EXISTS `platform_markdown`;
CREATE TABLE `platform_markdown`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plat_name` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台名称',
  `markdown` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_markdown
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '数据库MySQL', 'dbaas');
INSERT INTO `product` VALUES (2, '缓存服务Redis', 'redis');
INSERT INTO `product` VALUES (3, '对象存储OSS', 'oss');
INSERT INTO `product` VALUES (4, 'iGearAI工作服务平台', 'igear');
INSERT INTO `product` VALUES (5, '开发者平台', 'vdi');
INSERT INTO `product` VALUES (6, '块存储（云盘）', 'clouddisk');
INSERT INTO `product` VALUES (7, '云服务器（ECS）', 'ecs');
INSERT INTO `product` VALUES (8, '容器实例服务', 'container');
INSERT INTO `product` VALUES (9, 'vmm', 'vmm');

-- ----------------------------
-- Table structure for upstream_server
-- ----------------------------
DROP TABLE IF EXISTS `upstream_server`;
CREATE TABLE `upstream_server`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `description` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品编码',
  `protocol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `server_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `prefix_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `load_balance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upstream_server
-- ----------------------------
INSERT INTO `upstream_server` VALUES (21, 'aaaa', 'aaa', 'http', 'www.baidu.com', '9090', '', 'roundRobin', '2021-12-27 13:59:56', 1, '2021-12-27 14:03:26', 1, 0);
INSERT INTO `upstream_server` VALUES (22, 'sms-test-022', 'update', 'http', 'www.sionsdx.com', '8080', '', 'roundRobin', '2022-01-07 15:13:14', 1, '2022-01-07 15:25:27', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
