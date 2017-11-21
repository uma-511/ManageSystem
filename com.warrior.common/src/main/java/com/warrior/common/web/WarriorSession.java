package com.warrior.common.web;

import com.warrior.util.spring.SpringUtil;
import lombok.extern.log4j.Log4j;
import net.sf.ehcache.*;
import net.sf.ehcache.event.CacheEventListener;
import org.apache.commons.lang.StringUtils;
import org.joor.Reflect;

@Log4j
public class WarriorSession implements CacheEventListener {

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

    public static void removeItem(String key) {
        cache.remove(key);
    }

    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        cleanToken(element);
    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {

    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {

    }

    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {
        cleanToken(element);
    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {
        log.info("====Evicted");
    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {
    }

    @Override
    public void dispose() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

    private void cleanToken(Element element){
        if (element.getObjectValue() != null && StringUtils.equals(element.getObjectValue().getClass().getSimpleName(),"User")) {
            Object user = element.getObjectValue();
            Reflect.on(user).call("setToken","");
            Object userService = SpringUtil.getBean("userServiceImpl");
            Reflect.on(userService).call("updateById",user);
        }
    }
}