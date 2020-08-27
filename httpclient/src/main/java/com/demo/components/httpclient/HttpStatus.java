package com.demo.components.httpclient;

/**
 * @author wude
 * @date 2020/8/26 11:16
 */
public enum HttpStatus {

    OK(200, "OK");

    private final int value;
    private final String reasonPhrase;

    private HttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}