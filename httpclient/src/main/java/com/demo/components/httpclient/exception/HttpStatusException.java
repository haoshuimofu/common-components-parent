package com.demo.components.httpclient.exception;

/**
 * Http请求状态异常
 *
 * @author wude
 * @date 2020/8/26 13:32
 */
public class HttpStatusException extends RuntimeException {

    private int statusCode;
    private String reasonPhrase;

    public HttpStatusException(String message) {
        super(message);
    }

    public HttpStatusException(int statusCode, String reasonPhrase) {
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