package com.warrior.schedule.task;

import com.warrior.schedule.service.ScheduleService;
import org.joor.Reflect;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TaskRun implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Reflect reflect = (Reflect) context.getJobDetail().getJobDataMap().get(ScheduleService.QUARTZ_CLASS);
        String methodName = context.getJobDetail().getJobDataMap().getString(ScheduleService.QUARTZ_METHOD);
        reflect.call(methodName);

    }
}
