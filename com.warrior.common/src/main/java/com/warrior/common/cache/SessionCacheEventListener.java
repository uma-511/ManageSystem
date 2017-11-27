package com.warrior.common.cache;

import com.warrior.util.spring.SpringUtil;
import lombok.extern.log4j.Log4j;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import org.apache.commons.lang.StringUtils;
import org.joor.Reflect;

@Log4j
public class SessionCacheEventListener implements CacheEventListener {

    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        log.info("====Removed");
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
        log.info("====Expired");
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
            PushCache.removeClient(element.getKey().toString());
        }
    }
}
