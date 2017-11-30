package com.warrior.schedule.task;

import com.warrior.schedule.service.impl.ScheduleServiceImpl;
import com.warrior.util.common.GroovyUtil;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScriptTaskRun implements Job {

    @Override
    public void execute(JobExecutionContext cxt) throws JobExecutionException {
        String scriptName = cxt.getJobDetail().getJobDataMap().getString(ScheduleServiceImpl.QUARTZ_SCRIPT);
        if (StringUtils.isNotBlank(scriptName)) {
            GroovyUtil groovyUtil = GroovyUtil.getInstance();
            if (StringUtils.startsWith(scriptName,"classpath:")) {
                scriptName = scriptName.replace("classpath:","");
                groovyUtil.loadScript(this.getClass().getClassLoader().getResource(scriptName).getPath());
            } else {
                groovyUtil.loadScript(scriptName);
            }
            groovyUtil.runScript();
        }
    }
}