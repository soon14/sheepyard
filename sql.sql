

CREATE TABLE MANAGER
(
	ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	USERNO VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
	USERNAME VARCHAR(50) NOT NULL COMMENT '姓名',
	PWD VARCHAR(50) NOT NULL COMMENT '密码',
	PHONE VARCHAR(50) COMMENT '手机',
	REGDATE TIMESTAMP DEFAULT now() COMMENT '注册时间',
	ENABLE SMALLINT(1) DEFAULT 1 COMMENT '是否有效',
	SEX VARCHAR(10) DEFAULT 'man' COMMENT '性别',
	PRIMARY KEY (ID)
) COMMENT = '管理用户' DEFAULT CHARACTER SET utf8 ENGINE = INNODB;


insert into MANAGER (USERNO,USERNAME,PWD,PHONE)VALUES("cc","cc","cc","15810009265");




create table BaseInfo
(
    ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
    mark_num varchar(32) comment '耳标号',
    father_sheep_ID bigint comment '父羊',
    mother_sheep_ID bigint comment '母羊',
    gene_id smallint comment '基因型id',
    sex_genda tinyint(1) default 1 comment '性别',
    type_id smallint(1) comment '类型id',
	birth_day timestamp comment '出生日期',
    enter_day timestamp default now() comment '入场日期',
    pen_id smallint comment '羊舍id',
    variety_id smallint comment  '品种_id',
    is_death tinyint(1) default 1 comment '是否死亡',
    is_sell tinyint(1) default 1 comment '是否销售',
    growth_status smallint comment '当前生长状态',
    primary key(ID),
    foreign key(gene_id) references GeneInfo(id) on delete NO ACTION on update NO ACTION,
    foreign key(type_id) references TypeInfo(id) on delete NO ACTION on update NO ACTION,
    foreign key(pen_id) references PenInfo(id) on delete NO ACTION on update NO ACTION,
    foreign key(variety_id) references VarietyInfo(id) on delete NO ACTION on update NO ACTION,
    foreign key(growth_status) references GrowthStatus(id) on delete NO ACTION on update NO ACTION
)ENGINE = INNODB;
 
create table GeneInfo (
    id smallint NOT NULL AUTO_INCREMENT comment 'id',
    gene_type varchar(32),
    primary key(id)
)ENGINE = INNODB;

create table TypeInfo(
    id smallint NOT NULL AUTO_INCREMENT comment 'id',
    type_name varchar(32),
    primary key(id)
)ENGINE = INNODB;

create table Varietyinfo(
    id smallint NOT NULL AUTO_INCREMENT comment 'id',
    variety_name varchar(32),
    primary key(id)
)ENGINE = INNODB;


insert into `baseinfo`(`FATHER_SHEEP_ID`, `BIRTH_DAY`, `SEX_GENDA`, `TYPE_ID`, `ENTER_DAY`, `MOTHER_SHEEP_ID`, `PEN_ID`, `GENE_ID`, `GROWTH_STATUS`, `MARK_NUM`) values('1', '2011-05-09 11:49:45', '0', '1', '2011-05-09 11:49:45', '2', '1', '1', '1', 'test');

 create table PenInfo(
    id smallint NOT NULL AUTO_INCREMENT comment 'id',
    pen_name varchar(32) comment '羊舍名字',
	location varchar(32) comment '羊舍位置',
    facility varchar(256) comment '设施信息',
	description varchar(256) comment '描述信息',
    capacity smallint comment '容量大小',
    surplus_capacity smallint comment '剩余容量',
    area smallint comment '羊舍面积',
    primary key(id)
 )ENGINE = INNODB;
 
 insert into `peninfo`(`id`,`pen_name`) values('2','羔羊羊舍');
 update baseinfo set variety_id='3';
  
 create table GrowthInfo(
	id bigint not null auto_increment comment 'id',
    sheep_id bigint comment '羊只编号',
    record_time timestamp default now() comment '记录日期',
    weight smallint comment '体重',
    height smallint comment '身高',
    length smallint comment '体长',
    bust smallint comment '胸围',
    hipline smallint comment '臀围',
    circumference smallint comment '管围',
    primary key(id),
    foreign key (sheep_id) references BaseInfo(id) on delete NO ACTION on update NO ACTION
)ENGINE = INNODB;

create table TreatInfo(
    id bigint not null auto_increment comment 'id',
    sheep_id bigint comment '羊只编号',
    record_time timestamp default now() comment '记录日期',
    illness_name varchar(256) comment '疾病名称',
    symptom_des varchar(256) comment '症状描述',
    treat_des varchar(256) comment '治疗描述',
    huanbing_time timestamp comment '患病日期',
    treat_time timestamp comment '诊治日期',
    revoer_time timestamp comment '恢复日期',
    treat_people varchar(64) comment '兽医',
    primary key(id),
    foreign key (sheep_id) references BaseInfo(id) on delete NO ACTION on update NO ACTION
)ENGINE = INNODB;

create table ImmunityInfo(
    id bigint not null auto_increment comment 'id',
    sheep_id bigint comment '羊只编号',
    record_time timestamp default now() comment '记录日期',
    immune_name varchar(256) comment '疫苗名称',
    immune_manu varchar(256) comment '疫苗厂家',
    immunation_time timestamp comment '接种日期',
    immunation_way varchar(64) comment '接种方式',
    immunation_person varchar(64) comment '接种人',
    primary key(id),
    foreign key (sheep_id) references BaseInfo(id) on delete NO ACTION on update NO ACTION
)ENGINE = INNODB;

create table QuarantineInfo(
    id bigint not null auto_increment comment 'id',
    sheep_id bigint comment '羊只编号',
    record_time timestamp default now() comment '记录日期',
    vaccine_name varchar(256) comment '检疫疾病',
    vaccination_time timestamp comment '检疫日期',
    vaccination_way varchar(64) comment '检疫结果',
    vaccination_person varchar(64) comment '检疫人',
    primary key(id),
    foreign key (sheep_id) references BaseInfo(id) on delete NO ACTION on update NO ACTION
)ENGINE = INNODB;

 
create table GrowthStatus(
    id smallint not null auto_increment comment 'id',
	growth_status_name varchar(256) comment '生长状态名字',
    primary key(id)
)ENGINE = INNODB;
 

 

create table GrowthStatusConfig(
    id bigint not null auto_increment comment 'id',
    from_id smallint comment '来源状态',
    to_id smallint comment '到达状态',
    config_time smallint comment '转化时间',
    primary key(id),
    foreign key (from_id) references GrowthStatus(id) on delete NO ACTION on update NO ACTION,
    foreign key (to_id) references GrowthStatus(id) on delete NO ACTION on update NO ACTION
)ENGINE = INNODB;

insert into GrowthStatusConfig (from_id,to_id,config_time)VALUES("1","2","8");
 

 