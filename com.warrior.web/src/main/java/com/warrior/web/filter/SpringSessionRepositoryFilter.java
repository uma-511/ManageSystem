package com.warrior.web.filter;

import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;

//@WebFilter(filterName = "springSessionRepositoryFilter",urlPatterns = {"/*"},dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.ERROR})
public class SpringSessionRepositoryFilter extends DelegatingFilterProxy {
}