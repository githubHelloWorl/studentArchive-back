SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c
-- ----------------------------
DROP TABLE IF EXISTS `c`;
CREATE TABLE `c`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ccredit` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of c
-- ----------------------------
INSERT INTO `c` VALUES (7, '数据结构', 6);
INSERT INTO `c` VALUES (8, '组合数学', 3);
INSERT INTO `c` VALUES (9, '计算机网络', 5);
INSERT INTO `c` VALUES (10, '计算机组成原理', 5);
INSERT INTO `c` VALUES (11, 'RJC教你做选课系统', 10);
INSERT INTO `c` VALUES (12, 'test说', 22);

-- ----------------------------
-- Table structure for ct
-- ----------------------------
DROP TABLE IF EXISTS `ct`;
CREATE TABLE `ct`  (
  `ctid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NULL DEFAULT NULL,
  `tid` int(11) NULL DEFAULT NULL,
  `term` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`ctid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  CONSTRAINT `ct_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `c` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ct_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `t` (`tid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ct
-- ----------------------------
INSERT INTO `ct` VALUES (5, 8, 4, '22-春季学期');
INSERT INTO `ct` VALUES (6, 7, 4, '22-春季学期');
INSERT INTO `ct` VALUES (7, 10, 13, '22-春季学期');
INSERT INTO `ct` VALUES (8, 9, 13, '22-春季学期');
INSERT INTO `ct` VALUES (9, 11, 4, '22-春季学期');
INSERT INTO `ct` VALUES (10, 9, 4, '22-春季学期');

-- ----------------------------
-- Table structure for s
-- ----------------------------
DROP TABLE IF EXISTS s(sid,sname,password);
CREATE TABLE s(sid,sname,password)  (
  `sid` int(11) NOT NULL AUTO_INCREMENT comment '学号',
  `sname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL comment '姓名',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL comment '密码',
  `sex` ENUM('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '男' comment '性别',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '手机号',
  `department` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '所属院系',
  `major` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci comment '专业',
  `date` timestamp comment '出生日期',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

select * from s;
show tables;
-- ----------------------------
-- Records of s
-- ----------------------------
INSERT INTO s(sid,sname,password) VALUES (1, '阮健乘', '123456');
INSERT INTO s(sid,sname,password) VALUES (2, '张四', '123456');
INSERT INTO s(sid,sname,password) VALUES (3, '李四', '123456');
INSERT INTO s(sid,sname,password) VALUES (4, '彭昊辉', '123456');
INSERT INTO s(sid,sname,password) VALUES (6, '林春霞', '123456');
INSERT INTO s(sid,sname,password) VALUES (7, '董一超', '123456');
INSERT INTO s(sid,sname,password) VALUES (8, '董超', '123456');
INSERT INTO s(sid,sname,password) VALUES (9, '张千', '123456');
INSERT INTO s(sid,sname,password) VALUES (10, '李万', '123456');
INSERT INTO s(sid,sname,password) VALUES (14, '薇尔莉特', '123456');
INSERT INTO s(sid,sname,password) VALUES (21, '庄亮', '123456');
INSERT INTO s(sid,sname,password) VALUES (22, '钟平', '123456');
INSERT INTO s(sid,sname,password) VALUES (23, '李煜豪', '123456');
INSERT INTO s(sname,password) VALUES ('李煜豪', '123456');
select * from s order by sid desc limit 1;
-- ----------------------------
-- Table structure for sct
-- ----------------------------
DROP TABLE IF EXISTS `sct`;
CREATE TABLE `sct`  (
  `sctid` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NULL DEFAULT NULL,
  `cid` int(11) NULL DEFAULT NULL,
  `tid` int(11) NULL DEFAULT NULL,
  `grade` float NULL DEFAULT NULL,
  `term` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sctid`) USING BTREE,
  INDEX `sid`(`sid`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  CONSTRAINT `sct_ibfk_1` FOREIGN KEY (`sid`) REFERENCES s(sid,sname,password) (`sid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sct_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `ct` (`tid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sct_ibfk_3` FOREIGN KEY (`cid`) REFERENCES `ct` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sct
-- ----------------------------
INSERT INTO `sct` VALUES (10, 2, 8, 4, 2, '22-春季学期');
INSERT INTO `sct` VALUES (11, 2, 10, 13, NULL, '22-春季学期');
INSERT INTO `sct` VALUES (12, 2, 7, 4, 5, '22-春季学期');
INSERT INTO `sct` VALUES (13, 4, 8, 4, 10, '22-春季学期');
INSERT INTO `sct` VALUES (14, 4, 7, 4, NULL, '22-春季学期');
INSERT INTO `sct` VALUES (15, 4, 10, 13, NULL, '22-春季学期');
INSERT INTO `sct` VALUES (16, 1, 8, 4, NULL, '22-春季学期');
INSERT INTO `sct` VALUES (17, 1, 10, 13, NULL, '22-春季学期');
INSERT INTO `sct` VALUES (18, 1, 7, 4, NULL, '22-春季学期');

-- ----------------------------
-- Table structure for t
-- ----------------------------
DROP TABLE IF EXISTS `t`;
CREATE TABLE `t`  (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `tname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t
-- ----------------------------
select * from t;
INSERT INTO `t` VALUES (4, '李玉民', '123456');
INSERT INTO `t` VALUES (13, '张三', '123456');
INSERT INTO `t` VALUES (14, '王五2', '123456');
INSERT INTO `t` VALUES (666, 'admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
