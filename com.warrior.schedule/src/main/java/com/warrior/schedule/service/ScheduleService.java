package com.warrior.schedule.service;

import com.warrior.schedule.entity.ScheduleJob;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

import java.util.List;

public interface ScheduleService {

    String QUARTZ_REMARK = "remark";
    String QUARTZ_DEFAULT_GROUP = "defalut_group";
    String QUARTZ_SCRIPT = "script";
    String QUARTZ_CLASS = "quartz_class";
    String QUARTZ_METHOD = "quartz_method";

    void addJobList(List<ScheduleJob> jobList) throws SchedulerException;

    void addJobListener(JobListener listener) throws SchedulerException;

    void modifyJob(ScheduleJob job) throws SchedulerException;

    void addJob(ScheduleJob job) throws SchedulerException;

    void pauseJob(String jobName) throws SchedulerException;

    void pauseJob(String jobName,String jobGroup) throws SchedulerException;

    void resumeJob(String jobName) throws SchedulerException;

    void resumeJob(String jobName,String jobGroup) throws SchedulerException;

    void deleteJob(String jobName) throws SchedulerException;

    void deleteJob(String jobName,String jobGroup) throws SchedulerException;

    void start() throws SchedulerException;

    void stop() throws SchedulerException;

}