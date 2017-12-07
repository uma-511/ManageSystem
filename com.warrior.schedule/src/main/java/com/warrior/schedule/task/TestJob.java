package com.warrior.schedule.task;

import lombok.extern.log4j.Log4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Log4j
@JobTarget(value = "测试")
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
      log.info("==============");
    }
}
