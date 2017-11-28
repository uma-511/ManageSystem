package com.warrior.schedule.spring;

import com.warrior.schedule.service.impl.ScheduleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ScheduleConfig {

    @Bean("schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(){
        return new SchedulerFactoryBean();
    }

    @Bean
    public ScheduleServiceImpl scheduleService(){
        return new ScheduleServiceImpl(schedulerFactoryBean().getScheduler());
    }
}