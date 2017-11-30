package com.warrior.common.spring;

import com.warrior.common.push.PushService;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

@Log4j
public class CustomApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            log.info("====== ContextClosed ======");
            PushService.stopServer();
        } else if (event instanceof ContextRefreshedEvent) {
            log.info("====== ContextRefreshed ======");
            PushService.startSevice();
        } else if (event instanceof ContextStartedEvent) {
            log.info("====== ContextStarted ======");
        } else if (event instanceof ContextStoppedEvent) {
            log.info("====== ContextStopped ======");
            PushService.stopServer();
        }
    }
}
