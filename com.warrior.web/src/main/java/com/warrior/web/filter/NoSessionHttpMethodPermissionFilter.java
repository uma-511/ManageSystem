package com.warrior.web.filter;

import com.warrior.base.entity.User;
import com.warrior.base.service.UserService;
import com.warrior.common.Contacts;
import com.warrior.common.cache.PushCache;
import com.warrior.common.web.WarriorSession;
import com.warrior.util.spring.SpringUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
public class NoSessionHttpMethodPermissionFilter extends HttpMethodPermissionFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse rsp = (HttpServletResponse) response;
        rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletRequest res = (HttpServletRequest) request;
        if (!StringUtils.equals(res.getMethod(), "OPTIONS")) {
            if(StringUtils.equals(res.getRequestURI(), "/admin/doLogin")){
                doLogin(res,response);
            } else if(StringUtils.equals(res.getRequestURI(),"/admin/user/vailPassword")){
                User user = WarriorSession.getItem(res.getParameter("token"));
                if (user == null){
                    SecurityUtils.getSubject().logout();
                    doLogin(res,response);
                    UserService userService = SpringUtil.getBean("userServiceImpl");
                    String token = userService.login(request.getParameter("userName"),request.getParameter("passWord"));
                    res.setAttribute("token",token);
                    PushCache.replaceToken(res.getParameter("token"),token);
                }
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    private void doLogin(HttpServletRequest request, ServletResponse response){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken();
            token.setUsername(request.getParameter("userName"));
            token.setPassword(request.getParameter("passWord").toCharArray());
            getSubject(request, response).login(token);
        } catch (UnknownAccountException e) {
            log.error("账号不存在！");
            request.setAttribute(Contacts.SHIRO_EXCEPTION, Contacts.CODE_NO_ACCOUNT);
        } catch (LockedAccountException e) {
            log.error("账号被锁定或被删除！");
            request.setAttribute(Contacts.SHIRO_EXCEPTION, Contacts.CODE_ACCOUNT_LOCK);
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误！");
            request.setAttribute(Contacts.SHIRO_EXCEPTION, Contacts.CODE_PASSWORD_ERROR);
        } catch (ExcessiveAttemptsException e) {
            log.error("密码错误5次账户锁定10分钟！");
            request.setAttribute(Contacts.SHIRO_EXCEPTION, Contacts.CODE_PASSWORD_ERROR_5);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            log.error("登录认证失败！");
            request.setAttribute(Contacts.SHIRO_EXCEPTION, Contacts.CODE_LOGIN_FAIL);
        }
    }
}
