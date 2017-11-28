package com.warrior.schedule.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.joor.Reflect;
import org.quartz.Job;

import java.io.Serializable;

public class ScheduleJob implements Serializable{

    @Setter @Getter
    private String jobName;

    @Setter @Getter
    private String jobGroup;

    @Setter @Getter
    private String cronExpression;

    @Setter @Getter
    private String remark;

    @Setter @Getter
    private String scriptName;

    @Getter
    private Job targetClass;

    public void setTargetClass(String className) {
        if(StringUtils.isNotEmpty(className)){
            this.targetClass = Reflect.on(className).create().get();
        }
    }

    public ScheduleJob() {
    }

    public ScheduleJob(String jobName, String jobGroup, String cronExpression, String remark, String scriptName, String targetClass) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cronExpression = cronExpression;
        this.remark = remark;
        this.scriptName = scriptName;
        setTargetClass(targetClass);
    }
}
