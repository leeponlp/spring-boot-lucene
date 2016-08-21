/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50626
 Source Host           : localhost
 Source Database       : custom_db

 Target Server Type    : MySQL
 Target Server Version : 50626
 File Encoding         : utf-8

 Date: 08/21/2016 10:22:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_article`
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` int(10) NOT NULL,
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识库';

-- ----------------------------
--  Records of `t_article`
-- ----------------------------
BEGIN;
INSERT INTO `t_article` VALUES ('1', 'Lucene', '本示例简单的实现添加索引操作', '1'), ('2', 'Lucene', '本示例简单的实现删除索引操作', '1'), ('3', 'Lucene', '本示例简单的实现更新索引操作', '1'), ('4', 'Lucene', '本示例简单的实现查询索引操作', '1'), ('5', 'Lucene', '本示例简单的实现关键字查询操作', '1'), ('6', 'Lucene', '本示例简单的实现分页排序操作', '1'), ('7', 'Lucene', 'Lucene是一套用于全文检索和搜寻的开源程式库。', '1'), ('8', 'Lucene', '在Java开发环境里Lucene是一个成熟的免费开源工具。', '1'), ('9', 'Lucene', 'Lucene是当前以及最近几年最受欢迎的免费Java信息检索程序库。', '1'), ('10', 'Lucene', '全文索引技术是目前搜索引擎的关键技术。', '1'), ('11', 'Lucene', '在传统全文检索引擎的倒排索引的基础上，实现了分块索引。', '1'), ('12', 'Lucene', '能够针对新的文件建立小文件索引，提升索引速度。', '1'), ('13', 'Lucene', 'Lucene由Apache软件基金会支持和提供。', '1'), ('14', 'Lucene', 'Lucene在2001年9月做为高质量的开源Java产品加入到Apache软件基金会的 Jakarta家族中。', '1'), ('15', 'Lucene', 'Lucene最初是由Doug Cutting开发\n 载。', '1'), ('16', 'Lucene', 'Lucene是一个高性能、可伸缩的信息搜索(IR)库。', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
