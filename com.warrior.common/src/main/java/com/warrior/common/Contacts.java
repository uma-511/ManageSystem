package com.warrior.common;

import com.warrior.util.common.PropUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Contacts {

    //失败
    public final static int CODE_FAIL = -1;
    //成功
    public final static int CODE_SUCCESS = 0;
    //账户不存在
    public final static int CODE_NO_ACCOUNT = 1;
    //账号被锁定或被删除！
    public final static int CODE_ACCOUNT_LOCK = 2;
    //密码错误
    public final static int CODE_PASSWORD_ERROR = 3;
    //密码错误5次
    public final static int CODE_PASSWORD_ERROR_5 =4;
    //用户类型错误
    public final static int CODE_USERTYPE_ERROR = 11;
    //登录失败
    public final static int CODE_LOGIN_FAIL = 5;
    //token失效
    public final static int CODE_TOKEN_FAIL = 6;
    //请求超时
    public final static int CODE_REQUEST_TIMEOUT = 7;
    //签名错误
    public final static int CODE_SIGN_ERROR = 8;
    //无访问权限
    public final static int CODE_NO_PERMISSION = 9;
    //提出用户(同一账号在其他地方登陆时会踢出当前会话)
    public final static int CODE_OTHER_LOGIN = 10;

    //待办事项未完成
    public final static int NOTE_STATUS_NORMAL = 1;
    //待办事项已完成
    public final static int NOTE_STATUS_OVER = 2;


    public final static String MD5_SALT = "0202040103";

    public final static String SHIRO_EXCEPTION = "shiroException";

    public static String URL_NO_CHECK [] = {};

    public static Map<Integer,String> ERROR_MSG = new HashMap<>();

    static {
        String url = PropUtils.getPropValue("noLoginCheck");
        if(StringUtils.isNotEmpty(url)){
            URL_NO_CHECK = url.split(",");
        }

        ERROR_MSG.put(CODE_FAIL,"操作失败！");
        ERROR_MSG.put(CODE_SUCCESS,"操作成功！");
        ERROR_MSG.put(CODE_NO_ACCOUNT,"账户不存在！");
        ERROR_MSG.put(CODE_ACCOUNT_LOCK,"账户被锁定！");
        ERROR_MSG.put(CODE_PASSWORD_ERROR,"密码错误！");
        ERROR_MSG.put(CODE_PASSWORD_ERROR_5,"密码错误5次账户锁定10分钟！");
        ERROR_MSG.put(CODE_LOGIN_FAIL,"登录失败！");
        ERROR_MSG.put(CODE_TOKEN_FAIL,"Token失败，请重新获取！");
        ERROR_MSG.put(CODE_REQUEST_TIMEOUT,"请求超时！");
        ERROR_MSG.put(CODE_SIGN_ERROR,"签名错误！");
        ERROR_MSG.put(CODE_NO_PERMISSION,"无访问权限！");
        ERROR_MSG.put(CODE_USERTYPE_ERROR,"用户类型错误");
    }
}