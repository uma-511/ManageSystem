/*
 Navicat Premium Data Transfer

 Source Server         : 10.211.55.5
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 10.211.55.5
 Source Database       : warrior

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 12/16/2017 15:31:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `sys_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统设置' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `aid` varchar(100) NOT NULL COMMENT '编号',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `file_type` int(11) DEFAULT NULL COMMENT '文件类型',
  `file_path` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`aid`),
  UNIQUE KEY `warrior_attachment_aid_uindex` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件信息';

-- ----------------------------
--  Records of `sys_attachment`
-- ----------------------------
BEGIN;
INSERT INTO `sys_attachment` VALUES ('d3bfd3a261bc45cfb6b5dea3f02b1a98', 'file_20171123_0549029.jpeg', '14820', '1', '/Users/rookie/Workspace/CodeRepository/ManageSystem/upload', '2017-11-23 17:49:30');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dic_key` varchar(45) DEFAULT NULL COMMENT '字典key',
  `dic_value` varchar(45) DEFAULT NULL COMMENT '字典值',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_show` int(11) DEFAULT NULL COMMENT '是否显示\n0、显示\n1、不显示',
  `dic_type` int(11) DEFAULT NULL COMMENT '字典类型',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `type_value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionary` VALUES ('1', '0', '启用', '2', '0', '1', '2017-07-28 18:14:14', '状态'), ('2', '1', '禁用', '3', '0', '1', '2017-07-28 18:14:14', '状态'), ('3', '2', '删除', '4', '0', '1', '2017-07-28 18:16:36', '状态'), ('4', '0', '显示', '2', '0', '2', '2017-07-28 18:16:36', '是否显示'), ('5', '1', '不显示', '3', '0', '2', '2017-07-28 18:16:36', '是否显示'), ('6', '0', '菜单', '2', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('7', '1', '按钮', '3', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('9', '1', '管理员', '1', '0', '4', '2017-09-20 10:36:20', '用户类型'), ('10', '2', '普通用户', '2', '0', '4', '2017-09-20 10:36:53', '用户类型'), ('11', '0', '正常', '1', '0', '5', '2017-12-04 15:39:29', '定时作业状态'), ('12', '1', '暂停', '2', '0', '5', '2017-12-04 15:40:48', '定时作业状态');
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(45) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(45) DEFAULT NULL COMMENT '操作',
  `method` varchar(500) DEFAULT NULL COMMENT '访问函数',
  `params` varchar(500) DEFAULT NULL COMMENT '参数',
  `time` bigint(20) DEFAULT NULL COMMENT '耗时',
  `ip` varchar(45) DEFAULT NULL COMMENT '操作ip地址',
  `create_time` varchar(45) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=760 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_log`
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES ('756', '-1', null, '删除系统日志', 'com.warrior.base.controller.SysLogContrller.delAll()', null, '33', '127.0.0.1', '2017-12-08 10:30:16.157'), ('757', '1', 'admin', '用户登录', 'com.warrior.base.controller.LoginController.doLogin()', 'admin', '43', '127.0.0.1', '2017-12-08 11:13:25.368'), ('758', '1', 'admin', '用户登录', 'com.warrior.base.controller.LoginController.doLogin()', 'admin', '21', '127.0.0.1', '2017-12-08 11:43:41.596'), ('759', '-1', null, '用户登出', 'com.warrior.base.controller.LoginController.logOut()', null, '27', '127.0.0.1', '2017-12-08 11:54:32.037');
COMMIT;

-- ----------------------------
--  Table structure for `sys_notes`
-- ----------------------------
DROP TABLE IF EXISTS `sys_notes`;
CREATE TABLE `sys_notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `note` varchar(225) DEFAULT NULL COMMENT '内容',
  `status` int(11) DEFAULT NULL COMMENT '状态\n1、未完成\n2、已完成',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `over_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_notes_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='待办事项信息';

-- ----------------------------
--  Records of `sys_notes`
-- ----------------------------
BEGIN;
INSERT INTO `sys_notes` VALUES ('3', '1', '测试二', '2', '2017-11-24 17:11:38', '2017-11-24 17:52:40'), ('13', '1', '测试一', '1', '2017-11-25 09:32:11', '2017-11-25 09:43:48');
COMMIT;

-- ----------------------------
--  Table structure for `sys_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `own_id` int(11) NOT NULL COMMENT '拥有者 ID',
  `res_id` int(11) NOT NULL COMMENT '资源 ID',
  `type` int(11) DEFAULT NULL COMMENT '拥有者类型\n0、角色\n1、用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=578 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_permissions`
-- ----------------------------
BEGIN;
INSERT INTO `sys_permissions` VALUES ('277', '3', '1', '1'), ('278', '3', '5', '1'), ('313', '2', '1', '0'), ('314', '2', '5', '0'), ('315', '2', '6', '0'), ('316', '2', '7', '0'), ('317', '2', '8', '0'), ('318', '2', '9', '0'), ('319', '2', '3', '0'), ('320', '2', '15', '0'), ('544', '1', '1', '0'), ('545', '1', '27', '0'), ('546', '1', '5', '0'), ('547', '1', '6', '0'), ('548', '1', '7', '0'), ('549', '1', '8', '0'), ('550', '1', '9', '0'), ('551', '1', '3', '0'), ('552', '1', '10', '0'), ('553', '1', '11', '0'), ('554', '1', '12', '0'), ('555', '1', '13', '0'), ('556', '1', '14', '0'), ('557', '1', '15', '0'), ('558', '1', '16', '0'), ('559', '1', '28', '0'), ('560', '1', '24', '0'), ('561', '1', '2', '0'), ('562', '1', '29', '0'), ('563', '1', '17', '0'), ('564', '1', '18', '0'), ('565', '1', '19', '0'), ('566', '1', '140', '0'), ('567', '1', '141', '0'), ('568', '1', '142', '0'), ('569', '1', '143', '0'), ('570', '1', '4', '0'), ('571', '1', '30', '0'), ('572', '1', '20', '0'), ('573', '1', '21', '0'), ('574', '1', '22', '0'), ('575', '1', '25', '0'), ('576', '1', '26', '0'), ('577', '1', '31', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_resources`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `res_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `res_name` varchar(120) DEFAULT NULL COMMENT '资源名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级资源ID',
  `url` varchar(200) DEFAULT NULL COMMENT '功能地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_show` int(11) DEFAULT NULL COMMENT '是否显示\n0、显示\n1、不显示',
  `remark` varchar(200) DEFAULT NULL COMMENT '功能描述',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `permission` varchar(45) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '0、菜单\n1、按钮\n2、数据',
  PRIMARY KEY (`res_id`),
  UNIQUE KEY `resId_UNIQUE` (`res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_resources`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resources` VALUES ('1', '用户管理', '0', '/user/list', '1', '0', '', '0', '2017-07-31 10:08:01', '2017-11-07 15:20:59', '', 'person', '0'), ('2', '资源管理', '24', '/resource/list', '1', '0', '', '0', '2017-07-31 10:08:01', '2017-11-07 15:25:07', '', 'ios-folder', '0'), ('3', '角色管理', '0', '/role/list', '2', '0', '', '0', '2017-07-31 10:08:01', '2017-11-07 15:24:02', '', 'person', '0'), ('4', '字典管理', '24', '/dict/list', '2', '0', '', '0', '2017-07-31 10:08:01', '2017-11-07 15:25:32', '', 'briefcase', '0'), ('5', '新增', '1', '', '2', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:add', '', '1'), ('6', '修改', '1', '', '3', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:update', '', '1'), ('7', '删除', '1', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:del', '', '1'), ('8', '用户权限查询', '1', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userperm:view', '', '1'), ('9', '用户权限修改', '1', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userperm:update', '', '1'), ('10', '新增', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:add', '', '1'), ('11', '删除', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:del', '', '1'), ('12', '修改', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:update', '', '1'), ('13', '用户角色查询', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userrole:view', '', '1'), ('14', '用户角色修改', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userrole:update', '', '1'), ('15', '角色权限查询', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:roleperm:view', '', '1'), ('16', '角色权限修改', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:roleperm:update', '', '1'), ('17', '新增', '2', '', '2', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:add', '', '1'), ('18', '修改', '2', '', '3', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:update', '', '1'), ('19', '删除', '2', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:del', '', '1'), ('20', '新增', '4', '', '2', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:add', '', '1'), ('21', '修改', '4', '', '3', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:update', '', '1'), ('22', '删除', '4', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:del', '', '1'), ('24', '系统管理', '0', '', '4', '0', '', '0', '2017-09-28 11:27:14', '2017-09-28 11:27:39', '', 'ios-folder', '0'), ('25', '系统日志', '24', '/log/list', '3', '0', '', '0', '2017-09-28 14:35:37', '2017-11-07 15:25:53', '', 'ios-list-outline', '0'), ('26', '删除', '25', '', '1', '0', '', '0', '2017-09-28 16:10:43', '2017-09-28 16:11:20', 'admin:log:del', '', '1'), ('27', '用户查询', '1', '', '1', '1', '', '0', '2017-11-07 15:21:53', '2017-11-07 15:22:09', 'admin:user:view', '', '1'), ('28', '角色查询', '3', '', '1', '1', '', '0', '2017-11-07 15:24:23', '2017-11-07 15:24:23', 'admin:role:view', '', '1'), ('29', '资源查询', '2', '', '1', '1', '', '0', '2017-11-07 15:25:25', '2017-11-07 15:25:25', 'admin:resource:view', '', '1'), ('30', '字典查询', '4', '', '1', '1', '', '0', '2017-11-07 15:25:47', '2017-11-07 15:25:47', 'admin:dict:view', '', '0'), ('31', '日志查询', '25', '', '1', '1', '', '0', '2017-11-07 15:26:15', '2017-11-07 15:26:15', 'admin:log:view', '', '1'), ('140', '定时作业', '24', '/task/list', '1', '0', '', '0', '2017-11-21 10:36:28', '2017-11-21 10:36:28', 'admin:task:view', 'briefcase', '0'), ('141', '添加', '140', '', '0', '0', '', '0', '2017-11-21 10:36:28', '2017-11-21 10:36:28', 'admin:task:add', '', '1'), ('142', '修改', '140', '', '0', '0', '', '0', '2017-11-21 10:36:28', '2017-11-21 10:36:28', 'admin:task:update', '', '1'), ('143', '删除', '140', '', '0', '0', '', '0', '2017-11-21 10:36:28', '2017-11-21 10:36:28', 'admin:task:del', '', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(120) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rid_UNIQUE` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin1', '0', '2017-07-31 10:06:27', '2017-10-27 11:57:08'), ('2', '普通用户', '', '0', '2017-11-07 15:00:33', '2017-11-07 15:00:33');
COMMIT;

-- ----------------------------
--  Table structure for `sys_task`
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `task_name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `task_group` varchar(50) DEFAULT NULL COMMENT '任务分组',
  `cron` varchar(50) DEFAULT NULL COMMENT '任务执行时间表达式',
  `remark` varchar(150) DEFAULT NULL COMMENT '任务描述',
  `class_name` varchar(150) DEFAULT NULL COMMENT '任务执行目标',
  `script_name` varchar(150) DEFAULT NULL COMMENT '任务脚本\n1、classpath:开头 脚本在类路径下\n2、脚本绝对路径',
  `status` int(11) DEFAULT NULL COMMENT '任务状态\n0、正常\n1、暂停',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_task_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='定时作业';

-- ----------------------------
--  Records of `sys_task`
-- ----------------------------
BEGIN;
INSERT INTO `sys_task` VALUES ('9', 'Test1', '', '0/4 * * * * ? ', 'Test1', 'com.warrior.schedule.task.TestJob.execute', '', '1', '2017-12-07 18:48:58', '2017-12-08 09:49:51'), ('11', 'Test2', '', '0/10 * * * * ? ', 'Test2', 'com.warrior.schedule.task.TestJob.test2', '', '1', '2017-12-07 19:01:52', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `pass_word` varchar(200) NOT NULL COMMENT '用户密码',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别\n男 女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `img` varchar(200) DEFAULT NULL COMMENT '头像',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类别\n0、普通用户\n1、后台用户',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `salt` varchar(45) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `id_UNIQUE` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '24580d1a6c4be1c9da88f3c7b4408e7d', '男', '16', 'd3bfd3a261bc45cfb6b5dea3f02b1a98', '0', '0', '2017-11-18 16:16:29', '2017-11-17 16:43:31', '8147da4deb2a04dc4ec124f3af7b82fd', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('3', '1', '1'), ('4', '3', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
