set names utf8;
create database `internship_management` DEFAULT CHARACTER SET utf8;
use `internship_management`;
drop table if exists `tb_internship`;
drop table if exists `tb_internship_company`;
drop table if exists `statistical_table1`;
drop table if exists `statistical_table2`;
drop table if exists `statistical_table3`;
create table `tb_internship` (
`id` bigint(20) unsigned not null auto_increment comment '自增主键',
`student_name` varchar(10) not null default '' comment '学生姓名',
`sutdent_no` varchar(20) not null default '' comment '学号',
`internship_company_id` bigint(20) unsigned not null default 0 comment '实习公司id',
`internship_start_date` timestamp not null comment '开始日期',
`internship_end_date` timestamp not null comment '结束日期',
primary key (`id`)
)engine=InnoDB default charset=utf8 COMMENT='学生实习记录表';

create table `tb_internship_company` (
`id` bigint(20) unsigned not null auto_increment comment '自增主键',
`city` varchar(20) not null default '' comment '实习公司所在城市',
`department` varchar(20) not null default '' comment '实习公司所在城市区域',
`company` varchar(30) not null default '' comment '实习公司名称',
primary key(`id`)
) engine=InnoDB default charset=utf8 comment='实习公司表';
create table statistical_table1 (
year_internship_student_count int(10) unsigned not null default 0 comment '今年实习的学生人数',
year_not_internship_student_count int(10) unsigned not null default 0 comment '今年未实习的学生人数'
)engine=InnoDB default charset=utf8 comment='实习统计表1';

create table statistical_table2 (
`city` varchar(20) not null default '' comment '实习公司所在城市',
`department` varchar(20) not null default '' comment '实习公司所在城市区域',
`internship_count` int unsigned not null default 0 comment '实习生个数'
)engine=InnoDB default charset=utf8 comment='实习统计表2';
create table statistical_table3(
		company_name varchar(30) not null default '' comment '公司名称',
		internship_count int(10) not null default 0 comment '招收实习生人数'
		)engine=InnoDB default charset=utf8 comment '实习公司统计表3';
