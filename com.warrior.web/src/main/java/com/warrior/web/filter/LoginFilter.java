package com.warrior.web.filter;

import com.warrior.common.entity.User;
import com.warrior.common.JSONMsg;
import com.warrior.common.web.WebUtils;
import com.warrior.common.web.WarriorSession;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class LoginFilter extends AccessControlFilter{

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String url = request.getRequestURI();
        if (WebUtils.checkUrl(url)){
            return true;
        }

        User user = WarriorSession.getItem(request.getParameter("token"));
        if (null == user && !StringUtils.contains(request.getRequestURI(),"/doLogin")){
            JSONMsg msg = new JSONMsg();
            msg.setSuccess(JSONMsg.FLAG_FAIL);
            msg.setMsg("未登录！");
            msg.setLogin(false);
            WebUtils.responseWrite(response,msg);
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
