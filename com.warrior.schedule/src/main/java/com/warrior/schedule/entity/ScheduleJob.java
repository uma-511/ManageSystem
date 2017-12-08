package com.warrior.schedule.entity;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.joor.Reflect;
import org.quartz.Job;

import java.io.Serializable;
import java.util.Map;

public class ScheduleJob implements Serializable{
    public static final int TASK_STATUS_NORMAL = 0;
    public static final int TASK_STATUS_PAUSE = 1;


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

    @Setter @Getter
    private int status;

    @Getter
    private Map<String,Object> params = Maps.newHashMap();

    public void setTargetClass(String className) {
        if(StringUtils.isNotEmpty(className)){
            this.targetClass = Reflect.on(className).create().get();
        }
    }

    public ScheduleJob() {
    }

    public ScheduleJob addParam(String key,Object value){
        params.put(key,value);
        return this;
    }
    public ScheduleJob(String jobName, String jobGroup, String cronExpression, String remark, String scriptName, String targetClass,int status) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cronExpression = cronExpression;
        this.remark = remark;
        this.scriptName = scriptName;
        this.status = status;
        setTargetClass(targetClass);
    }
}
