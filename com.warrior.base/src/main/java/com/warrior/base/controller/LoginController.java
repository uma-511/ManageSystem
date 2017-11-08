package com.warrior.base.controller;

import com.warrior.common.service.UserService;
import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "LoginController",tags = "用户登录",description = "用户登录")
@RestController
public class LoginController extends WarriorBaseController {

    @Autowired
    private UserService userService;

    @SysLog("用户登录")
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg doLogin(
            @ApiParam(name="userName",value = "用户名",required = true)
            @RequestParam(name = "userName",defaultValue = "") String userName,
            @ApiParam(name="passWord",value = "用户密码",required = true)
            @RequestParam(name = "passWord",defaultValue = "") String passWord, HttpServletRequest request){
        return buildMsg(userService.login(userName,passWord),"登录成功！");
    }

    @SysLog("用户登出")
    @RequestMapping(value="/doLogOut",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "用户登出",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg logOut(){
        userService.logOut();
        return buildMsg("登出成功！");
    }
}