package com.warrior.common.web;

import com.google.common.collect.Maps;
import com.warrior.common.entity.User;
import lombok.extern.log4j.Log4j;
import net.sf.ehcache.*;
import net.sf.ehcache.event.CacheEventListener;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.util.Collection;
import java.util.Map;

@Log4j
public class WarriorSession implements CacheEventListener {

    private static Map<Long, String> online_user = Maps.newHashMap();
    private static Cache cache;

    public WarriorSession() {
        CacheManager manager = CacheManager.create(getClass().getClassLoader().getResource("ehcache.xml"));
        WarriorSession.cache = manager.getCache("sessionCache");
    }

    public static void setItem(String key, Object value) {
        Element element = new Element(key, value);
        cache.put(element);
    }

    public static <T> T getItem(String key) {
        Element element = cache.get(key);
        return element == null ? null : (T) element.getObjectValue();
    }

    public static void removeUser(long uid) {
        cache.remove(online_user.get(uid));
    }

    public static void removeSession(User user) {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        for (Session session : sessions) {
            if (StringUtils.equals(user.getUserName(), String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                sessionManager.getSessionDAO().delete(session);
                WarriorSession.removeUser(user.getUid());
            }
        }
    }

    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        if (element.getObjectValue() instanceof User) {
            User user = (User) element.getObjectValue();
            WarriorSession.removeUser(user.getUid());
            WarriorSession.removeSession(user);
        }
    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {

    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {

    }

    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {
        if (element.getObjectValue() instanceof User) {
            User user = (User) element.getObjectValue();
            WarriorSession.removeUser(user.getUid());
            WarriorSession.removeSession(user);
        }
    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {
        if (element.getObjectValue() instanceof User) {
            User user = (User) element.getObjectValue();
            WarriorSession.removeUser(user.getUid());
            WarriorSession.removeSession(user);
        }
    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {
        WarriorSession.online_user.clear();
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        for (Session session : sessions) {
            sessionManager.getSessionDAO().delete(session);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

}