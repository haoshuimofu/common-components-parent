create table if not exists t_user(
  id bigint(20) not null auto_increment comment '主键',
  username varchar(20) not null comment '用户名',
  email varchar(40) not null default '' comment '邮箱',
  status tinyint(1) not null default 0 comment '状态',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  primary key(`id`),
  unique index(`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '用户表';

use mydb;
show tables;