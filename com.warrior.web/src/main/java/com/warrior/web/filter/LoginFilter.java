package com.warrior.web.filter;

import com.warrior.common.Contacts;
import com.warrior.common.JSONMsg;
import com.warrior.common.web.SessionUtil;
import com.warrior.common.web.WebUtils;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String url = request.getRequestURI();
        if (StringUtils.equals("/",url) || StringUtils.contains(url,"/doLogin")
                || StringUtils.contains(url,"/doLogOut")
                || StringUtils.contains(url,"/dist")){
            return true;
        }

        Object entity = SessionUtil.getValue(Contacts.SESSION_USER);
        if (null == entity && !StringUtils.contains(request.getRequestURI(),"/doLogin")){
            if (WebUtils.isAjaxRequest(request)){
                JSONMsg msg = new JSONMsg();
                msg.setSuccess(JSONMsg.FLAG_FAIL);
                msg.setMsg("未登录！");
                msg.setLogin(false);
                WebUtils.responseWrite(response,msg);
                return false;
            }else{
                response.sendRedirect("/");
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
