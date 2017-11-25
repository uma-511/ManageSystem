package com.warrior.common.web;

import com.warrior.common.Contacts;
import com.warrior.common.JSONMsg;
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
        msg.setCode(Contacts.CODE_TOKEN_FAIL);
        msg.setMsg("未登录，请先登录！");
        return msg;
    }

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public JSONMsg authorizationException(HttpServletRequest request, HttpServletResponse response) {
        JSONMsg msg = new JSONMsg();
        msg.setCode(Contacts.CODE_NO_PERMISSION);
        msg.setMsg("无访问权限！");
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
    protected JSONMsg buildMsg(int success, Object data, String msg) {
        return new JSONMsg(success, data, msg);
    }
    protected JSONMsg buildMsg(int success, String msg) {
        return new JSONMsg(success, msg);
    }
    /**
     * 构建返回信息
     *
     * @param success
     * @return
     */
    protected JSONMsg buildMsg(int success) {
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
        return new JSONMsg(Contacts.CODE_SUCCESS, data, msg);
    }

    /**
     * 构建返回信息
     *
     * @param data
     * @return
     */
    protected JSONMsg buildMsg(Object data) {
        return new JSONMsg(Contacts.CODE_SUCCESS, data, "");
    }

    /**
     * 构建返回信息
     *
     * @param msg
     * @return
     */
    protected JSONMsg buildMsg(String msg) {
        return new JSONMsg(Contacts.CODE_SUCCESS, null, msg);
    }

    protected JSONMsg buildMsg(Boolean val) {
        return new JSONMsg(val ? Contacts.CODE_SUCCESS : Contacts.CODE_FAIL, null, val ? "" : "操作失败！");
    }
    /**
     * 构建失败返回信息
     *
     * @param msg
     * @return
     */
    protected JSONMsg buildFaliMsg(String msg) {
        return new JSONMsg(Contacts.CODE_FAIL, null, msg);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }
}