package com.warrior.common.web;

import lombok.extern.log4j.Log4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Log4j
public class WarriorSession {

    private static Cache cache;

    public WarriorSession(CacheManager manager) {
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
}