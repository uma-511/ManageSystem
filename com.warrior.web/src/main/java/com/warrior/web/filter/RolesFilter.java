package com.warrior.web.filter;

import com.warrior.common.JSONMsg;
import com.warrior.common.web.WebUtils;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
public class RolesFilter extends RolesAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if (WebUtils.isAjaxRequest(request)){
            JSONMsg msg = new JSONMsg();
            msg.setSuccess(JSONMsg.FLAG_FAIL);
            msg.setMsg("当前角色无访问权限！");
            WebUtils.responseWrite(response,msg);
        }else{
            response.sendRedirect("/unauth");
        }
        return false;
    }
}
