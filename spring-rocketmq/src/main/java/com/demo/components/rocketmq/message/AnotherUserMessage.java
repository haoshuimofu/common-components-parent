package com.demo.components.rocketmq.message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wude
 * @date 2021/4/16 10:48
 */
public class AnotherUserMessage {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String flag = "another";
    private String time = sdf.format(new Date());
    private String name;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}