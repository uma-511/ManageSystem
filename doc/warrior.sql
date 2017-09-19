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

 Date: 09/19/2017 17:49:45 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `warrior_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_dictionary`;
CREATE TABLE `warrior_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dicKey` varchar(45) DEFAULT NULL COMMENT '字典key',
  `dicValue` varchar(45) DEFAULT NULL COMMENT '字典值',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `isShow` int(11) DEFAULT NULL COMMENT '是否显示\n0、显示\n1、不显示',
  `dicType` int(11) DEFAULT NULL COMMENT '字典类型',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_dictionary` VALUES ('2', '0', '启用', '2', '0', '1', '2017-07-28 18:14:14', '状态'), ('3', '1', '禁用', '3', '0', '1', '2017-07-28 18:14:14', '状态'), ('4', '2', '删除', '4', '0', '1', '2017-07-28 18:16:36', '状态'), ('6', '0', '显示', '2', '0', '2', '2017-07-28 18:16:36', '是否显示'), ('7', '1', '不显示', '3', '0', '2', '2017-07-28 18:16:36', '是否显示'), ('9', '0', '菜单', '2', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('10', '1', '按钮', '3', '0', '3', '2017-07-28 18:16:36', '资源类型'), ('11', '2', '数据', '4', '0', '3', '2017-07-28 18:16:36', '资源类型');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_permissions`;
CREATE TABLE `warrior_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ownId` int(11) NOT NULL COMMENT '拥有者 ID',
  `resId` int(11) NOT NULL COMMENT '资源 ID',
  `type` int(11) DEFAULT NULL COMMENT '拥有者类型\n0、角色\n1、用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_permissions`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_permissions` VALUES ('1', '1', '2', '0'), ('2', '1', '3', '0'), ('3', '1', '4', '0'), ('4', '1', '5', '0'), ('5', '1', '6', '0');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_resources`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_resources`;
CREATE TABLE `warrior_resources` (
  `resId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `resName` varchar(120) DEFAULT NULL COMMENT '资源名称',
  `parentId` int(11) DEFAULT NULL COMMENT '父级资源ID',
  `url` varchar(200) DEFAULT NULL COMMENT '功能地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `isShow` int(11) DEFAULT NULL COMMENT '是否显示\n0、显示\n1、不显示',
  `remark` varchar(200) DEFAULT NULL COMMENT '功能描述',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `permission` varchar(45) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '0、菜单\n1、按钮\n2、数据',
  PRIMARY KEY (`resId`),
  UNIQUE KEY `resId_UNIQUE` (`resId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_resources`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_resources` VALUES ('2', '用户管理', '0', '', '1', '0', '', '0', '2017-07-24 16:02:00', '2017-07-24 16:02:00', 'admin:user:view', 'person', '0'), ('3', '用户查询', '2', '/user/list', '0', '0', '', '0', '2017-07-24 16:08:50', '2017-07-24 16:08:50', 'admin:user:view', 'person', '0'), ('4', '资源管理', '0', '', '2', '0', '', '0', '2017-07-28 04:03:20', '2017-07-28 04:03:20', 'admin:resource:view', 'ios-folder', '0'), ('5', '资源列表', '4', '/resource/list', '1', '0', '', '0', '2017-07-28 13:12:12', '2017-07-28 13:12:12', 'admin:resource:view', 'ios-folder', '0'), ('12', 'Test', '5', '/test', '2', '0', '', '0', '2017-09-19 16:46:38', '2017-09-19 16:46:38', '', 'cloud', '0'), ('13', 'Test2', '5', '/test2', '1', '0', '', '0', null, null, '', 'cloud', '0');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_role`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_role`;
CREATE TABLE `warrior_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleName` varchar(120) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rid_UNIQUE` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_role`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_role` VALUES ('1', 'admin', 'admin', '0', '2017-07-24 16:02:00', '2017-07-24 16:02:00');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_user`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_user`;
CREATE TABLE `warrior_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) NOT NULL COMMENT '用户名',
  `passWord` varchar(200) NOT NULL COMMENT '用户密码',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别  男 女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `userType` int(11) DEFAULT NULL COMMENT '用户类别\n0、普通用户\n1、后台用户',
  `status` int(11) DEFAULT NULL COMMENT '状态\n0、正常\n1、禁用\n2、删除',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `salt` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `id_UNIQUE` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `warrior_user`
-- ----------------------------
BEGIN;
INSERT INTO `warrior_user` VALUES ('1', 'admin', '3c6f7e551216a7cf19832575bd0ed817', null, '0', '0', '0', '2017-08-24 16:22:00', null, '61cdd9ba29ddf0d0589209d57d4bc95c');
COMMIT;

-- ----------------------------
--  Table structure for `warrior_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `warrior_user_role`;
CREATE TABLE `warrior_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户编号',
  `roleId` int(11) NOT NULL COMMENT '角色ID',
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
