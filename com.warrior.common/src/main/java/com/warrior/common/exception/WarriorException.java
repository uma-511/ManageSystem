package com.warrior.common.exception;

import com.warrior.util.exception.BaseException;

/***
 * 自定义异常处理
 *
 */
public class WarriorException extends BaseException {

    public WarriorException() {
    }

    public WarriorException(int code, String message) {
        super(code, message);
    }

    public WarriorException(int code, String message, Throwable cause) {
        super(code, message, cause);
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

    public WarriorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}