package com.warrior.common.exception;

import lombok.Getter;
import lombok.Setter;

/***
 * 自定义异常处理
 *
 */
public class WarriorException extends RuntimeException {

    @Setter @Getter
    private int code;

    public WarriorException() {
        super();
    }

    public WarriorException(int code,String message){
        super(message);
        this.code = code;
    }

    public WarriorException(int code,String message,Throwable cause){
        super(message,cause);
        this.code = code;
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