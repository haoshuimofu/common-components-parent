package com.demo.components.httpclient.exception;

/**
 * Http异常
 *
 * @author wude
 * @date 2020/10/13 14:15
 */
public class HttpException extends RuntimeException {

    private String message;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}