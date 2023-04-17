package com.demo.components.desensitive;

/**
 * @author dewu.de
 * @date 2023-04-17 11:48 上午
 */
public class DesensitiveConfigParseException extends RuntimeException {

    public DesensitiveConfigParseException(String message) {
        super(message);
    }

    public DesensitiveConfigParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
