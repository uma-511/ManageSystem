package com.warrior.user.controller;

import com.warrior.user.entity.User;
import com.warrior.user.service.UserService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController extends WarriorBaseController {

    @Autowired
    private UserService userService;

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:user:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public JSONMsg queryUser(@PathVariable(value = "id") long id) {
        return buildMsg(userService.selectById(id));
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("admin:user:add")
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
    @RequiresPermissions("admin:user:del")
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
    @RequiresPermissions("admin:user:update")
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
    @RequiresPermissions("admin:user:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONMsg getUserList(
            @RequestParam(name = "userName", defaultValue = "") String userName,
            @RequestParam(name = "userType", defaultValue = "-1") int userType,
            @RequestParam(name = "status", defaultValue = "-1") int status,
            @RequestParam(name="startTime")Date startTime,
            @RequestParam(name="endTime")Date endTime,
            @RequestParam(name="page",defaultValue = "1")int page,
            @RequestParam(name="rows",defaultValue = "10")int rows) {
        return buildMsg(userService.getUserList(userName, userType, status, startTime, endTime, page, rows));
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }
}