/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50539
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50539
File Encoding         : 65001

Date: 2016-12-08 17:55:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `seckill`
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(20) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone7', '595', '2016-12-06 17:54:45', '2016-12-06 00:00:00', '2016-12-13 00:00:00');
INSERT INTO `seckill` VALUES ('1001', '800元秒杀iphone6', '600', '2016-12-06 17:54:45', '2016-12-12 00:00:00', '2016-12-13 00:00:00');
INSERT INTO `seckill` VALUES ('1002', '600元秒杀小米5', '500', '2016-12-06 17:54:45', '2016-12-12 00:00:00', '2016-12-13 00:00:00');
INSERT INTO `seckill` VALUES ('1003', '500元秒杀华为荣耀8', '400', '2016-12-06 17:54:45', '2016-12-12 00:00:00', '2016-12-13 00:00:00');

-- ----------------------------
-- Table structure for `success_killed`
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态标识：-1：无效；0：成功；1：已付款；2：已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1000', '15201351122', '0', '2016-12-07 20:02:40');
INSERT INTO `success_killed` VALUES ('1000', '15201351133', '0', '2016-12-08 09:24:47');
INSERT INTO `success_killed` VALUES ('1000', '15201351234', '0', '2016-12-07 19:59:54');
INSERT INTO `success_killed` VALUES ('1001', '15201351234', '0', '2016-12-07 15:09:49');
INSERT INTO `success_killed` VALUES ('1001', '15201358461', '-1', '2016-12-07 14:57:19');
INSERT INTO `success_killed` VALUES ('1002', '15201351111', '-1', '2016-12-07 14:57:06');
INSERT INTO `success_killed` VALUES ('1002', '15201358400', '-1', '2016-12-07 14:56:53');
INSERT INTO `success_killed` VALUES ('1002', '15201358461', '-1', '2016-12-07 14:55:38');
