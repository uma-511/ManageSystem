package com.warrior.common.cache;

import com.warrior.common.web.WarriorSession;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import java.util.Properties;

public class SessionCacheEventListenerFactory extends CacheEventListenerFactory {

    @Override
    public CacheEventListener createCacheEventListener(Properties properties) {
        return new WarriorSession();
    }
}
