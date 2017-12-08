package com.warrior.schedule.task;

import lombok.extern.log4j.Log4j;
import org.quartz.JobExecutionContext;

@Log4j
@Job
public class TestJob{

    @JobExecute(value = "测试一号")
    public void execute(JobExecutionContext context) {
      log.info("=======测试一号=======");
    }

    @JobExecute("测试二号")
    public void test2(){
        log.info("------测试二号-------");
    }
}