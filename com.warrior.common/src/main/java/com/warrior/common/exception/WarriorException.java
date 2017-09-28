package com.warrior.common.exception;

/***
 * 自定义异常处理
 *
 */
public class WarriorException extends RuntimeException {

    public WarriorException() {
        super();
    }

    public WarriorException(String message) {
        super(message);
    }

    public WarriorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarriorException(Throwable cause) {
        super(cause);
    }

    protected WarriorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}