package com.warrior.schedule.task;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobExecute {
    String value() default "";
}