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

 Date: 09/27/2017 10:11:13 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `warrior_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_dictionary`;
CREATE TABLE `warrior_dictionary` (
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_dictionary` VALUES ('1', '0', '启用', '2', '0', '1', '2017-07-28 18:14:14', '状态'), ('2', '1', '禁用', '3', '0', '1', '2017-07-28 18:14:14', '状态'), ('3', '2', '删除', '4', '0', '1', '2017-07-28 18:16:36', '状态'), ('4', '0', '显示', '2', '0', '2', '2017-07-28 18:16:36', '是否显示'), ('5', '1', '不显示', '3', '0', '2', '2017-07-28 18:16:36', '是否显示'), ('6', '0', '菜单', '2', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('7', '1', '按钮', '3', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('8', '2', '数据', '4', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('9', '1', '管理员', '1', '0', '4', '2017-09-20 10:36:20', '用户类型'), ('10', '2', '普通用户', '2', '0', '4', '2017-09-20 10:36:53', '用户类型');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_permissions`;
CREATE TABLE `warrior_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `own_id` int(11) NOT NULL COMMENT '拥有者 ID',
  `res_id` int(11) NOT NULL COMMENT '资源 ID',
  `type` int(11) DEFAULT NULL COMMENT '拥有者类型\n0、角色\n1、用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_permissions`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_permissions` VALUES ('1', '1', '1', '0'), ('2', '1', '2', '0'), ('3', '1', '3', '0'), ('4', '1', '4', '0'), ('5', '1', '5', '0'), ('6', '1', '6', '0'), ('7', '1', '7', '0'), ('8', '1', '8', '0'), ('9', '1', '9', '0'), ('10', '1', '10', '0'), ('11', '1', '11', '0'), ('12', '1', '12', '0'), ('13', '1', '13', '0'), ('14', '1', '14', '0'), ('15', '1', '15', '0'), ('16', '1', '16', '0'), ('17', '1', '17', '0'), ('18', '1', '18', '0'), ('19', '1', '19', '0'), ('20', '1', '20', '0'), ('21', '1', '21', '0'), ('22', '1', '22', '0');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_resources`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_resources`;
CREATE TABLE `warrior_resources` (
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_resources`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_resources` VALUES ('1', '用户管理', '0', '/user/list', '0', '0', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:view', 'person', '0'), ('2', '资源管理', '0', '/resource/list', '2', '0', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:view', 'ios-folder', '0'), ('3', '角色管理', '0', '/role/list', '1', '0', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:view', 'person', '0'), ('4', '字典管理', '0', '/dict/list', '2', '0', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:view', 'briefcase', '0'), ('5', '新增', '1', '', '2', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:add', '', '1'), ('6', '修改', '1', '', '3', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:update', '', '1'), ('7', '删除', '1', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:user:del', '', '1'), ('8', '用户权限查询', '1', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userperm:view', '', '1'), ('9', '用户权限修改', '1', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userperm:update', '', '1'), ('10', '新增', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:add', '', '1'), ('11', '删除', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:del', '', '1'), ('12', '修改', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:role:update', '', '1'), ('13', '用户角色查询', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userrole:view', '', '1'), ('14', '用户角色修改', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:userrole:update', '', '1'), ('15', '角色权限查询', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:roleperm:view', '', '1'), ('16', '角色权限修改', '3', '', '1', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:roleperm:update', '', '1'), ('17', '新增', '2', '', '2', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:add', '', '1'), ('18', '修改', '2', '', '3', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:update', '', '1'), ('19', '删除', '2', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:resource:del', '', '1'), ('20', '新增', '4', '', '2', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:add', '', '1'), ('21', '修改', '4', '', '3', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:update', '', '1'), ('22', '删除', '4', '', '4', '1', '', '0', '2017-07-31 10:08:01', '2017-07-31 10:08:01', 'admin:dict:del', '', '1');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_role`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_role`;
CREATE TABLE `warrior_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(120) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rid_UNIQUE` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_role`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_role` VALUES ('1', 'admin', 'admin', '0', '2017-07-31 10:06:27', '2017-07-31 10:06:27');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_user`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_user`;
CREATE TABLE `warrior_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `pass_word` varchar(200) NOT NULL COMMENT '用户密码',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别  男 女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类别\n0、普通用户\n1、后台用户',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `salt` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `id_UNIQUE` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_user`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_user` VALUES ('1', 'admin', '3c6f7e551216a7cf19832575bd0ed817', '男', '0', '1', '0', '2017-07-31 10:07:24', '2017-07-31 10:07:24', '61cdd9ba29ddf0d0589209d57d4bc95c');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_user_role`;
CREATE TABLE `warrior_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_user_role` VALUES ('1', '1', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
