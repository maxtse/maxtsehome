set names utf8mb4;
create database if not exists learn_00;
use learn_00;
create table `order_info` (`id` int(10) unsigned not null auto_increment comment '自增主键',
`order_no` varchar(20) not null default '' comment '订单号',
`source` varchar(10) not null default 'web' comment '来源',
`create_time` timestamp not null default '2000-01-01 00:00:00' comment '创建日期',
`last_update` timestamp not null default current_timestamp on update current_timestamp comment '最后更新时间',
primary key(`id`),
unique `uniq_order_no` (`order_no`),
key `key_create_time`(`create_time`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment '订单分库测试表';

create database if not exists learn_01;
use learn_01;
create table `order_info` (`id` int(10) unsigned not null auto_increment comment '自增主键',
`order_no` varchar(20) not null default '' comment '订单号',
`source` varchar(10) not null default 'web' comment '来源',
`create_time` timestamp not null default '2000-01-01 00:00:00' comment '创建日期',
`last_update` timestamp not null default current_timestamp on update current_timestamp comment '最后更新时间',
primary key(`id`),
unique `uniq_order_no` (`order_no`),
key `key_create_time`(`create_time`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment '订单分库测试表';

create database if not exists learn_02;
use learn_02;
create table `order_info` (`id` int(10) unsigned not null auto_increment comment '自增主键',
`order_no` varchar(20) not null default '' comment '订单号',
`source` varchar(10) not null default 'web' comment '来源',
`create_time` timestamp not null default '2000-01-01 00:00:00' comment '创建日期',
`last_update` timestamp not null default current_timestamp on update current_timestamp comment '最后更新时间',
primary key(`id`),
unique `uniq_order_no` (`order_no`),
key `key_create_time`(`create_time`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment '订单分库测试表';

create database if not exists learn_03;
use learn_03;
create table `order_info` (`id` int(10) unsigned not null auto_increment comment '自增主键',
`order_no` varchar(20) not null default '' comment '订单号',
`source` varchar(10) not null default 'web' comment '来源',
`create_time` timestamp not null default '2000-01-01 00:00:00' comment '创建日期',
`last_update` timestamp not null default current_timestamp on update current_timestamp comment '最后更新时间',
primary key(`id`),
unique `uniq_order_no` (`order_no`),
key `key_create_time`(`create_time`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment '订单分库测试表';

create database if not exists learn_04;
use learn_04;
create table `order_info` (`id` int(10) unsigned not null auto_increment comment '自增主键',
`order_no` varchar(20) not null default '' comment '订单号',
`source` varchar(10) not null default 'web' comment '来源',
`create_time` timestamp not null default '2000-01-01 00:00:00' comment '创建日期',
`last_update` timestamp not null default current_timestamp on update current_timestamp comment '最后更新时间',
primary key(`id`),
unique `uniq_order_no` (`order_no`),
key `key_create_time`(`create_time`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment '订单分库测试表';