package com.warrior.schedule.listener;

import com.warrior.schedule.service.impl.ScheduleServiceImpl;
import lombok.extern.log4j.Log4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Log4j
public class ScheduleJobListener implements JobListener{

    @Override
    public String getName() {
        return "ScheduleJob";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        Object desc = jobExecutionContext.getJobDetail().getJobDataMap().get(ScheduleServiceImpl.QUARTZ_REMARK);
        log.info(String.format("任务 [%s] 开始执行！",desc));
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        Object desc = jobExecutionContext.getJobDetail().getJobDataMap().get(ScheduleServiceImpl.QUARTZ_REMARK);
        log.info(String.format("任务 [%s] 执行被否决......",desc));
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        Object desc = jobExecutionContext.getJobDetail().getJobDataMap().get(ScheduleServiceImpl.QUARTZ_REMARK);
        log.info(String.format("任务 [%s] 执行完毕！",desc));
    }
}
