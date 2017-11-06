package com.warrior.common.aspect;

import com.warrior.common.JSONMsg;
import com.warrior.common.entity.SysLog;
import com.warrior.common.service.SysLogService;
import com.warrior.common.web.WebUtils;
import com.warrior.util.common.JSONUtils;
import com.warrior.util.common.WarriorSession;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

        saveLog(point,result,endTime - beginTime);
        return result;
    }

    private void saveLog(ProceedingJoinPoint point,Object result,long time){
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
        if(args != null && args.length >0 && args[0] != null){
            String params = JSONUtils.toJson(args[0]);
            log.setParams(params);
        }
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)){
            JSONMsg msg = (JSONMsg)result;
            token = msg.getData().toString();
        }
        Long uid = WarriorSession.getItem(token);

        if (uid != null){
            log.setUserId(uid);
        }else{
            log.setUserId(-1L);
        }
        log.setIp(WebUtils.getIpAddr());
        log.setTime(time);
        log.setCreateTime(new Date());
        sysLogService.insert(log);
    }
}