package com.warrior.common.spring;

import com.warrior.common.Contacts;
import com.warrior.util.exception.BaseException;
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
        ModelAndView model = new ModelAndView();
        model.setView(new JSONView());
        if (e instanceof UnauthorizedException){
            model.addObject("code", Contacts.CODE_NO_PERMISSION);
            model.addObject("msg",Contacts.ERROR_MSG.get(Contacts.CODE_NO_PERMISSION));
        } else if(e instanceof BaseException){
            BaseException waException = (BaseException)e;
            model.addObject("code", waException.getCode() == 0 ? Contacts.CODE_FAIL : waException.getCode());
            model.addObject("msg", waException.getMessage());
        } else {
            model.addObject("code", Contacts.CODE_FAIL);
            model.addObject("msg",StringUtils.isBlank(e.getMessage()) ? DEFAULT_ERROR_MESSAGE : e.getMessage());
        }
        return model;
    }
}