package com.warrior.common.spring;

import com.warrior.common.JSONMsg;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionHandler implements HandlerExceptionResolver{

    protected static final String DEFAULT_ERROR_MESSAGE = "系统繁忙，请稍后再试！";


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView();
        model.setView(new JSONView());
        model.addObject("success", JSONMsg.FLAG_FAIL);
        if (e instanceof UnauthorizedException){
            model.addObject("msg","无访问权限！");
        }else{
            model.addObject("msg",StringUtils.isBlank(e.getMessage()) ? DEFAULT_ERROR_MESSAGE : e.getMessage());
        }
        return model;
    }
}