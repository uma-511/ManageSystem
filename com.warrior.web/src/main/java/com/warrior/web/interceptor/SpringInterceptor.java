package com.warrior.web.interceptor;

import com.google.common.collect.Maps;
import com.warrior.common.entity.User;
import com.warrior.common.Contacts;
import com.warrior.common.exception.WarriorException;
import com.warrior.common.web.WarriorSession;
import com.warrior.common.web.WebUtils;
import com.warrior.util.common.TokenUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Log4j
@Component
public class SpringInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!StringUtils.equals(request.getMethod(),"OPTIONS")  && !WebUtils.checkUrl(request.getRequestURI())){
            User user = WarriorSession.getItem(request.getParameter("token"));
            if(user == null ){
                throw new WarriorException("用户未登录，请登录！");
            }
            long time = Long.parseLong(request.getParameter("time"));
            if((System.currentTimeMillis() - time) > 1000 * 60){
                throw new WarriorException("非法请求，请求时间过长！");
            }
            Enumeration<String> params = request.getParameterNames();
            Map<String,String> data = Maps.newHashMap();
            String sign = request.getParameter("sign");
            while (params.hasMoreElements()){
                String key = params.nextElement();
                if(!StringUtils.equals(key,"sign")){
                    data.put(key,request.getParameter(key));
                }
            }
            if(!StringUtils.equals(sign,TokenUtil.getSign(data, Contacts.MD5_SALT,true))){
                throw new WarriorException("非法请求，签名错误！");
            }
        }
        return super.preHandle(request, response, handler);
    }
}