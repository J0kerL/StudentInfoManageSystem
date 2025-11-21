-- 学生信息管理系统数据库设计

-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_info_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE student_info_system;

-- 学生表
CREATE TABLE student (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生ID',
    student_number VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('MALE', 'FEMALE') NOT NULL COMMENT '性别',
    birth_date DATE NOT NULL COMMENT '出生日期',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    address VARCHAR(200) COMMENT '家庭地址',
    enrollment_date DATE NOT NULL COMMENT '入学日期',
    major_id BIGINT UNSIGNED COMMENT '专业ID',
    class_id BIGINT UNSIGNED COMMENT '班级ID',
    status ENUM('ACTIVE', 'INACTIVE', 'GRADUATED', 'TRANSFERRED') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_student_number (student_number),
    INDEX idx_name (name),
    INDEX idx_major_id (major_id),
    INDEX idx_class_id (class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生信息表';

-- 专业表
CREATE TABLE major (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '专业ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '专业名称',
    code VARCHAR(20) NOT NULL UNIQUE COMMENT '专业代码',
    description TEXT COMMENT '专业描述',
    college VARCHAR(100) COMMENT '所属学院',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专业信息表';

-- 班级表
CREATE TABLE class (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '班级ID',
    name VARCHAR(100) NOT NULL COMMENT '班级名称',
    major_id BIGINT UNSIGNED NOT NULL COMMENT '专业ID',
    grade VARCHAR(10) NOT NULL COMMENT '年级',
    advisor VARCHAR(50) COMMENT '辅导员',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_major_id (major_id),
    INDEX idx_grade (grade)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级信息表';

-- 课程表
CREATE TABLE course (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    code VARCHAR(20) NOT NULL UNIQUE COMMENT '课程代码',
    name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credit DECIMAL(3,1) NOT NULL COMMENT '学分',
    hours INT NOT NULL COMMENT '学时',
    description TEXT COMMENT '课程描述',
    teacher VARCHAR(50) COMMENT '授课教师',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_code (code),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程信息表';

-- 成绩表
CREATE TABLE score (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
    student_id BIGINT UNSIGNED NOT NULL COMMENT '学生ID',
    course_id BIGINT UNSIGNED NOT NULL COMMENT '课程ID',
    score DECIMAL(5,2) NOT NULL COMMENT '成绩分数',
    grade VARCHAR(5) COMMENT '成绩等级(A+, A, B+, B, C+, C, D, F)',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    exam_date DATE COMMENT '考试日期',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_student_course_semester (student_id, course_id, semester),
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id),
    INDEX idx_semester (semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩信息表';

-- 教师表
CREATE TABLE teacher (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师ID',
    employee_number VARCHAR(20) NOT NULL UNIQUE COMMENT '工号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('MALE', 'FEMALE') NOT NULL COMMENT '性别',
    birth_date DATE NOT NULL COMMENT '出生日期',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    department VARCHAR(100) COMMENT '所属部门',
    title VARCHAR(50) COMMENT '职称',
    hire_date DATE NOT NULL COMMENT '入职日期',
    status ENUM('ACTIVE', 'INACTIVE', 'RETIRED') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_employee_number (employee_number),
    INDEX idx_name (name),
    INDEX idx_department (department)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教师信息表';

-- 课程安排表
CREATE TABLE course_schedule (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程安排ID',
    course_id BIGINT UNSIGNED NOT NULL COMMENT '课程ID',
    teacher_id BIGINT UNSIGNED NOT NULL COMMENT '教师ID',
    class_id BIGINT UNSIGNED NOT NULL COMMENT '班级ID',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    classroom VARCHAR(50) COMMENT '教室',
    weekday ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') COMMENT '星期',
    start_time TIME COMMENT '开始时间',
    end_time TIME COMMENT '结束时间',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_course_id (course_id),
    INDEX idx_teacher_id (teacher_id),
    INDEX idx_class_id (class_id),
    INDEX idx_semester (semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程安排表';

-- 系统用户表
CREATE TABLE sys_user (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    user_type ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL COMMENT '用户类型',
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    last_login_time TIMESTAMP NULL COMMENT '最后登录时间',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_username (username),
    INDEX idx_user_type (user_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 插入初始数据
-- 插入专业数据
INSERT INTO major (name, code, description, college) VALUES
('计算机科学与技术', 'CS001', '学习计算机系统和计算原理的专业', '计算机学院'),
('软件工程', 'SE001', '研究软件开发和维护的专业', '计算机学院'),
('电子信息工程', 'EE001', '研究电子技术和信息系统工程的专业', '电子工程学院'),
('机械设计制造及其自动化', 'ME001', '研究机械设计制造及自动化控制的专业', '机械工程学院');

-- 插入班级数据
INSERT INTO class (name, major_id, grade, advisor) VALUES
('计算机科学与技术2021级1班', 1, '2021', '张老师'),
('计算机科学与技术2021级2班', 1, '2021', '李老师'),
('软件工程2021级1班', 2, '2021', '王老师'),
('电子信息工程2021级1班', 3, '2021', '赵老师');

-- 插入学生数据
INSERT INTO student (student_number, name, gender, birth_date, phone, email, address, enrollment_date, major_id, class_id) VALUES
('2021001001', '张三', 'MALE', '2003-05-12', '13800000001', 'zhangsan@example.com', '北京市朝阳区', '2021-09-01', 1, 1),
('2021001002', '李四', 'FEMALE', '2003-08-24', '13800000002', 'lisi@example.com', '上海市浦东新区', '2021-09-01', 1, 1),
('2021001003', '王五', 'MALE', '2003-11-03', '13800000003', 'wangwu@example.com', '广州市天河区', '2021-09-01', 1, 2),
('2021002001', '赵六', 'FEMALE', '2003-02-18', '13800000004', 'zhaoliu@example.com', '深圳市南山区', '2021-09-01', 2, 3);

-- 插入课程数据
INSERT INTO course (code, name, credit, hours, description, teacher) VALUES
('CS101', '计算机基础', 3.0, 48, '计算机入门课程', '陈教授'),
('CS201', '数据结构', 4.0, 64, '学习各种数据结构', '刘教授'),
('CS202', '算法分析', 4.0, 64, '学习算法设计与分析', '杨教授'),
('MA101', '高等数学', 5.0, 80, '大学数学基础课程', '孙教授'),
('EN101', '大学英语', 4.0, 64, '英语基础课程', '周老师');

-- 插入教师数据
INSERT INTO teacher (employee_number, name, gender, birth_date, phone, email, department, title, hire_date) VALUES
('T001', '陈教授', 'MALE', '1975-03-15', '13900000001', 'chen@university.edu', '计算机学院', '教授', '2005-09-01'),
('T002', '刘教授', 'FEMALE', '1980-07-22', '13900000002', 'liu@university.edu', '计算机学院', '教授', '2008-09-01'),
('T003', '杨教授', 'MALE', '1978-11-30', '13900000003', 'yang@university.edu', '计算机学院', '副教授', '2010-09-01'),
('T004', '孙教授', 'FEMALE', '1972-01-18', '13900000004', 'sun@university.edu', '数学学院', '教授', '2003-09-01');

-- 插入成绩数据
INSERT INTO score (student_id, course_id, score, grade, semester, exam_date) VALUES
(1, 1, 85.0, 'B+', '2021-2022-1', '2022-01-10'),
(1, 4, 92.0, 'A', '2021-2022-1', '2022-01-12'),
(2, 1, 78.0, 'C+', '2021-2022-1', '2022-01-10'),
(2, 4, 88.0, 'B+', '2021-2022-1', '2022-01-12'),
(3, 1, 95.0, 'A', '2021-2022-1', '2022-01-10'),
(3, 4, 90.0, 'A', '2021-2022-1', '2022-01-12');

-- 插入系统用户数据
-- 默认密码都是: password123
INSERT INTO sys_user (username, password, real_name, phone, email, user_type) VALUES
('admin', '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ123456', '管理员', '13800000000', 'admin@system.edu', 'ADMIN'),
('teacher01', '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ123456', '陈教授', '13900000001', 'chen@university.edu', 'TEACHER'),
('student01', '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ123456', '张三', '13800000001', 'zhangsan@example.com', 'STUDENT');

-- 添加外键约束
ALTER TABLE student ADD CONSTRAINT fk_student_major FOREIGN KEY (major_id) REFERENCES major(id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE student ADD CONSTRAINT fk_student_class FOREIGN KEY (class_id) REFERENCES class(id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE class ADD CONSTRAINT fk_class_major FOREIGN KEY (major_id) REFERENCES major(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE score ADD CONSTRAINT fk_score_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE score ADD CONSTRAINT fk_score_course FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE course_schedule ADD CONSTRAINT fk_schedule_course FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE course_schedule ADD CONSTRAINT fk_schedule_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE course_schedule ADD CONSTRAINT fk_schedule_class FOREIGN KEY (class_id) REFERENCES class(id) ON DELETE CASCADE ON UPDATE CASCADE;