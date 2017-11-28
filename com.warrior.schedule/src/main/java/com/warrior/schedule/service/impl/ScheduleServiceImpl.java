package com.warrior.schedule.service.impl;

import com.warrior.schedule.entity.ScheduleJob;
import com.warrior.schedule.listener.ScheduleJobListener;
import com.warrior.schedule.service.ScheduleService;
import com.warrior.schedule.task.ScriptTaskRun;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {

    @Setter
    @Getter
    private Scheduler scheduler;

    public ScheduleServiceImpl() {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            this.scheduler = sf.getScheduler();
            this.addJobListener(new ScheduleJobListener());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public ScheduleServiceImpl(Scheduler scheduler) {
        try {
            this.scheduler = scheduler;
            this.addJobListener(new ScheduleJobListener());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addJobList(List<ScheduleJob> jobList) throws SchedulerException {
        if (jobList != null && jobList.size() > 0) {
            for (ScheduleJob job : jobList) {
                this.addJob(job);
            }
        }
    }

    /**
     * 添加任务监听器
     *
     * @param listener
     * @throws SchedulerException
     */
    public void addJobListener(JobListener listener) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(listener);
    }

    /**
     * 修改定时器
     *
     * @param job
     * @throws SchedulerException
     */
    public void modifyJob(ScheduleJob job) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(job.getJobName(),
                job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger != null) {
            if (trigger.getJobDataMap().containsKey(QUARTZ_SCRIPT)){
                trigger.getJobDataMap().put(QUARTZ_SCRIPT, job.getScriptName());
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put(QUARTZ_REMARK, job.getRemark());
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 添加定时器
     *
     * @param job
     * @throws SchedulerException
     */
    public void addJob(ScheduleJob job)
            throws SchedulerException {
        String jobGroup = StringUtils.isNotEmpty(job.getJobGroup()) ? job.getJobGroup() : QUARTZ_DEFAULT_GROUP;
        JobDetail jobDetail;
        if (job.getTargetClass() != null) {
            jobDetail = JobBuilder
                    .newJob(job.getTargetClass().getClass())
                    .withIdentity(job.getJobName(), jobGroup).build();
        }else{
            jobDetail = JobBuilder
                    .newJob(ScriptTaskRun.class)
                    .withIdentity(job.getJobName(), jobGroup).build();
            jobDetail.getJobDataMap().put(QUARTZ_SCRIPT, job.getScriptName());
        }

        jobDetail.getJobDataMap().put(QUARTZ_REMARK, job.getRemark());
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(job.getCronExpression());
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停定时器
     * @throws SchedulerException
     */
    public void pauseJob(String jobName) throws SchedulerException{
        pauseJob(jobName,QUARTZ_DEFAULT_GROUP);
    }

    /**
     * 暂停定时器
     * @throws SchedulerException
     */
    public void pauseJob(String jobName,String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
        scheduler.pauseJob(jobKey);
    }

    public void resumeJob(String jobName) throws SchedulerException {
        resumeJob(jobName,QUARTZ_DEFAULT_GROUP);
    }
    /**
     * 恢复定时器
     * @throws SchedulerException
     */
    public void resumeJob(String jobName,String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
        scheduler.resumeJob(jobKey);
    }

    public void deleteJob(String jobName) throws SchedulerException {
        deleteJob(jobName,QUARTZ_DEFAULT_GROUP);
    }

    /**
     * 删除定时器
     * @throws SchedulerException
     */
    public void deleteJob(String jobName,String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 启动
     *
     * @throws SchedulerException
     */
    public void start() throws SchedulerException {
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
    }

    /**
     * 关闭
     *
     * @throws SchedulerException
     */
    public void stop() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown(true);
        }
    }

}