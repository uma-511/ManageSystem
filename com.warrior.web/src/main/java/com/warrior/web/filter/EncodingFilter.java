package com.warrior.web.filter;

import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "encodingFilter",urlPatterns = "/*",initParams = {@WebInitParam(name = "encoding",value = "UTF-8"),@WebInitParam(name="forceEncoding",value = "true")})
public class EncodingFilter extends CharacterEncodingFilter {
}
