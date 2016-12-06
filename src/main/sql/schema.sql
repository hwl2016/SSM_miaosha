--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;
--使用数据库
use seckill;

--创建秒杀库存表
CREATE TABLE `NewTable` (
`seckill_id`  bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id' ,
`name`  varchar(20) NOT NULL COMMENT '商品名称' ,
`number`  int NOT NULL COMMENT '库存数量' ,
`start_time`  timestamp NOT NULL COMMENT '秒杀开启时间' ,
`end_time`  timestamp NOT NULL DEFAULT '' COMMENT '秒杀结束时间' ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
PRIMARY KEY (`seckill_id`),
KEY `idx_start_time` (`start_time`) ,
KEY `idx_end_time` (`end_time`) ,
KEY `idx_create_time` (`create_time`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8
COMMENT='秒杀库存表'
AUTO_INCREMENT=1000
;

--初始化数据
insert into 
	seckill(name,number,start_time,end_time) 
values
	('1000元秒杀iphone7',600,'2016-12-12 00:00:00','2016-12-13 00:00:00'),
	('800元秒杀iphone6',600,'2016-12-12 00:00:00','2016-12-13 00:00:00'),
	('600元秒杀小米5',500,'2016-12-12 00:00:00','2016-12-13 00:00:00'),
	('500元秒杀华为荣耀8',400,'2016-12-12 00:00:00','2016-12-13 00:00:00');

--秒杀成功明细表
	














