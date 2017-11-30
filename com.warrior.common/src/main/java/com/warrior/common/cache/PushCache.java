package com.warrior.common.cache;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.log4j.Log4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import java.util.List;

@Log4j
public class PushCache {

    private static Cache cache;
    private static Cache uuidCache;

    public PushCache(CacheManager manager){
        cache = manager.getCache("clientCache");
        uuidCache = manager.getCache("uuidCache");
    }

    public static SocketIOClient getClient(String token){
        Element element = cache.get(getUuid(token));
        return element != null ? (SocketIOClient) element.getObjectValue() : null;
    }

    public static void addClient(String uuid,String token,SocketIOClient client){
        cache.put(new Element(uuid,client));
        uuidCache.put(new Element(token,uuid));
    }

    public static void removeClient(String token){
        SocketIOClient client = getClient(token);
        if(client != null){
            client.disconnect();
        }
        cache.remove(getUuid(token));
        uuidCache.remove(token);
    }

    public static void removeAll(){
        cache.removeAll();
        uuidCache.removeAll();
    }

    public static void replaceToken(String oldToken,String newToken){
        uuidCache.remove(oldToken);
        uuidCache.put(new Element(newToken,getUuid(oldToken)));
    }

    public static String getUuid(String token){
        Element element = uuidCache.get(token);
        return element == null ? "" : (String)element.getObjectValue();
    }

    public static List<String> getAllClient(){
        List<String> list = cache.getKeys();
        return list;
    }
}
