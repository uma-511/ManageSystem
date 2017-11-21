package com.warrior.common.aspect;

import com.warrior.common.Contacts;
import com.warrior.common.JSONMsg;
import com.warrior.common.entity.SysLog;
import com.warrior.common.entity.User;
import com.warrior.common.service.SysLogService;
import com.warrior.common.web.WebUtils;
import com.warrior.util.common.JSONUtils;
import com.warrior.common.web.WarriorSession;
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
        JSONMsg msg = (JSONMsg)result;

        com.warrior.common.annotation.SysLog sysLog = method.getAnnotation(com.warrior.common.annotation.SysLog.class);

        if (sysLog != null){
            log.setOperation(msg.getCode() == Contacts.CODE_SUCCESS ? sysLog.value() : sysLog.value() + "[操作失败]");
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
        User user = null;
        if (StringUtils.isEmpty(token) && (msg.getCode() == Contacts.CODE_SUCCESS)){
            token = msg.getData().toString();
            user = WarriorSession.getItem(token);
        }
        if (user != null){
            log.setUserId(user.getUid());
            log.setUserName(user.getUserName());
        }else{
            log.setUserId(-1L);
        }
        log.setIp(WebUtils.getIpAddr());
        log.setTime(time);
        log.setCreateTime(new Date());
        sysLogService.insert(log);
    }
}