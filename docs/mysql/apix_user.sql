/*
 Navicat Premium Data Transfer

 Source Server         : 47.103.109.225（sino apix）
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 47.103.109.225:30306
 Source Schema         : apix_user

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 21/01/2022 12:10:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_client`;
CREATE TABLE `sys_client`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) NOT NULL COMMENT '资源id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'code',
  `resource_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源类型',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_client
-- ----------------------------
INSERT INTO `sys_client` VALUES (22, 65, '2b315c98', 'user', '2022-01-21 11:12:38', 0, '2022-01-21 11:12:38', 0, 0);
INSERT INTO `sys_client` VALUES (23, 66, 'c03a1548', 'user', '2022-01-21 11:17:51', 0, '2022-01-21 11:17:51', 0, 0);

-- ----------------------------
-- Table structure for sys_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_login`;
CREATE TABLE `sys_login`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号名',
  `user_id` int(11) NOT NULL COMMENT '用户userId',
  `mobile` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `qq_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password_reset_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `password_expire_time` datetime(0) NULL DEFAULT NULL COMMENT '密码过期时间',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mobile_index`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login
-- ----------------------------
INSERT INTO `sys_login` VALUES (61, NULL, 65, '15216660001', '$2a$10$YppjL1kloDH4I3ygNegoCu7xTtUI3m.2dxs.JD/uF65Ek7alvBofS', NULL, NULL, NULL, NULL, '2022-01-21 11:12:38', 65, '2022-01-21 11:21:32', 65, 0);
INSERT INTO `sys_login` VALUES (62, NULL, 66, '17621702332', '$2a$10$2AAvbyYAevgQlOABnAmaju95Y8ut7HH2mQwVM97qO8J/VRDJORoRy', NULL, NULL, NULL, NULL, '2022-01-21 11:17:51', 66, '2022-01-21 11:21:25', 66, 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `drill_down` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否下钻 0-不 1-是',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端本地路径',
  `path_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端',
  `path_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (9, 14, '添加成员', 0, '@/views/system/org/add', 'org', 'Org', '/org', NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 0, '系统设置', 0, NULL, NULL, NULL, '/system', NULL, 0, 'setting', 6);
INSERT INTO `sys_menu` VALUES (11, 10, '用户管理', 0, '@/views/system/user/list', 'User', 'user', '/user', NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (12, 10, '审计日志', 0, '@/views/system/log/list', 'log', 'log', '/audit', NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (13, 10, '角色管理', 0, '@/views/system/role/list', 'role', 'Role', '/role', NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (14, 10, '组织管理', 0, '@/views/system/org/list', 'org', 'Org', '/org', NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (15, 11, '配置', 0, '@/views/system/user/config', 'User', 'user', '/user', NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (16, 12, '详情', 0, '@/views/system/log/detail', 'log', 'log', '/audit', NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 13, '配置', 0, '@/views/system/role/config', 'role', 'Role', '/role', NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (18, 13, '删除', 0, '@/views/system/role/delete', 'role', 'Role', '/role', NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, 14, '修改', 0, '@/views/system/org/update', 'org', 'Org', '/org', NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (20, 0, '上游管理', 0, NULL, NULL, NULL, '/upstream', NULL, 0, 'computer', 7);
INSERT INTO `sys_menu` VALUES (21, 20, '上游管理', 0, '@/views/upstream/index', 'upstream', 'index', NULL, 'sys:upstream:list,sys:upstream:info', 1, 'computer', 6);
INSERT INTO `sys_menu` VALUES (22, 20, '创建上游服务', 1, '@/views/upstream/detail', 'UpstreamCreate', 'upstream/create', NULL, 'sys:upstream:save', 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (23, 20, '配置上游服务', 1, '@/views/upstream/detail', 'UpstreamEdit', 'upstream/edit/:id', NULL, 'sys:upstream:update', 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (24, 21, '配置', 0, '@/views/upstream/config', 'upstream', 'index', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (25, 21, '查看', 0, '@/views/upstream/list', 'upstream', 'index', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (26, 21, '删除', 0, '@/views/upstream/delete', 'upstream', 'index', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (27, 21, '创建', 0, '@/views/upstream/create', 'upstream', 'index', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (40, 0, 'API门户', 0, NULL, NULL, NULL, '/apiDoor', NULL, 0, 'door', 8);
INSERT INTO `sys_menu` VALUES (41, 40, 'API门户', 0, '@/views/apiDoor/index', 'apiDoor', 'index', NULL, 'sys:api:list,sys:api:info', 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (42, 40, '门户详情', 1, '@/views/apiDoor/deatil', 'apiDoorDetail', 'detail', NULL, 'sys:api:save', 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (50, 0, '控制台', 0, NULL, NULL, NULL, '/dashboard', NULL, 1, 'dashboard', 1);
INSERT INTO `sys_menu` VALUES (52, 50, '控制台', 1, '@/views/dashboard/index', 'dashboard', 'index', NULL, NULL, 0, 'dashboard', 6);
INSERT INTO `sys_menu` VALUES (60, 0, 'API管理', 0, NULL, NULL, NULL, '/api', NULL, 0, 'list', 5);
INSERT INTO `sys_menu` VALUES (61, 60, 'API列表', 0, '@/views/api/list', 'App', 'list', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (62, 60, 'API发布', 0, '@/views/api/add', 'release', 'add', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (63, 60, 'CSP2.0产品API模板', 1, '@/views/api/add', 'dashboard', 'dashboard', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (64, 61, '删除', 0, '@/views/api/delete', 'App', 'list', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (80, 0, '数据统计', 0, NULL, NULL, NULL, '/data', NULL, 0, 'tree', 3);
INSERT INTO `sys_menu` VALUES (81, 80, '数据统计', 0, '@/views/router/list', 'Statistics', 'Statistics', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (82, 81, '详情', 0, '@/views/router/detail', 'Statistics', 'Statistics', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (90, 0, '应用管理', 0, NULL, NULL, NULL, '/app', NULL, 0, 'component', 2);
INSERT INTO `sys_menu` VALUES (91, 90, '我的应用', 0, '@/views/app/list', 'app', 'list', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (92, 90, '已订阅应用详情', 1, '@/views/app/subscribeDetail', 'subscribeDetail', 'subscribeDetail', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (93, 90, '应用详情', 1, '@/views/app/detail', 'detail', 'detail', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (94, 90, '创建应用', 1, '@/views/app/add', 'add', 'add', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (95, 90, '已订阅的应用', 0, '@/views/app/subscribe', 'subscribe', 'subscribe', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (96, 90, '发布新版本', 1, '@/views/app/newVersion', 'newversion', 'newversion', NULL, NULL, 1, NULL, 6);
INSERT INTO `sys_menu` VALUES (97, 91, '创建', 0, '@/views/app/create', 'app', 'list', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (98, 91, '详情', 0, '@/views/app/detail', 'app', 'list', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (99, 91, '上架', 0, '@/views/app/up', 'app', 'list', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (100, 91, '启用', 0, '@/views/app/start', 'app', 'list', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (101, 91, '停用', 0, '@/views/app/stop', 'app', 'list', NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_menu` VALUES (102, 95, '详情', 0, '@/views/app/subscribe/detail', 'app', 'list', NULL, NULL, 1, NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu_delete
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_delete`;
CREATE TABLE `sys_menu_delete`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `drill_down` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否下钻 0-不 1-是',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端本地路径',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_delete
-- ----------------------------
INSERT INTO `sys_menu_delete` VALUES (10, 0, '系统设置', 0, NULL, '/sys/system', NULL, 0, NULL, NULL);
INSERT INTO `sys_menu_delete` VALUES (20, 10, '用户管理', 0, NULL, '/sys/user', NULL, 1, 'config', 6);
INSERT INTO `sys_menu_delete` VALUES (21, 20, '查看', 0, NULL, NULL, 'sys:user:list,sys:user:info', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (22, 20, '新增', 0, NULL, NULL, 'sys:user:save', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (23, 20, '修改', 0, NULL, NULL, 'sys:user:update', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (24, 20, '删除', 0, NULL, NULL, 'sys:user:delete', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (31, 10, '审计日志', 0, NULL, '/sys/audit', NULL, 1, 'config', 6);
INSERT INTO `sys_menu_delete` VALUES (32, 31, '查看', 0, NULL, NULL, 'sys:audit:list,sys:audit:info', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (33, 31, '新增', 0, NULL, NULL, 'sys:audit:save', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (34, 31, '修改', 0, NULL, NULL, 'sys:audit:update', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (35, 31, '删除', 0, NULL, NULL, 'sys:audit:delete', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (36, 0, '上游管理', 0, NULL, '/sys/upstream', NULL, 1, 'config', 6);
INSERT INTO `sys_menu_delete` VALUES (37, 36, '查看详情', 0, NULL, NULL, 'sys:upstream:list,sys:upstream:info', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (38, 36, '新增', 0, NULL, NULL, 'sys:upstream:save', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (39, 36, '修改', 0, NULL, NULL, 'sys:upstream:update', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (40, 36, '删除', 0, NULL, NULL, 'sys:upstream:delete', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (41, 0, 'API门户', 0, NULL, '/sys/api', NULL, 1, 'config', 6);
INSERT INTO `sys_menu_delete` VALUES (42, 41, '查看', 0, NULL, NULL, 'sys:api:list,sys:api:info', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (43, 41, '新增', 0, NULL, NULL, 'sys:api:save', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (44, 41, '修改', 0, NULL, NULL, 'sys:api:update', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (45, 41, '删除', 0, NULL, NULL, 'sys:api:delete', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (46, 10, '角色管理', 0, NULL, '/sys/role', NULL, 1, 'config', 6);
INSERT INTO `sys_menu_delete` VALUES (47, 46, '查看', 0, NULL, NULL, 'sys:role:list,sys:role:info', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (48, 46, '新增', 0, NULL, NULL, 'sys:role:save', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (49, 46, '修改', 0, NULL, NULL, 'sys:role:update', 2, NULL, 6);
INSERT INTO `sys_menu_delete` VALUES (50, 46, '删除', 0, NULL, NULL, 'sys:role:delete', 2, NULL, 6);

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织编码',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (2, 'Damien', NULL, '2021-09-26 14:58:29', 2, '2021-10-21 14:37:29', 21, 0);
INSERT INTO `sys_org` VALUES (8, '测试组1', NULL, '2021-09-27 10:27:55', 28, '2021-09-27 10:27:55', 0, 0);
INSERT INTO `sys_org` VALUES (9, '测试组2', NULL, '2021-09-27 10:40:24', 29, '2021-09-27 10:40:24', 0, 0);
INSERT INTO `sys_org` VALUES (15, 'ahdad', NULL, '2021-09-29 18:08:37', 39, '2021-09-29 18:08:37', 0, 0);
INSERT INTO `sys_org` VALUES (16, 'ieiqf', NULL, '2021-09-29 18:58:13', 40, '2021-09-29 18:58:13', 0, 0);
INSERT INTO `sys_org` VALUES (17, 'adadf', NULL, '2021-09-30 13:58:11', 41, '2021-09-30 13:58:11', 0, 0);
INSERT INTO `sys_org` VALUES (18, '312131', NULL, '2021-09-30 14:05:00', 42, '2021-09-30 14:05:00', 0, 0);
INSERT INTO `sys_org` VALUES (19, '阿杜哈', NULL, '2021-09-30 14:05:16', 43, '2021-09-30 14:05:16', 0, 0);
INSERT INTO `sys_org` VALUES (20, '阿杜哈222', NULL, '2021-09-30 14:07:13', 44, '2021-09-30 14:07:13', 0, 0);
INSERT INTO `sys_org` VALUES (21, 'qwer', NULL, '2021-09-30 17:31:00', 45, '2021-10-21 10:41:55', 21, 0);
INSERT INTO `sys_org` VALUES (22, 'Rick', NULL, '2021-11-26 15:11:04', 50, '2021-11-26 15:11:04', 0, 0);
INSERT INTO `sys_org` VALUES (23, '7e6dfc47', NULL, '2022-01-10 15:11:23', 51, '2022-01-10 15:11:23', 0, 0);
INSERT INTO `sys_org` VALUES (24, '456c033c', NULL, '2022-01-10 15:25:14', 52, '2022-01-10 15:25:14', 0, 0);
INSERT INTO `sys_org` VALUES (25, 'a2ff13f6', NULL, '2022-01-10 16:17:17', 53, '2022-01-10 16:17:17', 0, 0);
INSERT INTO `sys_org` VALUES (26, 'fd75edf4', NULL, '2022-01-13 16:39:10', 54, '2022-01-13 16:39:10', 0, 0);
INSERT INTO `sys_org` VALUES (27, '5d7c2263', NULL, '2022-01-13 16:39:58', 55, '2022-01-13 16:39:58', 0, 0);
INSERT INTO `sys_org` VALUES (28, 'd13709ba', NULL, '2022-01-13 16:54:37', 56, '2022-01-13 16:54:37', 0, 0);
INSERT INTO `sys_org` VALUES (29, 'fa040130', NULL, '2022-01-13 16:57:08', 57, '2022-01-13 16:57:08', 0, 0);
INSERT INTO `sys_org` VALUES (30, '2e2309fd', NULL, '2022-01-13 17:29:31', 58, '2022-01-13 17:29:31', 0, 0);
INSERT INTO `sys_org` VALUES (32, 'f8adff44', NULL, '2022-01-14 13:34:57', 59, '2022-01-14 13:34:57', 0, 0);
INSERT INTO `sys_org` VALUES (33, '92e808d9', NULL, '2022-01-14 15:27:34', 61, '2022-01-14 15:27:34', 0, 0);
INSERT INTO `sys_org` VALUES (34, 'b20f6fca', NULL, '2022-01-17 13:48:08', 62, '2022-01-17 13:48:08', 0, 0);
INSERT INTO `sys_org` VALUES (35, '732c693b', NULL, '2022-01-18 11:18:10', 63, '2022-01-18 11:18:10', 0, 0);
INSERT INTO `sys_org` VALUES (36, 'faccf1d4', NULL, '2022-01-18 15:43:21', 64, '2022-01-18 15:43:21', 0, 0);
INSERT INTO `sys_org` VALUES (37, 'aa205324', NULL, '2022-01-21 11:12:38', 65, '2022-01-21 11:12:38', 0, 0);
INSERT INTO `sys_org` VALUES (38, 'fa730e8b', NULL, '2022-01-21 11:17:51', 66, '2022-01-21 11:17:51', 0, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '父权限id\r\n',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 0, '超级管理员', '超级管理员', 3, '2021-09-10 13:25:45', '2021-10-21 09:13:47');
INSERT INTO `sys_role` VALUES (2, 1, '开发者', '开发者', 1, '2021-09-13 10:14:02', '2021-10-15 14:45:15');
INSERT INTO `sys_role` VALUES (3, 1, '组织管理员', '组织管理员', 1, '2021-09-24 15:34:30', '2021-09-24 15:34:34');
INSERT INTO `sys_role` VALUES (18, 0, '普通管理', '普通管理', 3, '2021-09-15 17:54:14', '2021-10-21 13:36:09');
INSERT INTO `sys_role` VALUES (32, 2, '博冀开发', '博冀开发', 1, '2021-09-17 13:38:57', '2021-09-17 13:39:00');
INSERT INTO `sys_role` VALUES (40, 0, 'dadas', '', 17, '2021-09-17 14:31:31', '2021-09-17 14:31:31');
INSERT INTO `sys_role` VALUES (41, 0, '开发者14', '开发者', 19, '2021-09-17 15:15:33', '2021-09-17 15:15:33');
INSERT INTO `sys_role` VALUES (44, 0, '开发者999', '开发者', 12, '2021-09-18 15:29:15', '2021-09-24 14:09:49');
INSERT INTO `sys_role` VALUES (58, 3, '开发者12', 'aaa', 1, '2021-09-22 13:58:25', '2021-09-27 16:46:31');
INSERT INTO `sys_role` VALUES (74, 0, '普通开发者', '开发者16', 21, '2021-09-24 09:29:31', '2021-09-29 15:33:15');
INSERT INTO `sys_role` VALUES (75, 0, '开发者17', '开发者17', 1, '2021-09-24 09:33:05', '2021-09-24 09:35:30');
INSERT INTO `sys_role` VALUES (76, 0, '开发者创建角色', '开发者创建角色', 19, '2021-09-24 10:56:29', '2021-09-24 10:56:29');
INSERT INTO `sys_role` VALUES (77, 0, 'test1', 'test1', 19, '2021-09-24 10:58:26', '2021-09-24 10:58:26');
INSERT INTO `sys_role` VALUES (78, 0, 'test2', 'test2', 19, '2021-09-24 10:59:36', '2021-09-24 10:59:36');
INSERT INTO `sys_role` VALUES (79, 1, 'test3', 'test3', 3, '2021-09-26 14:44:48', '2021-10-25 21:54:43');
INSERT INTO `sys_role` VALUES (85, 1, '这是新建4', '1234567', 3, '2021-10-15 10:12:31', '2021-10-21 13:38:00');
INSERT INTO `sys_role` VALUES (86, 1, 'omg', 'wdada', 3, '2021-10-21 14:36:52', '2021-10-21 16:33:56');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1316 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (433, 75, 60);
INSERT INTO `sys_role_menu` VALUES (434, 75, 61);
INSERT INTO `sys_role_menu` VALUES (435, 75, 62);
INSERT INTO `sys_role_menu` VALUES (436, 75, 63);
INSERT INTO `sys_role_menu` VALUES (437, 75, -666666);
INSERT INTO `sys_role_menu` VALUES (438, 76, 71);
INSERT INTO `sys_role_menu` VALUES (439, 76, 72);
INSERT INTO `sys_role_menu` VALUES (440, 76, -666666);
INSERT INTO `sys_role_menu` VALUES (441, 76, 70);
INSERT INTO `sys_role_menu` VALUES (442, 77, 70);
INSERT INTO `sys_role_menu` VALUES (443, 77, 71);
INSERT INTO `sys_role_menu` VALUES (444, 77, 72);
INSERT INTO `sys_role_menu` VALUES (445, 77, 73);
INSERT INTO `sys_role_menu` VALUES (446, 77, -666666);
INSERT INTO `sys_role_menu` VALUES (447, 78, 71);
INSERT INTO `sys_role_menu` VALUES (448, 78, 73);
INSERT INTO `sys_role_menu` VALUES (449, 78, -666666);
INSERT INTO `sys_role_menu` VALUES (450, 78, 70);
INSERT INTO `sys_role_menu` VALUES (473, 44, 10);
INSERT INTO `sys_role_menu` VALUES (474, 44, 11);
INSERT INTO `sys_role_menu` VALUES (475, 44, 12);
INSERT INTO `sys_role_menu` VALUES (476, 44, 13);
INSERT INTO `sys_role_menu` VALUES (477, 44, 20);
INSERT INTO `sys_role_menu` VALUES (478, 44, 21);
INSERT INTO `sys_role_menu` VALUES (479, 44, 22);
INSERT INTO `sys_role_menu` VALUES (480, 44, 23);
INSERT INTO `sys_role_menu` VALUES (481, 44, 40);
INSERT INTO `sys_role_menu` VALUES (482, 44, 41);
INSERT INTO `sys_role_menu` VALUES (483, 44, 42);
INSERT INTO `sys_role_menu` VALUES (484, 44, 50);
INSERT INTO `sys_role_menu` VALUES (485, 44, 52);
INSERT INTO `sys_role_menu` VALUES (486, 44, 60);
INSERT INTO `sys_role_menu` VALUES (487, 44, 61);
INSERT INTO `sys_role_menu` VALUES (488, 44, 62);
INSERT INTO `sys_role_menu` VALUES (489, 44, 63);
INSERT INTO `sys_role_menu` VALUES (490, 44, 70);
INSERT INTO `sys_role_menu` VALUES (491, 44, 71);
INSERT INTO `sys_role_menu` VALUES (492, 44, 72);
INSERT INTO `sys_role_menu` VALUES (493, 44, 73);
INSERT INTO `sys_role_menu` VALUES (494, 44, 90);
INSERT INTO `sys_role_menu` VALUES (495, 44, 91);
INSERT INTO `sys_role_menu` VALUES (496, 44, 92);
INSERT INTO `sys_role_menu` VALUES (497, 44, 93);
INSERT INTO `sys_role_menu` VALUES (498, 44, 94);
INSERT INTO `sys_role_menu` VALUES (500, 44, 96);
INSERT INTO `sys_role_menu` VALUES (501, 44, -666666);
INSERT INTO `sys_role_menu` VALUES (515, 58, 10);
INSERT INTO `sys_role_menu` VALUES (516, 58, 11);
INSERT INTO `sys_role_menu` VALUES (517, 58, 12);
INSERT INTO `sys_role_menu` VALUES (518, 58, 13);
INSERT INTO `sys_role_menu` VALUES (519, 58, 14);
INSERT INTO `sys_role_menu` VALUES (520, 58, -666666);
INSERT INTO `sys_role_menu` VALUES (537, 84, 10);
INSERT INTO `sys_role_menu` VALUES (538, 84, 11);
INSERT INTO `sys_role_menu` VALUES (539, 84, 12);
INSERT INTO `sys_role_menu` VALUES (540, 84, 13);
INSERT INTO `sys_role_menu` VALUES (541, 84, 14);
INSERT INTO `sys_role_menu` VALUES (542, 84, -666666);
INSERT INTO `sys_role_menu` VALUES (548, 74, 20);
INSERT INTO `sys_role_menu` VALUES (549, 74, 21);
INSERT INTO `sys_role_menu` VALUES (550, 74, 22);
INSERT INTO `sys_role_menu` VALUES (551, 74, 23);
INSERT INTO `sys_role_menu` VALUES (552, 74, -666666);
INSERT INTO `sys_role_menu` VALUES (901, 2, 50);
INSERT INTO `sys_role_menu` VALUES (902, 2, 52);
INSERT INTO `sys_role_menu` VALUES (903, 2, 60);
INSERT INTO `sys_role_menu` VALUES (904, 2, 61);
INSERT INTO `sys_role_menu` VALUES (905, 2, 64);
INSERT INTO `sys_role_menu` VALUES (906, 2, 62);
INSERT INTO `sys_role_menu` VALUES (907, 2, 63);
INSERT INTO `sys_role_menu` VALUES (908, 2, 90);
INSERT INTO `sys_role_menu` VALUES (909, 2, 91);
INSERT INTO `sys_role_menu` VALUES (910, 2, 97);
INSERT INTO `sys_role_menu` VALUES (911, 2, 98);
INSERT INTO `sys_role_menu` VALUES (912, 2, 99);
INSERT INTO `sys_role_menu` VALUES (913, 2, 100);
INSERT INTO `sys_role_menu` VALUES (914, 2, 101);
INSERT INTO `sys_role_menu` VALUES (915, 2, 92);
INSERT INTO `sys_role_menu` VALUES (916, 2, 93);
INSERT INTO `sys_role_menu` VALUES (917, 2, 94);
INSERT INTO `sys_role_menu` VALUES (918, 2, 95);
INSERT INTO `sys_role_menu` VALUES (919, 2, 102);
INSERT INTO `sys_role_menu` VALUES (920, 2, 96);
INSERT INTO `sys_role_menu` VALUES (921, 2, -666666);
INSERT INTO `sys_role_menu` VALUES (1004, 1, 10);
INSERT INTO `sys_role_menu` VALUES (1005, 1, 11);
INSERT INTO `sys_role_menu` VALUES (1006, 1, 15);
INSERT INTO `sys_role_menu` VALUES (1007, 1, 12);
INSERT INTO `sys_role_menu` VALUES (1008, 1, 16);
INSERT INTO `sys_role_menu` VALUES (1009, 1, 13);
INSERT INTO `sys_role_menu` VALUES (1010, 1, 17);
INSERT INTO `sys_role_menu` VALUES (1011, 1, 18);
INSERT INTO `sys_role_menu` VALUES (1012, 1, 14);
INSERT INTO `sys_role_menu` VALUES (1013, 1, 9);
INSERT INTO `sys_role_menu` VALUES (1014, 1, 19);
INSERT INTO `sys_role_menu` VALUES (1015, 1, 20);
INSERT INTO `sys_role_menu` VALUES (1016, 1, 21);
INSERT INTO `sys_role_menu` VALUES (1017, 1, 24);
INSERT INTO `sys_role_menu` VALUES (1018, 1, 25);
INSERT INTO `sys_role_menu` VALUES (1019, 1, 26);
INSERT INTO `sys_role_menu` VALUES (1020, 1, 27);
INSERT INTO `sys_role_menu` VALUES (1021, 1, 22);
INSERT INTO `sys_role_menu` VALUES (1022, 1, 23);
INSERT INTO `sys_role_menu` VALUES (1023, 1, 40);
INSERT INTO `sys_role_menu` VALUES (1024, 1, 41);
INSERT INTO `sys_role_menu` VALUES (1025, 1, 42);
INSERT INTO `sys_role_menu` VALUES (1026, 1, 50);
INSERT INTO `sys_role_menu` VALUES (1027, 1, 52);
INSERT INTO `sys_role_menu` VALUES (1028, 1, 60);
INSERT INTO `sys_role_menu` VALUES (1029, 1, 61);
INSERT INTO `sys_role_menu` VALUES (1030, 1, 64);
INSERT INTO `sys_role_menu` VALUES (1031, 1, 62);
INSERT INTO `sys_role_menu` VALUES (1032, 1, 63);
INSERT INTO `sys_role_menu` VALUES (1033, 1, 80);
INSERT INTO `sys_role_menu` VALUES (1034, 1, 81);
INSERT INTO `sys_role_menu` VALUES (1035, 1, 82);
INSERT INTO `sys_role_menu` VALUES (1036, 1, 90);
INSERT INTO `sys_role_menu` VALUES (1037, 1, 91);
INSERT INTO `sys_role_menu` VALUES (1038, 1, 97);
INSERT INTO `sys_role_menu` VALUES (1039, 1, 98);
INSERT INTO `sys_role_menu` VALUES (1040, 1, 99);
INSERT INTO `sys_role_menu` VALUES (1041, 1, 100);
INSERT INTO `sys_role_menu` VALUES (1042, 1, 101);
INSERT INTO `sys_role_menu` VALUES (1043, 1, 92);
INSERT INTO `sys_role_menu` VALUES (1044, 1, 93);
INSERT INTO `sys_role_menu` VALUES (1045, 1, 94);
INSERT INTO `sys_role_menu` VALUES (1046, 1, 95);
INSERT INTO `sys_role_menu` VALUES (1047, 1, 102);
INSERT INTO `sys_role_menu` VALUES (1048, 1, 96);
INSERT INTO `sys_role_menu` VALUES (1049, 1, -666666);
INSERT INTO `sys_role_menu` VALUES (1061, 18, 96);
INSERT INTO `sys_role_menu` VALUES (1062, 18, -666666);
INSERT INTO `sys_role_menu` VALUES (1081, 85, 10);
INSERT INTO `sys_role_menu` VALUES (1082, 85, 11);
INSERT INTO `sys_role_menu` VALUES (1083, 85, 12);
INSERT INTO `sys_role_menu` VALUES (1084, 85, 13);
INSERT INTO `sys_role_menu` VALUES (1085, 85, 95);
INSERT INTO `sys_role_menu` VALUES (1086, 85, -666666);
INSERT INTO `sys_role_menu` VALUES (1096, 86, 10);
INSERT INTO `sys_role_menu` VALUES (1097, 86, 11);
INSERT INTO `sys_role_menu` VALUES (1098, 86, 15);
INSERT INTO `sys_role_menu` VALUES (1099, 86, 13);
INSERT INTO `sys_role_menu` VALUES (1100, 86, 17);
INSERT INTO `sys_role_menu` VALUES (1101, 86, 18);
INSERT INTO `sys_role_menu` VALUES (1102, 86, 9);
INSERT INTO `sys_role_menu` VALUES (1103, 86, 19);
INSERT INTO `sys_role_menu` VALUES (1104, 86, -666666);
INSERT INTO `sys_role_menu` VALUES (1283, 79, 10);
INSERT INTO `sys_role_menu` VALUES (1284, 79, 11);
INSERT INTO `sys_role_menu` VALUES (1285, 79, 15);
INSERT INTO `sys_role_menu` VALUES (1286, 79, 12);
INSERT INTO `sys_role_menu` VALUES (1287, 79, 16);
INSERT INTO `sys_role_menu` VALUES (1288, 79, 13);
INSERT INTO `sys_role_menu` VALUES (1289, 79, 14);
INSERT INTO `sys_role_menu` VALUES (1290, 79, 9);
INSERT INTO `sys_role_menu` VALUES (1291, 79, 19);
INSERT INTO `sys_role_menu` VALUES (1292, 79, 20);
INSERT INTO `sys_role_menu` VALUES (1293, 79, 21);
INSERT INTO `sys_role_menu` VALUES (1294, 79, 24);
INSERT INTO `sys_role_menu` VALUES (1295, 79, 25);
INSERT INTO `sys_role_menu` VALUES (1296, 79, 26);
INSERT INTO `sys_role_menu` VALUES (1297, 79, 27);
INSERT INTO `sys_role_menu` VALUES (1298, 79, 22);
INSERT INTO `sys_role_menu` VALUES (1299, 79, 23);
INSERT INTO `sys_role_menu` VALUES (1300, 79, 41);
INSERT INTO `sys_role_menu` VALUES (1301, 79, 42);
INSERT INTO `sys_role_menu` VALUES (1302, 79, 50);
INSERT INTO `sys_role_menu` VALUES (1303, 79, 52);
INSERT INTO `sys_role_menu` VALUES (1304, 79, 60);
INSERT INTO `sys_role_menu` VALUES (1305, 79, 61);
INSERT INTO `sys_role_menu` VALUES (1306, 79, 64);
INSERT INTO `sys_role_menu` VALUES (1307, 79, 62);
INSERT INTO `sys_role_menu` VALUES (1308, 79, 63);
INSERT INTO `sys_role_menu` VALUES (1309, 79, 90);
INSERT INTO `sys_role_menu` VALUES (1310, 79, 92);
INSERT INTO `sys_role_menu` VALUES (1311, 79, 93);
INSERT INTO `sys_role_menu` VALUES (1312, 79, 94);
INSERT INTO `sys_role_menu` VALUES (1313, 79, 95);
INSERT INTO `sys_role_menu` VALUES (1314, 79, 96);
INSERT INTO `sys_role_menu` VALUES (1315, 79, -666666);

-- ----------------------------
-- Table structure for sys_role_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_org`;
CREATE TABLE `sys_role_org`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT 'orgID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色与组织关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_org
-- ----------------------------
INSERT INTO `sys_role_org` VALUES (1, 1, 1);
INSERT INTO `sys_role_org` VALUES (2, 32, 1);
INSERT INTO `sys_role_org` VALUES (4, 2, 1);
INSERT INTO `sys_role_org` VALUES (6, 58, 2);
INSERT INTO `sys_role_org` VALUES (9, 41, 1);
INSERT INTO `sys_role_org` VALUES (14, 74, 1);
INSERT INTO `sys_role_org` VALUES (15, 75, 1);
INSERT INTO `sys_role_org` VALUES (17, 76, 1);
INSERT INTO `sys_role_org` VALUES (18, 77, 1);
INSERT INTO `sys_role_org` VALUES (19, 78, 1);
INSERT INTO `sys_role_org` VALUES (20, 79, 1);
INSERT INTO `sys_role_org` VALUES (21, 2, 2);
INSERT INTO `sys_role_org` VALUES (38, 1, 8);
INSERT INTO `sys_role_org` VALUES (39, 2, 8);
INSERT INTO `sys_role_org` VALUES (40, 3, 8);
INSERT INTO `sys_role_org` VALUES (41, 1, 9);
INSERT INTO `sys_role_org` VALUES (42, 2, 9);
INSERT INTO `sys_role_org` VALUES (43, 3, 9);
INSERT INTO `sys_role_org` VALUES (48, 3, 1);
INSERT INTO `sys_role_org` VALUES (61, 1, 15);
INSERT INTO `sys_role_org` VALUES (62, 2, 15);
INSERT INTO `sys_role_org` VALUES (63, 3, 15);
INSERT INTO `sys_role_org` VALUES (64, 1, 16);
INSERT INTO `sys_role_org` VALUES (65, 2, 16);
INSERT INTO `sys_role_org` VALUES (66, 3, 16);
INSERT INTO `sys_role_org` VALUES (67, 1, 17);
INSERT INTO `sys_role_org` VALUES (68, 2, 17);
INSERT INTO `sys_role_org` VALUES (69, 3, 17);
INSERT INTO `sys_role_org` VALUES (70, 1, 18);
INSERT INTO `sys_role_org` VALUES (71, 2, 18);
INSERT INTO `sys_role_org` VALUES (72, 3, 18);
INSERT INTO `sys_role_org` VALUES (73, 1, 19);
INSERT INTO `sys_role_org` VALUES (74, 2, 19);
INSERT INTO `sys_role_org` VALUES (75, 3, 19);
INSERT INTO `sys_role_org` VALUES (76, 1, 20);
INSERT INTO `sys_role_org` VALUES (77, 2, 20);
INSERT INTO `sys_role_org` VALUES (78, 3, 20);
INSERT INTO `sys_role_org` VALUES (79, 1, 21);
INSERT INTO `sys_role_org` VALUES (80, 2, 21);
INSERT INTO `sys_role_org` VALUES (81, 3, 21);
INSERT INTO `sys_role_org` VALUES (82, 85, 21);
INSERT INTO `sys_role_org` VALUES (83, 86, 8);
INSERT INTO `sys_role_org` VALUES (84, 1, 22);
INSERT INTO `sys_role_org` VALUES (85, 2, 22);
INSERT INTO `sys_role_org` VALUES (86, 3, 22);
INSERT INTO `sys_role_org` VALUES (87, 1, 23);
INSERT INTO `sys_role_org` VALUES (88, 2, 23);
INSERT INTO `sys_role_org` VALUES (89, 3, 23);
INSERT INTO `sys_role_org` VALUES (90, 1, 24);
INSERT INTO `sys_role_org` VALUES (91, 2, 24);
INSERT INTO `sys_role_org` VALUES (92, 3, 24);
INSERT INTO `sys_role_org` VALUES (93, 1, 25);
INSERT INTO `sys_role_org` VALUES (94, 2, 25);
INSERT INTO `sys_role_org` VALUES (95, 3, 25);
INSERT INTO `sys_role_org` VALUES (96, 1, 26);
INSERT INTO `sys_role_org` VALUES (97, 2, 26);
INSERT INTO `sys_role_org` VALUES (98, 3, 26);
INSERT INTO `sys_role_org` VALUES (99, 1, 27);
INSERT INTO `sys_role_org` VALUES (100, 2, 27);
INSERT INTO `sys_role_org` VALUES (101, 3, 27);
INSERT INTO `sys_role_org` VALUES (102, 1, 28);
INSERT INTO `sys_role_org` VALUES (103, 2, 28);
INSERT INTO `sys_role_org` VALUES (104, 3, 28);
INSERT INTO `sys_role_org` VALUES (105, 1, 29);
INSERT INTO `sys_role_org` VALUES (106, 2, 29);
INSERT INTO `sys_role_org` VALUES (107, 3, 29);
INSERT INTO `sys_role_org` VALUES (108, 1, 30);
INSERT INTO `sys_role_org` VALUES (109, 2, 30);
INSERT INTO `sys_role_org` VALUES (110, 3, 30);
INSERT INTO `sys_role_org` VALUES (111, 1, 32);
INSERT INTO `sys_role_org` VALUES (112, 2, 32);
INSERT INTO `sys_role_org` VALUES (113, 3, 32);
INSERT INTO `sys_role_org` VALUES (114, 1, 33);
INSERT INTO `sys_role_org` VALUES (115, 2, 33);
INSERT INTO `sys_role_org` VALUES (116, 3, 33);
INSERT INTO `sys_role_org` VALUES (117, 1, 34);
INSERT INTO `sys_role_org` VALUES (118, 2, 34);
INSERT INTO `sys_role_org` VALUES (119, 3, 34);
INSERT INTO `sys_role_org` VALUES (120, 1, 35);
INSERT INTO `sys_role_org` VALUES (121, 2, 35);
INSERT INTO `sys_role_org` VALUES (122, 3, 35);
INSERT INTO `sys_role_org` VALUES (123, 1, 36);
INSERT INTO `sys_role_org` VALUES (124, 2, 36);
INSERT INTO `sys_role_org` VALUES (125, 3, 36);
INSERT INTO `sys_role_org` VALUES (126, 1, 37);
INSERT INTO `sys_role_org` VALUES (127, 2, 37);
INSERT INTO `sys_role_org` VALUES (128, 3, 37);
INSERT INTO `sys_role_org` VALUES (129, 1, 38);
INSERT INTO `sys_role_org` VALUES (130, 2, 38);
INSERT INTO `sys_role_org` VALUES (131, 3, 38);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号名',
  `username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `gender` int(11) NULL DEFAULT NULL COMMENT '性别',
  `mobile` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'email账号',
  `avatar_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像名称',
  `avatar_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `source` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户来源',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态',
  `creation_date` datetime(0) NULL DEFAULT NULL,
  `creation_by` int(11) NULL DEFAULT NULL,
  `last_update_date` datetime(0) NULL DEFAULT NULL,
  `last_updated_by` int(11) NULL DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mobile_index`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (65, NULL, '15216660001', NULL, '15216660001', '15216660001@163.com', NULL, NULL, NULL, 1, '2022-01-21 11:12:38', 0, '2022-01-21 11:12:38', 0, 0);
INSERT INTO `sys_user` VALUES (66, NULL, 'hwd', NULL, '17621702332', 'wendi.huang@sinosdx.com', NULL, NULL, NULL, 1, '2022-01-21 11:17:51', 0, '2022-01-21 11:17:51', 0, 0);

-- ----------------------------
-- Table structure for sys_user_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_org`;
CREATE TABLE `sys_user_org`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT 'orgID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与组织' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_org
-- ----------------------------
INSERT INTO `sys_user_org` VALUES (45, 65, 37);
INSERT INTO `sys_user_org` VALUES (46, 66, 38);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (116, 65, 3);
INSERT INTO `sys_user_role` VALUES (117, 66, 3);

-- ----------------------------
-- Table structure for user_digest
-- ----------------------------
DROP TABLE IF EXISTS `user_digest`;
CREATE TABLE `user_digest`  (
  `digest_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mobile` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`digest_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_digest
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
