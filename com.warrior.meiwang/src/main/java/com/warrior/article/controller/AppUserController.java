package com.warrior.article.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.article.entity.AppUser;
import com.warrior.article.service.AppUserService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="AppUserController",tags = "用户管理",description = "用户管理")
@RestController
@RequestMapping("/appUser")
public class AppUserController extends WarriorBaseController {

    @Autowired
    private AppUserService appUserService;

    /**
    * 根据id获取用户管理
    *
    * @param id 
    * @return
    */
    @RequiresPermissions("admin:appUser:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取用户管理",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg queryAppUser(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(appUserService.selectById(id));
    }

    /**
    * 新增用户管理
    *
    * @param appUser
    * @return
    */
    @SysLog("新增用户管理")
    @RequiresPermissions("admin:appUser:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增用户管理",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addAppUser(@ModelAttribute AppUser appUser) {
        return buildMsg(appUserService.insert(appUser));
    }

    /**
    * 删除用户
    *
    * @param id
    * @return
    */
    @SysLog("删除用户")
    @RequiresPermissions("admin:appUser:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除appUser",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delAppUser(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {

        return buildMsg(appUserService.cancelUserById(id));
    }

    /**
     * 冻结用户
     *
     * @param id
     * @return
     */
    @SysLog("冻结用户")
    @RequiresPermissions("admin:appUser:del")
    @RequestMapping(value="/freezeUser/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "冻结appUser",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg disableAppUser(
            @ApiParam(name="id",value = "id",required = true)
            @PathVariable(value = "id") int id) {

        return buildMsg(appUserService.disableUserById(id));
    }

    /**
     * 解冻用户
     *
     * @param id
     * @return
     */
    @SysLog("冻结用户")
    @RequiresPermissions("admin:appUser:update")
    @RequestMapping(value="/enableUser/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "解冻用户",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg enableAppUser(
            @ApiParam(name="id",value = "id",required = true)
            @PathVariable(value = "id") int id) {

        return buildMsg(appUserService.enableUserById(id));
    }

    /**
    * 修改用户
    *
    * @param appUser
    * @return
    */
    @SysLog("修改用户管理")
    @RequiresPermissions("admin:appUser:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改用户管理",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modifiedAppUser(@ModelAttribute AppUser appUser) {
        AppUser au=new AppUser();
        au.setId(appUser.getId());
        au.setStatus(appUser.getStatus());
        au.setLevel(appUser.getLevel());
        return buildMsg(appUserService.insertOrUpdate(au));
    }

    /**
    * 查询用户管理列表
    *
    */
    @RequiresPermissions("admin:appUser:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户管理列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getAppUserList(
        @ApiParam(name="nickName",value = "昵称")
        @RequestParam(name = "nickName", defaultValue = "") String nickName,
        @ApiParam(name="status",value = "用户状态")
        @RequestParam(name = "status", defaultValue = "") String status,
        @ApiParam(name="phone",value = "电话号码")
        @RequestParam(name = "phone", defaultValue = "") String phone,
        @ApiParam(name="gender",value = "性别")
        @RequestParam(name = "gender", defaultValue = "") String gender,
        @ApiParam(name="page",value = "页码")
        @RequestParam(name="page",defaultValue = "1")int page,
        @ApiParam(name="rows",value = "分页大小")
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(appUserService.getPageList(new Page<AppUser>(page,rows),nickName,status,phone,gender));
    }
}