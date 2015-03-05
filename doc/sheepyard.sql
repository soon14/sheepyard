SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE AREA;
DROP TABLE COLLEGE;
DROP TABLE INSTRUCTOR;
DROP TABLE MANAGER;
DROP TABLE STUDENT;
DROP TABLE SUBJECT;




/* Create Tables */

CREATE TABLE AREA
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	NAME VARCHAR(20) COMMENT '名称',
	PRIMARY KEY (ID)
) COMMENT = '地区' DEFAULT CHARACTER SET utf8;


CREATE TABLE COLLEGE
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	NAME VARCHAR(20) COMMENT '名称',
	PRIMARY KEY (ID)
) COMMENT = '院校' DEFAULT CHARACTER SET utf8;


CREATE TABLE INSTRUCTOR
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	PHONE VARCHAR(50) COMMENT '手机',
	REGDATE TIMESTAMP COMMENT '注册时间',
	USERNAME VARCHAR(50) COMMENT '姓名',
	USERNO VARCHAR(50) UNIQUE COMMENT '用户名',
	PWD VARCHAR(50) COMMENT '密码',
	EMAIL VARCHAR(100) UNIQUE COMMENT 'email',
	SEX VARCHAR(10) DEFAULT '男' COMMENT '性别',
	ADDR VARCHAR(100) COMMENT '住址',
	PRIMARY KEY (ID)
) COMMENT = '教员' DEFAULT CHARACTER SET utf8;


CREATE TABLE MANAGER
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	USERNO VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
	USERNAME VARCHAR(50) NOT NULL COMMENT '姓名',
	PWD VARCHAR(50) NOT NULL COMMENT '密码',
	PHONE VARCHAR(50) COMMENT '手机',
	REGDATE TIMESTAMP DEFAULT now() COMMENT '注册时间',
	ENABLE SMALLINT(1) DEFAULT 1 COMMENT '是否有效',
	SEX VARCHAR(10) DEFAULT '男' COMMENT '性别',
	PRIMARY KEY (ID)
) COMMENT = '管理用户' DEFAULT CHARACTER SET utf8;


CREATE TABLE STUDENT
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	PHONE VARCHAR(50) COMMENT '手机',
	REGDATE TIMESTAMP COMMENT '注册时间',
	USERNAME VARCHAR(50) COMMENT '姓名',
	USERNO VARCHAR(50) UNIQUE COMMENT '用户名',
	PWD VARCHAR(50) COMMENT '密码',
	EMAIL VARCHAR(100) UNIQUE COMMENT 'email',
	SEX VARCHAR(10) DEFAULT '男' COMMENT '性别',
	ADDR VARCHAR(100) COMMENT '住址',
	PRIMARY KEY (ID)
) COMMENT = '学员' DEFAULT CHARACTER SET utf8;


CREATE TABLE SUBJECT
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	NAME VARCHAR(20) COMMENT '名称',
	PRIMARY KEY (ID)
) COMMENT = '学科' DEFAULT CHARACTER SET utf8;



