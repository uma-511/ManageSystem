package com.warrior.web.shiro;

import lombok.extern.log4j.Log4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

@Log4j
public class ShiroSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {

    }

    @Override
    public void onStop(Session session) {

    }

    @Override
    public void onExpiration(Session session) {
        log.info("session 过期");
    }
}