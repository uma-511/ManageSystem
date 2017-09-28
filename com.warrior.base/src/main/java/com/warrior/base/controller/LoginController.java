package com.warrior.base.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import com.warrior.common.model.UserModel;
import com.warrior.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController extends WarriorBaseController {

    @Autowired
    private UserService userService;

    @SysLog("用户登录")
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public JSONMsg doLogin(String userName, String passWord, HttpServletRequest request){
        UserModel entity = userService.login(userName,passWord);
        return buildMsg(entity,"登录成功！");
    }

    @SysLog("用户登出")
    @RequestMapping(value="/doLogOut",method = {RequestMethod.GET,RequestMethod.POST})
    public JSONMsg logOut(){
        userService.logOut();
        return buildMsg("登出成功！");
    }
}