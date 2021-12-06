create database ds_0;
use ds_0;
create table t_order(
  order_id varchar(20) not null comment '订单ID',
  user_id bigint(20) not null comment '用户ID',
  title varchar(200) not null default '' comment '订单标题',
  total_amount bigint(20) not null default 0 comment '订单总金额，单位分',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  pay_time timestamp comment '支付时间',
  primary key (`order_id`)
);
create table t_order_0(
  order_id varchar(20) not null comment '订单ID',
  user_id bigint(20) not null comment '用户ID',
  title varchar(200) not null default '' comment '订单标题',
  total_amount bigint(20) not null default 0 comment '订单总金额，单位分',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  pay_time timestamp comment '支付时间',
  primary key (`order_id`)
);
create table t_order_1(
  order_id varchar(20) not null comment '订单ID',
  user_id bigint(20) not null comment '用户ID',
  title varchar(200) not null default '' comment '订单标题',
  total_amount bigint(20) not null default 0 comment '订单总金额，单位分',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  pay_time timestamp comment '支付时间',
  primary key (`order_id`)
);

create database ds_1;
use ds_1;
create table t_order(
  order_id varchar(20) not null comment '订单ID',
  user_id bigint(20) not null comment '用户ID',
  title varchar(200) not null default '' comment '订单标题',
  total_amount bigint(20) not null default 0 comment '订单总金额，单位分',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  pay_time timestamp comment '支付时间',
  primary key (`order_id`)
);
create table t_order_0(
  order_id varchar(20) not null comment '订单ID',
  user_id bigint(20) not null comment '用户ID',
  title varchar(200) not null default '' comment '订单标题',
  total_amount bigint(20) not null default 0 comment '订单总金额，单位分',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  pay_time timestamp comment '支付时间',
  primary key (`order_id`)
);
create table t_order_1(
  order_id varchar(20) not null comment '订单ID',
  user_id bigint(20) not null comment '用户ID',
  title varchar(200) not null default '' comment '订单标题',
  total_amount bigint(20) not null default 0 comment '订单总金额，单位分',
  create_time timestamp not null default current_timestamp comment '创建时间',
  update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  pay_time timestamp comment '支付时间',
  primary key (`order_id`)
);

use ds_0;
delete from t_order;
delete from t_order_0;
delete from t_order_1;

use ds_1;
delete from t_order;
delete from t_order_0;
delete from t_order_1;

