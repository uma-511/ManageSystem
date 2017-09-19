package com.warrior.web.filter;

import lombok.extern.log4j.Log4j;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "shiroFilter",urlPatterns = {"/*"},initParams = {@WebInitParam(name = "targetFilterLifecycle",value = "true"),@WebInitParam(name="staticSecurityManagerEnabled",value = "true")})
@Log4j
public class ShiroFilter extends DelegatingFilterProxy {
}