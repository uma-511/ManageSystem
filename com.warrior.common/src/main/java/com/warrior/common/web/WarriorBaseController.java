package com.warrior.common.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.common.JSONMsg;
import com.warrior.util.common.JSONUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WarriorBaseController {

    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public String authenticationException(HttpServletRequest request, HttpServletResponse response){
        if (WebUtils.isAjaxRequest(request)){
            JSONMsg msg = new JSONMsg();
            msg.setSuccess(JSONMsg.FLAG_FAIL);
            msg.setMsg("未登录，请先登录！");
            WebUtils.responseWrite(response, JSONUtils.toJson(msg));
            return null;
        }else{
            return "redirect:/index";
        }
    }

    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response){
        if (WebUtils.isAjaxRequest(request)){
            JSONMsg msg = new JSONMsg();
            msg.setSuccess(JSONMsg.FLAG_FAIL);
            msg.setMsg("无访问权限！");
            WebUtils.responseWrite(response,JSONUtils.toJson(msg));
            return null;
        }else{
            return "redirect:/unauth";
        }
    }

    protected JSONMsg buildMsg(Page pageInfo){
        JSONMsg msg = new JSONMsg();
        msg.setSuccess(JSONMsg.FLAG_SUCCESS);
        msg.setPageInfo(pageInfo);
        return msg;
    }
    /**
     * 构建返回信息
     * @param success
     * @param data
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(boolean success,Object data,String msg){
        return new JSONMsg(success,data,msg);
    }

    /**
     * 构建返回信息
     * @param success
     * @return
     */
    protected JSONMsg buildMsg(boolean success){
        return new JSONMsg(success);
    }
    /**
     * 构建返回信息
     * @param data
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(Object data,String msg){
        return new JSONMsg(JSONMsg.FLAG_SUCCESS,data,msg);
    }

    /**
     * 构建返回信息
     * @param data
     * @return
     */
    protected JSONMsg buildMsg(Object data){
        return new JSONMsg(JSONMsg.FLAG_SUCCESS,data,"");
    }

    /**
     * 构建返回信息
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(String msg){
        return new JSONMsg(JSONMsg.FLAG_SUCCESS,null,msg);
    }

    /**
     * 构建失败返回信息
     * @param msg
     * @return
     */
    protected JSONMsg buildFaliMsg(String msg){
        return new JSONMsg(JSONMsg.FLAG_FAIL,null,msg);
    }
}