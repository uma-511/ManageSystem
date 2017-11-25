package com.warrior.util.exception;

public class UtilException extends BaseException {

    public UtilException() {
    }

    public UtilException(int code, String message) {
        super(code, message);
    }

    public UtilException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
