/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Schema         : apix_application

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 21/01/2022 12:09:53
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
  `protocol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '协议',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api
-- ----------------------------
INSERT INTO `api` VALUES (116, '获取每日本地新闻', '', NULL, '', '/widget?id=LocalNews&ajax=json&t=1642041193240', 'GET', '60012', '2022-01-21 11:26:52', 65, '15216660001', '2022-01-21 11:26:52', 0, NULL, 0, 'http://news.baidu.com', '[{\"parame\":\"id\",\"type\":\"String\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"LocalNews\",\"_XID\":\"row_15\"},{\"parame\":\"ajax\",\"type\":\"String\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"json\",\"_XID\":\"row_16\"},{\"parame\":\"t\",\"type\":\"Integer\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"1642041193240\",\"_XID\":\"row_17\"}]', '\"http://news.baidu.com/widget?id=LocalNews&ajax=json&t=1642041193240\"', '\"{\\n  \\\"errno\\\": 0,\\n  \\\"request_id\\\": \\\"1339394591\\\",\\n  \\\"timestamp\\\": 1642735339,\\n  \\\"data\\\": {\\n    \\\"LocalNews\\\": {\\n      \\\"errno\\\": 0,\\n      \\\"data\\\": {\\n        \\\"rows\\\": {\\n          \\\"first\\\": [\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369221\\\",\\n              \\\"title\\\": \\\"上海GDP突破4万亿，2021年增长8.1%\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369457\\\",\\n              \\\"title\\\": \\\"上海市长龚正：今年将新增就业岗位55万个以上\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369409\\\",\\n              \\\"title\\\": \\\"龚正：今年要有序推动重点领域、重点行业开展碳达峰专项行动\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369470\\\",\\n              \\\"title\\\": \\\"龚正：上海要建儿童友好城市，一半以上幼儿园今年开托班\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369364\\\",\\n              \\\"title\\\": \\\"上海将筹建国有资本投资母基金，适时组建新的国企集团\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369473\\\",\\n              \\\"title\\\": \\\"龚正：坚持房住不炒，将建设筹措17.3万套保障性租赁住房\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369357\\\",\\n              \\\"title\\\": \\\"龚正：临港将建全球动力之城核心区，将实行更大程度压力测试\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:06:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369360\\\",\\n              \\\"title\\\": \\\"上海市长：做大做强免退税经济等，加快建设国际消费中心城市\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:06:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            }\\n          ],\\n          \\\"second\\\": [\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369394\\\",\\n              \\\"title\\\": \\\"上海市长：打造30个制造业数字化赋能平台\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:06:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369350\\\",\\n              \\\"title\\\": \\\"上海市长龚正：精心办好第五届进博会\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:46:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369321\\\",\\n              \\\"title\\\": \\\"上海市长龚正：“一网通办”已接入服务事项3458项\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:46:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369351\\\",\\n              \\\"title\\\": \\\"实现更多“从0到1”的突破！上海市长：新建一批重大科技基础项目\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:46:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369353\\\",\\n              \\\"title\\\": \\\"强化新赛道布局！上海这些产业今年将有大动作\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:46:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369318\\\",\\n              \\\"title\\\": \\\"龚正：制定实施“沪十条”等政策，房地产市场保持平稳健康发展\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:46:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369337\\\",\\n              \\\"title\\\": \\\"上海明确2022年GDP增长预期：5.5%左右\\\",\\n              \\\"time\\\": \\\"2022-01-20 09:46:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369467\\\",\\n              \\\"title\\\": \\\"上海市长再谈疫情防控：严格落实高风险人员等闭环管理措施\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:16:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            },\\n            {\\n              \\\"url\\\": \\\"https://www.thepaper.cn/newsDetail_forward_16369370\\\",\\n              \\\"title\\\": \\\"上海政府工作报告首次将教育与科创、人才建设列入同一板块\\\",\\n              \\\"time\\\": \\\"2022-01-20 10:36:22\\\",\\n              \\\"imgUrl\\\": \\\"\\\"\\n            }\\n          ],\\n          \\\"pic\\\": {\\n            \\\"title\\\": \\\"向世界发出“上海的邀请”\\\",\\n            \\\"url\\\": \\\"https://www.jfdaily.com/staticsg/res/html/journal/detail.html?date=2021-09-30&id=321943&page=02\\\",\\n            \\\"imgUrl\\\": \\\"https://b.bdstatic.com/boxlib/20220121/2022012111130097464073263.jpg\\\",\\n            \\\"time\\\": \\\"2021-09-30 12:40:28\\\"\\n          }\\n        },\\n        \\\"cityid\\\": 2354,\\n        \\\"total\\\": 0,\\n        \\\"result_num\\\": 19,\\n        \\\"name\\\": \\\"上海\\\",\\n        \\\"province\\\": 2354\\n      },\\n      \\\"ad\\\": null\\n    }\\n  }\\n}\"', 0, '', '[{\"parame\":\"url\",\"type\":\"String\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_28\"},{\"parame\":\"title\",\"type\":\"String\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_29\"},{\"parame\":\"time\",\"type\":\"Integer\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_30\"},{\"parame\":\"imageUrl\",\"type\":\"String\",\"isHaveto\":\"是\",\"describe\":\"\",\"default\":\"\",\"_XID\":\"row_31\"}]', 'http');
INSERT INTO `api` VALUES (117, '百度本地每日新闻2', '', NULL, '', '/widget*', 'GET', '60012', '2022-01-21 11:39:12', 65, '15216660001', '2022-01-21 11:39:12', 0, NULL, 0, 'http://news.baidu.com', '[]', '\"\"', '\"\"', 0, '', '[]', 'http');

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
  `provider` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务商',
  `publish_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
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
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES (150, '百度本地每日新闻（示例服务）', '这是百度本地每日新闻服务', '', NULL, '7fc55237', '60005', '15216660001', '2022-01-21 11:40:33', '2022-01-21 11:27:49', 65, '15216660001', '2022-01-21 11:40:33', 65, '15216660001', 0, '', 'baidu');

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
) ENGINE = InnoDB AUTO_INCREMENT = 165 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_api
-- ----------------------------
INSERT INTO `application_api` VALUES (163, 150, '7fc55237', 94, 116, NULL, '2022-01-21 11:28:05', 0, '2022-01-21 11:28:05', 0, 0);
INSERT INTO `application_api` VALUES (164, 150, '7fc55237', 95, 117, NULL, '2022-01-21 11:39:24', 0, '2022-01-21 11:39:24', 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_api_gateway
-- ----------------------------
INSERT INTO `application_api_gateway` VALUES (206, '7ccefc91', 150, 116, '/widget?id=LocalNews&ajax=json&t=1642041193240', 'http://news.baidu.com', '7ccefc91_946a86eb', 0, '');
INSERT INTO `application_api_gateway` VALUES (207, '7ccefc91', 150, 117, '/widget*', 'http://news.baidu.com', '7ccefc91_946a86eb', 0, '');

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
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_developer
-- ----------------------------

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

-- ----------------------------
-- Table structure for application_plugin
-- ----------------------------
DROP TABLE IF EXISTS `application_plugin`;
CREATE TABLE `application_plugin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `app_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用编号',
  `plugin_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件类型',
  `plugin_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '插件参数',
  `enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否启用 1-启用 0-关闭',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_plugin
-- ----------------------------
INSERT INTO `application_plugin` VALUES (107, 150, '7fc55237', 'error_log', NULL, 1, '2022-01-21 11:41:09', 65, '2022-01-21 11:41:09', 0, 0);
INSERT INTO `application_plugin` VALUES (108, 150, '7fc55237', 'http_log', NULL, 1, '2022-01-21 11:41:19', 65, '2022-01-21 11:41:19', 0, 0);

-- ----------------------------
-- Table structure for application_plugin_client
-- ----------------------------
DROP TABLE IF EXISTS `application_plugin_client`;
CREATE TABLE `application_plugin_client`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_client_id` int(11) NOT NULL COMMENT 'client_id',
  `app_plugin_id` int(11) NULL DEFAULT NULL COMMENT '插件Id',
  `plugin_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件类型',
  `plugin_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '插件参数',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_plugin_client
-- ----------------------------

-- ----------------------------
-- Table structure for application_plugin_detail
-- ----------------------------
DROP TABLE IF EXISTS `application_plugin_detail`;
CREATE TABLE `application_plugin_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plugin_type` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '插件类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '插件名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_plugin_detail
-- ----------------------------
INSERT INTO `application_plugin_detail` VALUES (1, 'base_auth', 'basic-auth', 'basic-auth鉴权插件用于给调用服务的用户添加基本身份认证。该插件检查Authorization请求头中的用户凭据。');
INSERT INTO `application_plugin_detail` VALUES (2, 'jwt', 'jwt-auth', 'jwt-auth鉴权插件用于验证包含 HS256 签名的 JSON Web 令牌的请求。该插件检查Authorization请求头中的用户凭据。');
INSERT INTO `application_plugin_detail` VALUES (3, 'oauth2', 'OAuth2', 'OAuth2鉴权插件用于给服务接口请求提供基于OAuth2协议的鉴权。该插件检查Authorization请求头中的用户凭据。');
INSERT INTO `application_plugin_detail` VALUES (4, 'black_list_ip', 'ip黑名单', '为限制访问资源的用户，提升服务的安全性，您可以设置黑名单或白名单，实现对访客身份的识别和过滤。添加IP到黑名单，那么该IP无法访问当前服务。');
INSERT INTO `application_plugin_detail` VALUES (5, 'white_list_ip', 'ip白名单', '为限制访问资源的用户，提升服务的安全性，您可以设置黑名单或白名单，实现对访客身份的识别和过滤。添加IP到白名单，那么只有该IP能够访问当前服务。');
INSERT INTO `application_plugin_detail` VALUES (6, 'cors', 'cors跨域', 'CORS跨域插件用于动态配置接口跨域资源访问，关于CORS跨域资源访问的基础知识可参考跨域资源共享（CORS）机制。');
INSERT INTO `application_plugin_detail` VALUES (7, 'sign', 'sign-auth（数据防篡改签名）', 'sign-auth插件用于保护请求数据中途不被篡改，相当于您给API网关颁发了一个token，开启插件后，请求接口时需要根据约定的加签处理方法和key生成签名并放到请求头中，网关会对比签名和服务器端计算的签名是否一致，实现身份验证。');
INSERT INTO `application_plugin_detail` VALUES (8, 'error_log', 'ERROR_LOG', '添加此插件可观测服务调用失败的日志');
INSERT INTO `application_plugin_detail` VALUES (9, 'http_log', 'HTTP_LOG', '添加此插件可观测服务调用成功的日志');
INSERT INTO `application_plugin_detail` VALUES (10, 'gzip', 'GZIP 请求压缩', 'gzip协议压缩就是依据HTTP协议进行压缩，不需要程序员进行压缩，解压编码，而是把压缩过程交给WEB服务器，将解压过程交给客户端。 如果客户端为支持GZIP压缩的浏览器，那么解压过程也不需要程序员参与，浏览器会按照一定的规则自动进行解压缩。');
INSERT INTO `application_plugin_detail` VALUES (11, 'proxy_cache', 'proxy_cache', 'proxy-cache插件用于动态缓存请求的返回数据，可以将后端返回的应答缓存在API网关服务层面，有效降低后端的负荷，增加平滑度，开启插件后第一次请求会真实转发到服务端获取数据，之后在过期时间内的请求会从缓存中获取，默认使用redis分布式缓存。');
INSERT INTO `application_plugin_detail` VALUES (12, 'real_ip', 'real_ip', 'real-ip插件用于获取客户端获取真实ip，默认情况下经过多层代理或转发后，服务端会获取多个不同ip，开启后网关层会自动解析判断获取客户端真实ip并通过响应头返回');
INSERT INTO `application_plugin_detail` VALUES (13, 'replay_attacks', '防网络重放攻击', '重放攻击(Replay Attacks)又称重播攻击、回放攻击，是指攻击者发送一个目的主机已接收过的包，来达到欺骗系统的目的。 防重放的核心在于，防止抓取请求报文，从而进行重播攻击，也就意味我们不能允许客户端，用相同参数在特定时间内，请求第二次。');
INSERT INTO `application_plugin_detail` VALUES (14, 'response_rewrite', 'response-rewrite (返回值重写)', '发布的服务，当接口还不可用时，开启返回值重写可以拦截掉对服务的请求，并给请求返回一个固定值的响应');
INSERT INTO `application_plugin_detail` VALUES (15, 'sentinel', 'sentinel限流', '对服务和服务下的api进行流量控制，限流规则持久化到nacos配置中心，网关服务监听该配置文件，实时更新生效。');

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
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_subscribe
-- ----------------------------
INSERT INTO `application_subscribe` VALUES (86, 22, '7fc55237', 150, '7ccefc91', '2022-01-21 11:28:58', 65, '2022-01-21 11:28:58', 0, 0);

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
  `markdown` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_version
-- ----------------------------
INSERT INTO `application_version` VALUES (94, 150, '7fc55237', 'V1', 'V1版本', NULL, '2022-01-21 11:28:05', 0, '2022-01-21 11:28:05', 0, 0);
INSERT INTO `application_version` VALUES (95, 150, '7fc55237', 'V2', 'V2', NULL, '2022-01-21 11:39:24', 0, '2022-01-21 11:39:24', 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upstream_server
-- ----------------------------
INSERT INTO `upstream_server` VALUES (28, '百度新闻服务', '百度新闻', 'http', 'news.baidu.com', '80', '', 'roundRobin', '2022-01-21 11:23:17', 65, '2022-01-21 11:23:31', 65, 0);

SET FOREIGN_KEY_CHECKS = 1;
