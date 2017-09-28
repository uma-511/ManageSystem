package com.warrior.common.aspect;

import com.warrior.common.Contacts;
import com.warrior.common.entity.SysLog;
import com.warrior.common.model.UserModel;
import com.warrior.common.service.SysLogService;
import com.warrior.common.web.SessionUtil;
import com.warrior.common.web.WebUtils;
import com.warrior.util.common.JSONUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/***
 * 系统日志切面
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.warrior.common.annotation.SysLog)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long beginTime = System.currentTimeMillis();

        Object result = point.proceed();

        long endTime = System.currentTimeMillis();

        saveLog(point,endTime - beginTime);
        return result;
    }

    private void saveLog(ProceedingJoinPoint point,long time){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        SysLog log = new SysLog();

        com.warrior.common.annotation.SysLog sysLog = method.getAnnotation(com.warrior.common.annotation.SysLog.class);

        if (sysLog != null){
            log.setOperation(sysLog.value());
        }

        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(String.format("%s.%s()",className,methodName));
        Object [] args = point.getArgs();
        if(args[0] != null){
            String params = JSONUtils.toJson(args[0]);
            log.setParams(params);
        }

        UserModel entity = SessionUtil.getValue(Contacts.SESSION_USER);
        if (entity != null){
            log.setUserId(entity.getUid());
            log.setUserName(entity.getUserName());
        }else{
            log.setUserId(-1L);
            log.setUserName("");
        }
        log.setIp(WebUtils.getIpAddr());
        log.setTime(time);
        log.setCreateTime(new Date());
        sysLogService.insert(log);
    }
}