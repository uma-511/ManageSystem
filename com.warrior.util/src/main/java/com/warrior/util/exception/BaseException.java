package com.warrior.util.exception;

import lombok.Getter;
import lombok.Setter;

public class BaseException extends RuntimeException {

    @Setter
    @Getter
    private int code;

    public BaseException() {
        super();
    }

    public BaseException(int code,String message){
        super(message);
        this.code = code;
    }

    public BaseException(int code,String message,Throwable cause){
        super(message,cause);
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
