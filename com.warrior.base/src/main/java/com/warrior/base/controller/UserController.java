package com.warrior.base.controller;

import com.warrior.base.entity.User;
import com.warrior.base.service.UserService;
import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import com.warrior.common.web.WarriorSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(value = "UserController", tags = "用户管理", description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController extends WarriorBaseController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "验证密码", httpMethod = "POST", response = JSONMsg.class)
    @RequestMapping(value = "/vailPassword", method = RequestMethod.POST)
    public JSONMsg vailPassword(
            @ApiParam(name = "password", value = "用户密码", required = true)
            @RequestParam(value = "passWord", defaultValue = "") String passWord) {
        return buildMsg(userService.vailPassword(passWord),"");
    }

    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET", response = JSONMsg.class)
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public JSONMsg getCurrentUser(
            @ApiParam(name = "token", value = "Token", required = true)
                    String token) {
        User user = WarriorSession.getItem(token);
        return buildMsg(userService.selectById(user.getUid()));
    }

    @ApiOperation(value = "获取当前用户的基本信息", httpMethod = "GET", response = JSONMsg.class)
    @RequestMapping(value = "/getBaseInfo",method = RequestMethod.GET)
    public JSONMsg getSimpleUserInfo(){
        return buildMsg(userService.getUserSimpleInfo());
    }

    @SysLog(value = "修改用户信息")
    @ApiOperation(value = "修改当前用户信息", httpMethod = "POST", response = JSONMsg.class)
    @RequestMapping(value = "/updateCurrentUser", method = RequestMethod.POST)
    public JSONMsg updateCurrent(@ModelAttribute User user){
        user.setUpdateTime(new Date());
        return buildMsg(userService.updateById(user));
    }

    @SysLog(value = "修改用户头像")
    @RequestMapping(value = "/updateHeadImage",method = RequestMethod.POST)
    @ApiOperation(value = "修改当前用户头像", httpMethod = "POST", response = JSONMsg.class)
    public JSONMsg updateHeadImage(HttpServletRequest request){
        return buildMsg(userService.uploadImg(request),"");
    }

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:user:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", response = JSONMsg.class)
    public JSONMsg queryUser(
            @ApiParam(name = "id", value = "用户ID", required = true)
            @PathVariable(value = "id") long id) {
        return buildMsg(userService.selectById(id));
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @SysLog("新增用户")
    @RequiresPermissions("admin:user:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增用户", httpMethod = "POST", response = JSONMsg.class)
    public JSONMsg addUser(@ModelAttribute User user) {
        return buildMsg(userService.insert(user));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @SysLog("删除用户")
    @RequiresPermissions("admin:user:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除用户", httpMethod = "DELETE", response = JSONMsg.class)
    public JSONMsg delUser(
            @ApiParam(name = "id", value = "用户ID", required = true)
            @PathVariable(value = "id") long id) {
        return buildMsg(userService.delete(id));
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @SysLog("修改用户")
    @RequiresPermissions("admin:user:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改用户信息", httpMethod = "PUT", response = JSONMsg.class)
    public JSONMsg modifiedUser(@ModelAttribute User user) {
        user.setUpdateTime(new Date());
        return buildMsg(userService.modified(user));
    }

    /**
     * 查询用户列表
     *
     * @param userName
     * @param userType
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @param rows
     * @return
     */
    @RequiresPermissions("admin:user:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表", httpMethod = "GET", response = JSONMsg.class)
    public JSONMsg getUserList(
            @ApiParam(name = "userName", value = "用户名")
            @RequestParam(name = "userName", defaultValue = "") String userName,
            @ApiParam(name = "userType", value = "用户类型")
            @RequestParam(name = "userType", defaultValue = "-1") int userType,
            @ApiParam(name = "status", value = "状态")
            @RequestParam(name = "status", defaultValue = "-1") int status,
            @ApiParam(name = "startTime", value = "注册时间-开始时间")
            @RequestParam(name = "startTime") Date startTime,
            @ApiParam(name = "endTime", value = "注册时间-结束时间")
            @RequestParam(name = "endTime") Date endTime,
            @ApiParam(name = "page", value = "页码")
            @RequestParam(name = "page", defaultValue = "1") int page,
            @ApiParam(name = "rows", value = "分页大小")
            @RequestParam(name = "rows", defaultValue = "10") int rows) {
        return buildMsg(userService.getUserList(userName, userType, status, startTime, endTime, page, rows));
    }

    @SysLog("修改密码")
    @RequestMapping(value = "/password/new", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户密码", httpMethod = "PUT", response = JSONMsg.class)
    public JSONMsg updatePassWord(
            @ApiParam(name = "oldPw", value = "旧密码", required = true)
            @RequestParam(name = "oldPw", defaultValue = "") String oldPw,
            @ApiParam(name = "newPw", value = "新密码", required = true)
            @RequestParam(name = "newPw", defaultValue = "") String newPw) {
        return buildMsg(userService.updatePassWord(oldPw, newPw));
    }
}