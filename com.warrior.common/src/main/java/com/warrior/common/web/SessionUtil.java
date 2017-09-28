package com.warrior.common.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/***
 * session相关操作
 */
public class SessionUtil {


    private static Session getSession(){
        return SecurityUtils.getSubject().getSession(false);
    }

    public static void setAttr(String key,Object value){
        Session session = getSession();
        if (session != null){
            getSession().setAttribute(key,value);
        }
    }

    public static <T> T getValue(String key){
        Session session = getSession();
        return session == null ? null : (T)getSession().getAttribute(key);
    }

}