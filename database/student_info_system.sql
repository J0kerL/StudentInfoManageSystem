/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : student_info_system

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/11/2025 10:36:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级名称',
  `major_id` bigint UNSIGNED NOT NULL COMMENT '专业ID',
  `grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '年级',
  `advisor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '辅导员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_major_id`(`major_id` ASC) USING BTREE,
  INDEX `idx_grade`(`grade` ASC) USING BTREE,
  CONSTRAINT `fk_class_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班级信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '计算机科学与技术2025级1班', 1, '2025', '张老师', '2025-11-12 12:03:37', '2025-11-26 11:49:45');
INSERT INTO `class` VALUES (2, '计算机科学与技术2025级2班', 1, '2025', '李老师', '2025-11-12 12:03:37', '2025-11-26 11:49:46');
INSERT INTO `class` VALUES (3, '软件工程2025级1班', 2, '2025', '王老师', '2025-11-12 12:03:37', '2025-11-26 11:49:48');
INSERT INTO `class` VALUES (4, '电子信息工程2025级1班', 3, '2025', '赵老师', '2025-11-12 12:03:37', '2025-11-26 11:49:51');
INSERT INTO `class` VALUES (5, '测试2026级1班', 1, '2026', '测试老师', '2025-11-26 11:35:06', '2025-11-26 11:50:23');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程代码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `credit` decimal(3, 1) NOT NULL COMMENT '学分',
  `hours` int NOT NULL COMMENT '学时',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '课程描述',
  `teacher` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授课教师',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code` ASC) USING BTREE,
  INDEX `idx_code`(`code` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'CS101', '计算机基础', 3.0, 48, '计算机入门课程', '陈教授', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `course` VALUES (2, 'CS201', '数据结构', 4.0, 64, '学习各种数据结构', '刘教授', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `course` VALUES (3, 'CS202', '算法分析', 4.0, 64, '学习算法设计与分析', '杨教授', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `course` VALUES (4, 'MA101', '高等数学', 5.0, 80, '大学数学基础课程', '孙教授', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `course` VALUES (5, 'EN101', '大学英语', 4.0, 64, '英语基础课程', '周老师', '2025-11-12 12:03:37', '2025-11-12 12:03:37');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '专业ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专业名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专业代码',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '专业描述',
  `college` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属学院',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE,
  UNIQUE INDEX `code`(`code` ASC) USING BTREE,
  INDEX `idx_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '专业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '计算机科学与技术', 'CS001', '学习计算机系统和计算原理的专业', '计算机学院', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `major` VALUES (2, '软件工程', 'SE001', '研究软件开发和维护的专业', '计算机学院', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `major` VALUES (3, '电子信息工程', 'EE001', '研究电子技术和信息系统工程的专业', '电子工程学院', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `major` VALUES (4, '机械设计制造及其自动化', 'ME001', '研究机械设计制造及自动化控制的专业', '机械工程学院', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `major` VALUES (5, '测试专业', 'CSZY001', '测试专业', '测试学院', '2025-11-28 09:43:37', '2025-11-28 09:43:37');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` bigint UNSIGNED NOT NULL COMMENT '学生ID',
  `course_id` bigint UNSIGNED NOT NULL COMMENT '课程ID',
  `score` decimal(5, 2) NOT NULL COMMENT '成绩分数',
  `grade` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '成绩等级(A+, A, B+, B, C+, C, D, F)',
  `semester` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学期',
  `exam_date` date NULL DEFAULT NULL COMMENT '考试日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course_semester`(`student_id` ASC, `course_id` ASC, `semester` ASC) USING BTREE,
  INDEX `idx_student_id`(`student_id` ASC) USING BTREE,
  INDEX `idx_course_id`(`course_id` ASC) USING BTREE,
  INDEX `idx_semester`(`semester` ASC) USING BTREE,
  CONSTRAINT `fk_score_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_score_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '成绩信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `student_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `gender` enum('MALE','FEMALE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '家庭地址',
  `enrollment_date` date NOT NULL COMMENT '入学日期',
  `major_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '专业ID',
  `class_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '班级ID',
  `status` enum('ACTIVE','INACTIVE','GRADUATED','TRANSFERRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_number`(`student_number` ASC) USING BTREE,
  INDEX `idx_student_number`(`student_number` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_major_id`(`major_id` ASC) USING BTREE,
  INDEX `idx_class_id`(`class_id` ASC) USING BTREE,
  CONSTRAINT `fk_student_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_student_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '2025001001', '昭阳', 'MALE', '13800000001', 'zhaoyang@luku.com', '江苏省徐州市', '2025-09-01', 1, 1, 'ACTIVE', '2025-11-25 13:57:14', '2025-11-26 11:07:02');
INSERT INTO `student` VALUES (2, '2025001002', '米彩', 'FEMALE', '13800000002', 'micai@zhuomei.com', '江苏省苏州市', '2025-09-01', 1, 1, 'ACTIVE', '2025-11-25 14:06:53', '2025-11-26 11:07:03');
INSERT INTO `student` VALUES (3, '2025001003', '乐瑶', 'FEMALE', '13800000003', 'leyao@actress.com', '北京市东城区', '2025-09-01', 1, 1, 'ACTIVE', '2025-11-25 14:11:55', '2025-11-26 11:07:05');
INSERT INTO `student` VALUES (4, '2025001005', '测试123', 'FEMALE', '13800000008', 'test@test.com', '测试省测试市', '2026-09-01', 2, 5, 'ACTIVE', '2025-11-26 11:35:20', '2025-11-26 11:57:41');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `user_type` enum('ADMIN','TEACHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户类型',
  `status` enum('ACTIVE','INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_user_type`(`user_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$12$N7RJajdRNh5sLl99tuB18euJhpiLmkDsUEdmcTBuYcovSWpSdfsu.', '管理员', '13800000000', 'admin@system.edu', 'ADMIN', 'ACTIVE', '2025-11-25 10:46:21', '2025-11-25 10:46:27');
INSERT INTO `sys_user` VALUES (2, 'teacher01', '$2a$12$N7RJajdRNh5sLl99tuB18euJhpiLmkDsUEdmcTBuYcovSWpSdfsu.', '陈教授', '13900000001', 'chen@university.edu', 'TEACHER', 'ACTIVE', '2025-11-12 12:03:37', '2025-11-18 17:36:12');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `employee_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `gender` enum('MALE','FEMALE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `birth_date` date NOT NULL COMMENT '出生日期',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属部门',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职称',
  `hire_date` date NOT NULL COMMENT '入职日期',
  `status` enum('ACTIVE','INACTIVE','RETIRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `employee_number`(`employee_number` ASC) USING BTREE,
  INDEX `idx_employee_number`(`employee_number` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_department`(`department` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'T001', '陈教授', 'MALE', '1975-03-15', '13900000001', 'chen@university.edu', '计算机学院', '教授', '2005-09-01', 'ACTIVE', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `teacher` VALUES (2, 'T002', '刘教授', 'FEMALE', '1980-07-22', '13900000002', 'liu@university.edu', '计算机学院', '教授', '2008-09-01', 'ACTIVE', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `teacher` VALUES (3, 'T003', '杨教授', 'MALE', '1978-11-30', '13900000003', 'yang@university.edu', '计算机学院', '副教授', '2010-09-01', 'ACTIVE', '2025-11-12 12:03:37', '2025-11-12 12:03:37');
INSERT INTO `teacher` VALUES (4, 'T004', '孙教授', 'FEMALE', '1972-01-18', '13900000004', 'sun@university.edu', '数学学院', '教授', '2003-09-01', 'ACTIVE', '2025-11-12 12:03:37', '2025-11-12 12:03:37');

SET FOREIGN_KEY_CHECKS = 1;
