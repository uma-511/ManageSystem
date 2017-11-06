package com.warrior.util.common;

import lombok.extern.log4j.Log4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import java.util.List;

@Log4j
public class WarriorSession {

    private static Cache cache;

    public WarriorSession(){
        CacheManager manager = CacheManager.create(getClass().getClassLoader().getResource("ehcache.xml"));
        WarriorSession.cache = manager.getCache("sessionCache");
    }

    public static void setItem(String key,Object value){
        Element element = new Element(key,value);
        cache.put(element);
    }

    public static <T> T getItem(String key){
        Element element = cache.get(key);
        return element == null ? null : (T)element.getObjectValue();
    }

    public static void printAll(){
        List<String> list = cache.getKeys();
        if(list != null && list.size() > 0){
            for (String key : list){
                log.info(key+"="+getItem(key));
            }
        }
    }
}