/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-11-16 11:09:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for reservedfund_luoyang
-- ----------------------------
DROP TABLE IF EXISTS `reservedfund_luoyang`;
CREATE TABLE `reservedfund_luoyang` (
  `id` varchar(32) NOT NULL,
  `content` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '调取洛阳公积金接口前存入的参数内容',
  `insert_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据插入时间',
  `idno` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `percode` varchar(100) DEFAULT NULL,
  `corpcode` varchar(100) DEFAULT NULL,
  `buscode` varchar(100) DEFAULT NULL,
  `fetchrea` varchar(100) DEFAULT NULL,
  `fetchtype` varchar(100) DEFAULT NULL,
  `curcode` varchar(100) DEFAULT NULL,
  `curname` varchar(100) DEFAULT NULL,
  `curdepcode` varchar(100) DEFAULT NULL,
  `isdelacc` varchar(100) DEFAULT NULL,
  `auditflag` varchar(100) DEFAULT NULL,
  `relno` varchar(100) DEFAULT NULL,
  `allrequiremny` varchar(100) DEFAULT NULL,
  `allstandmny` varchar(100) DEFAULT NULL,
  `houseno` varchar(100) DEFAULT NULL,
  `isAuto` varchar(2) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `bkcard` varchar(100) DEFAULT NULL,
  `yhhb` varchar(100) DEFAULT NULL,
  `bkcardname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
