package com.demo.components.shardingjdbc.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.visitor.SQLTableAliasCollectVisitor;
import com.demo.components.shardingjdbc.property.DruidProperty;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author eleme
 * @datetime 2021-08-20 下午4:29
 */
public class DataSourceFactory {

    public static DataSource dataSource(DruidProperty property) {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(property.getUrl());
        druidDataSource.setUsername(property.getUsername());
        druidDataSource.setPassword(property.getPassword());
        druidDataSource.setInitialSize(property.getInitialSize());
        druidDataSource.setMinIdle(property.getMinIdle());
        druidDataSource.setMaxActive(property.getMaxActive());
        druidDataSource.setMaxWait(property.getMaxWait());
        druidDataSource.setDriverClassName(property.getDriverClassName());
        return druidDataSource;
    }

}
