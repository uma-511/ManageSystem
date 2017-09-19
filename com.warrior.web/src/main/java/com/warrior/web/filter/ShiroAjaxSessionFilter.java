package com.warrior.web.filter;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShiroAjaxSessionFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = WebUtils.getHttpRequest(request);
        if (com.warrior.util.web.WebUtils.isAjaxRequest(req)){
            HttpServletResponse res = WebUtils.toHttp(response);
            res.setHeader("oauthstatus","401");
            return false;
        }
        return super.onAccessDenied(request, response);
    }
}