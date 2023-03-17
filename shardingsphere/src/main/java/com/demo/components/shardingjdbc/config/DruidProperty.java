package com.demo.components.shardingjdbc.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author eleme
 * @datetime 2021-08-20 下午4:32
 */
@Getter
@Setter
public class DruidProperty {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;

}
