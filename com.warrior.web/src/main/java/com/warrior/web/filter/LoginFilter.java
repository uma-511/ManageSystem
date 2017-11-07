package com.warrior.web.filter;

import com.warrior.common.JSONMsg;
import com.warrior.common.web.WebUtils;
import com.warrior.util.common.WarriorSession;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter extends AccessControlFilter{

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String url = request.getRequestURI();
        if (WebUtils.checkUrl(url)){
            return true;
        }

        Long uid = WarriorSession.getItem(request.getParameter("token"));
        if (null == uid && !StringUtils.contains(request.getRequestURI(),"/doLogin")){
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
