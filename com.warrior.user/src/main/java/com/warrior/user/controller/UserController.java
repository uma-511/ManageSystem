package com.warrior.user.controller;

import com.warrior.user.entity.User;
import com.warrior.user.entity.UserEntity;
import com.warrior.user.service.UserService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController extends WarriorBaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping(value="/getCurrentUser",method = {RequestMethod.GET})
    public JSONMsg getCurrentUser(){
        UserEntity entity = userService.getCurrentUser();
        return buildMsg(entity);
    }

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:user:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public JSONMsg queryUser(@PathVariable(value = "id") long id) {
        return buildMsg(userService.getById(id));
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = {"", "/"}, method = {RequestMethod.POST})
    public JSONMsg addUser(User user) {
        return buildMsg(userService.insert(user));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    public JSONMsg delUser(@PathVariable(value = "id") long id) {
        return buildMsg(userService.delete(id));
    }

    /**
     * 修改用户信息
     *
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT})
    public JSONMsg modifiedUser(@PathVariable(value = "id") long id, User user) {
        user.setUid(id);
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
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONMsg getUserList(String userName,
                               Integer userType,
                               Integer status,
                               Date startTime,
                               Date endTime,
                               Integer page,
                               Integer rows) {
        return buildMsg(userService.getUserList(userName, userType, status, startTime, endTime, page, rows));
    }
}