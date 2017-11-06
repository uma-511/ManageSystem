package com.warrior.common.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.common.JSONMsg;
import com.warrior.util.common.JSONUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WarriorBaseController {

    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public JSONMsg authenticationException(HttpServletRequest request, HttpServletResponse response) {
        JSONMsg msg = new JSONMsg();
        msg.setSuccess(JSONMsg.FLAG_FAIL);
        msg.setLogin(false);
        msg.setMsg("未登录，请先登录！");
        return msg;
    }

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public JSONMsg authorizationException(HttpServletRequest request, HttpServletResponse response) {
        JSONMsg msg = new JSONMsg();
        msg.setSuccess(JSONMsg.FLAG_FAIL);
        msg.setMsg("无访问权限！");
        return msg;
    }

    protected JSONMsg buildMsg(Page pageInfo) {
        JSONMsg msg = new JSONMsg();
        msg.setSuccess(JSONMsg.FLAG_SUCCESS);
        msg.setPageInfo(pageInfo);
        return msg;
    }

    /**
     * 构建返回信息
     *
     * @param success
     * @param data
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(boolean success, Object data, String msg) {
        return new JSONMsg(success, data, msg);
    }

    /**
     * 构建返回信息
     *
     * @param success
     * @return
     */
    protected JSONMsg buildMsg(boolean success) {
        return new JSONMsg(success);
    }

    /**
     * 构建返回信息
     *
     * @param data
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(Object data, String msg) {
        return new JSONMsg(JSONMsg.FLAG_SUCCESS, data, msg);
    }

    /**
     * 构建返回信息
     *
     * @param data
     * @return
     */
    protected JSONMsg buildMsg(Object data) {
        return new JSONMsg(JSONMsg.FLAG_SUCCESS, data, "");
    }

    /**
     * 构建返回信息
     *
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(String msg) {
        return new JSONMsg(JSONMsg.FLAG_SUCCESS, null, msg);
    }

    /**
     * 构建失败返回信息
     *
     * @param msg
     * @return
     */
    protected JSONMsg buildFaliMsg(String msg) {
        return new JSONMsg(JSONMsg.FLAG_FAIL, null, msg);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }
}