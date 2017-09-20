package com.warrior.user.controller;

import com.warrior.user.entity.UserEntity;
import com.warrior.user.service.UserService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController extends WarriorBaseController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public JSONMsg doLogin(String userName, String passWord, HttpServletRequest request){
        UserEntity entity = userService.login(userName,passWord);
        return buildMsg(entity,"登录成功！");
    }

    @RequestMapping(value="/doLogOut",method = {RequestMethod.GET,RequestMethod.POST})
    public JSONMsg logOut(){
        userService.logOut();
        return buildMsg("登出成功！");
    }
}