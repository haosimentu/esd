/*
Navicat MySQL Data Transfer

Source Server         : esd
Source Server Version : 50173
Source Host           : 121.199.54.69:3306
Source Database       : esd

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2014-10-31 17:09:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `AUTHORITY`
-- ----------------------------
DROP TABLE IF EXISTS `AUTHORITY`;
CREATE TABLE `AUTHORITY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USERID` bigint(20) NOT NULL,
  `ROLE` varchar(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_AUTHORITY_USER` (`USERID`),
  CONSTRAINT `FK_AUTHORITY_USER` FOREIGN KEY (`USERID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of AUTHORITY
-- ----------------------------
INSERT INTO `AUTHORITY` VALUES ('1', '1', 'ROLE_ADMIN');
INSERT INTO `AUTHORITY` VALUES ('2', '1', 'ROLE_USER');
INSERT INTO `AUTHORITY` VALUES ('3', '2', 'ROLE_ENGINEER');
INSERT INTO `AUTHORITY` VALUES ('4', '2', 'ROLE_USER');
INSERT INTO `AUTHORITY` VALUES ('5', '3', 'ROLE_USER');

-- ----------------------------
-- Table structure for `HISTORY`
-- ----------------------------
DROP TABLE IF EXISTS `HISTORY`;
CREATE TABLE `HISTORY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STATIONID` bigint(20) NOT NULL,
  `STARTTIME` datetime NOT NULL,
  `ENDTIME` datetime NOT NULL,
  `GATHERTIME` datetime DEFAULT NULL,
  `RESULT` varchar(8) COLLATE utf8_bin NOT NULL,
  `DURATION` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of HISTORY
-- ----------------------------

-- ----------------------------
-- Table structure for `MENU`
-- ----------------------------
DROP TABLE IF EXISTS `MENU`;
CREATE TABLE `MENU` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `URL` varchar(128) COLLATE utf8_bin NOT NULL,
  `ROLE` varchar(32) COLLATE utf8_bin NOT NULL,
  `DESC` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of MENU
-- ----------------------------
INSERT INTO `MENU` VALUES ('1', '账号管理', '/admin/account.html', 'ROLE_ADMIN', '账号增删改查');
INSERT INTO `MENU` VALUES ('2', '实时数据', '/user/monitor.html', 'ROLE_USER', '实时数据展示');
INSERT INTO `MENU` VALUES ('3', '历史数据', '/usr/history.html', 'ROLE_USER', '历史数据查询');
INSERT INTO `MENU` VALUES ('4', '设备配置', '/engineer/config.html', 'ROLE_ENGINEER', '生产线设备配置');

-- ----------------------------
-- Table structure for `MONITOR`
-- ----------------------------
DROP TABLE IF EXISTS `MONITOR`;
CREATE TABLE `MONITOR` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STATIONID` bigint(20) NOT NULL,
  `STARTTIME` datetime NOT NULL,
  `ENDTIME` datetime NOT NULL,
  `GATHERTIME` datetime DEFAULT NULL,
  `RESULT` varchar(8) COLLATE utf8_bin NOT NULL,
  `DURATION` int(11) DEFAULT '0' COMMENT '持续时间长度，单位分钟',
  PRIMARY KEY (`ID`),
  KEY `FK_MONITOR_STATION` (`STATIONID`),
  CONSTRAINT `FK_MONITOR_STATION` FOREIGN KEY (`STATIONID`) REFERENCES `STATION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of MONITOR
-- ----------------------------

-- ----------------------------
-- Table structure for `PIPELINE`
-- ----------------------------
DROP TABLE IF EXISTS `PIPELINE`;
CREATE TABLE `PIPELINE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `NUMBER` varchar(8) COLLATE utf8_bin NOT NULL,
  `STATE` smallint(6) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  `STATIONS` int(11) NOT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DESC` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of PIPELINE
-- ----------------------------

-- ----------------------------
-- Table structure for `STATION`
-- ----------------------------
DROP TABLE IF EXISTS `STATION`;
CREATE TABLE `STATION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `PIPELINEID` bigint(20) NOT NULL,
  `NUMBER` varchar(16) COLLATE utf8_bin NOT NULL,
  `STATE` smallint(6) NOT NULL DEFAULT '1' COMMENT ' 1正常 0 删除',
  `ADDRESS` varchar(128) COLLATE utf8_bin NOT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DESC` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STATION_PIPELINE` (`PIPELINEID`),
  KEY `ID` (`ID`),
  KEY `ID_2` (`ID`),
  KEY `ID_3` (`ID`),
  KEY `ID_4` (`ID`),
  CONSTRAINT `FK_STATION_PIPELINE` FOREIGN KEY (`PIPELINEID`) REFERENCES `PIPELINE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of STATION
-- ----------------------------

-- ----------------------------
-- Table structure for `USER`
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACCOUNT` varchar(64) COLLATE utf8_bin NOT NULL,
  `PASSWORD` varchar(128) COLLATE utf8_bin NOT NULL,
  `MOBILE` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `LOCKED` bit(1) NOT NULL DEFAULT b'0',
  `EXPIRATION` datetime NOT NULL DEFAULT '2020-12-30 00:00:00',
  `ENABLED` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of USER
-- ----------------------------
INSERT INTO `USER` VALUES ('1', 'admin', 'admin', 'admin', null, 'admin@126.com', '0', '2020-12-30 00:00:00', '1');
INSERT INTO `USER` VALUES ('2', 'engineer', 'engineer', 'engineer', null, 'engineer@126.com', '0', '2020-12-30 00:00:00', '1');
INSERT INTO `USER` VALUES ('3', 'user', 'user', 'user', null, 'user@126.com', '0', '2020-12-30 00:00:00', '1');


DELIMITER $$

DROP TRIGGER /*!50032 IF EXISTS */ `INSERT_MONITOR_TRIGGER`$$

CREATE
    /*!50017 DEFINER = 'esd'@'%' */
    TRIGGER `INSERT_MONITOR_TRIGGER` AFTER INSERT ON `MONITOR` 
    FOR EACH ROW BEGIN
	INSERT INTO HISTORY(PIPELINEID,PIPELINENAME,STATIONID,STATIONNAME,MONITORTIME,RESULT,VALUEE,DURATION)
     VALUES(NEW.PIPELINEID,NEW.PIPELINENAME,NEW.STATIONID,NEW.STATIONNAME,NEW.MONITORTIME,NEW.RESULT,NEW.VALUEE,NEW.DURATION);
END;
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER /*!50032 IF EXISTS */ `UPDATE_MONITOR_TRIGGER`$$

CREATE
    /*!50017 DEFINER = 'esd'@'%' */
    TRIGGER `UPDATE_MONITOR_TRIGGER` AFTER UPDATE ON `MONITOR` 
    FOR EACH ROW BEGIN
    INSERT INTO HISTORY(PIPELINEID,PIPELINENAME,STATIONID,STATIONNAME,MONITORTIME,RESULT,VALUEE,DURATION)
     VALUES(NEW.PIPELINEID,NEW.PIPELINENAME,NEW.STATIONID,NEW.STATIONNAME,NEW.MONITORTIME,NEW.RESULT,NEW.VALUEE,NEW.DURATION);
END;
$$

DELIMITER ;