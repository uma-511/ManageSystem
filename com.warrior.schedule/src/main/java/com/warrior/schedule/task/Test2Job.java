package com.warrior.schedule.task;

import lombok.extern.log4j.Log4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Log4j
@JobTarget("Test2")
public class Test2Job implements Job{

    @Override
    public void execute(JobExecutionContext context) {
        log.info("--------Test2--------");
    }
}
