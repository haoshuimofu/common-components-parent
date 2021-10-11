drop table IF EXISTS `geom_test`;
create TABLE `geom_test`(
    `id` int(11) NOT NULL,
    `name` varchar(255) NOT NULL,
    `geometry_1` geometry NULL,
    `point_1` point NULL,
    `linestring_1` linestring NULL,
    `polygon_1` polygon NULL,
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    SPATIAL KEY `s_idx_geometry` (`geometry_1`)
    SPATIAL KEY `s_idx_pt` (`point_1`),
    SPATIAL KEY `s_idx_line` (`linestring_1`),
    SPATIAL KEY `s_idx_polygon` (`polygon_1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='mysql空间索引测试表';

-- ----------------------------
-- Records of geom_test
-- ----------------------------
insert into `geom_test` VALUES (1, 'P1', ST_GeomFromText('POINT(121.474 31.2329)'), ST_GeomFromText('POINT(121.474 31.2329)'), ST_GeomFromText('LINESTRING(1 3, 12 5, 12 7)'), ST_GeomFromText('POLYGON((121.474 31.2345, 121.472 31.2333, 121.471 31.2315, 121.472 31.2302, 121.473 31.2304, 121.476 31.232, 121.474 31.2345))'), now(), now());
insert into `geom_test` VALUES (2, 'L1', ST_GeomFromText('LINESTRING(121.342 31.5424, 121.346 31.2468, 121.453 31.4569)'), ST_GeomFromText('POINT(-3 -5)'), ST_GeomFromText('LINESTRING(1 3, 12 5, 12 7)'), NULL, now(), now());
insert into `geom_test` VALUES (3, 'P1', ST_GeomFromText('POLYGON((121.474 31.2345, 121.472 31.2333, 121.471 31.2315, 121.472 31.2302, 121.473 31.2304, 121.476 31.232, 121.474 31.2345))'), ST_GeomFromText('POINT(1 1)'), ST_GeomFromText('LINESTRING(121.342 31.5424, 121.346 31.2468, 121.453 31.4569)'), NULL, now(), now());
insert into `geom_test` VALUES (4, 'MP1', ST_GeomFromText('MULTIPOINT(0 0, 20 20, 60 60)'), ST_GeomFromText('POINT(6 7)'), NULL, NULL, now(), now());
insert into `geom_test` VALUES (5, 'ML1', ST_GeomFromText('MULTILINESTRING((10 10, 20 20), (15 15, 30 15))'), ST_GeomFromText('POINT(4 6)'), NULL, NULL, now(), now());
insert into `geom_test` VALUES (6, 'MPG1', ST_GeomFromText('MULTIPOLYGON(((0 0, 10 0, 10 10, 0 10, 0 0)), ((5 5, 7 5, 7 7, 5 7, 5 5)))'), ST_GeomFromText('POINT(2 5)'), NULL, NULL, now(), now());
insert into `geom_test` VALUES (7, 'G1', ST_GeomFromText('GEOMETRYCOLLECTION(POINT(10 10), POINT(30 30), LINESTRING(15 15, 20 20))'), ST_GeomFromText('POINT(1 3)'), NULL, NULL, now(), now());
insert into `geom_test` VALUES (8, 'P1', NULL, ST_GeomFromText('POINT(-3 -5)'), NULL, NULL, now(), now());
insert into `geom_test` VALUES (9, 'P1', NULL, NULL, ST_GeomFromText('LINESTRING(1 3, 12 5, 12 7)'), NULL, now(), now());
insert into `geom_test` VALUES (10, 'P1', NULL, NULL, NULL, ST_GeomFromText('POLYGON((121.474 31.2345, 121.472 31.2333, 121.471 31.2315, 121.472 31.2302, 121.473 31.2304, 121.476 31.232, 121.474 31.2345))'), now(), now());
insert into `geom_test` VALUES (17, 'P1', ST_GeomFromText('GEOMETRYCOLLECTION(POINT(10 10), POINT(30 30), LINESTRING(15 15, 20 20))'), NULL, NULL, NULL, now(), now());
insert into `geom_test` VALUES (18, 'P1', NULL, NULL, NULL, NULL, now(), now());

