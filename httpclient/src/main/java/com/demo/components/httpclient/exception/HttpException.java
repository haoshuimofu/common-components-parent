package com.demo.components.httpclient.exception;

/**
 * @author wude
 * @date 2020/8/26 13:32
 */
public class HttpException extends RuntimeException {

    private int statusCode;
    private String reasonPhrase;

    public HttpException(String message) {
        super(message);
    }

    public HttpException(int statusCode, String reasonPhrase) {
        super("HTTP Status Code = " + statusCode + ", ReasonPhrase = " + reasonPhrase);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}