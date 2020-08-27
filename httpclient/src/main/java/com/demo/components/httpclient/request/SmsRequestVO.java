package com.demo.components.httpclient.request;

/**
 * @author wude
 * @date 2020/8/27 12:43
 */
public class SmsRequestVO {

    private String mobile;
    private String content;

    @Override
    public String toString() {
        return "mobile = " + mobile + "; content = " + content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}