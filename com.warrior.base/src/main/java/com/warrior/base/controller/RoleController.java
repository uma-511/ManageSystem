package com.warrior.base.controller;

import com.warrior.common.JSONMsg;
import com.warrior.base.entity.Role;
import com.warrior.base.service.RoleService;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value="RoleController",tags = "角色管理",description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController extends WarriorBaseController {

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("admin:role:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getRoleList(
            @ApiParam(name="roleName",value = "角色名称")
            @RequestParam(value = "roleName", defaultValue = "") String roleName,
            @ApiParam(name="status",value = "状态")
            @RequestParam(value = "status", defaultValue = "-1") int status,
            @ApiParam(name="page",value = "页码")
            @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(name="rows",value = "分页大小")
            @RequestParam(value = "rows", defaultValue = "10") int rows) {
        return buildMsg(roleService.getRoleList(roleName, status, page, rows));
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @SysLog("新增角色")
    @RequiresPermissions("admin:role:add")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    @ApiOperation(value = "新增角色",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addRole(@ModelAttribute Role role) {
        role.setUpdateTime(new Date());
        role.setCreateTime(new Date());
        return buildMsg(roleService.insert(role));
    }

    /**
     * 根据ID获取角色
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:role:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg get(
            @ApiParam(name="id",value = "角色ID",required = true)
            @PathVariable(value = "id") long id) {
        return buildMsg(roleService.selectById(id));
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @SysLog("修改角色")
    @RequiresPermissions("admin:role:update")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "修改角色",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modified(@ModelAttribute Role role) {
        role.setUpdateTime(new Date());
        return buildMsg(roleService.insertOrUpdate(role));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @SysLog("删除角色")
    @RequiresPermissions("admin:role:del")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delete(
            @ApiParam(name="id",value = "角色ID",required = true)
            @PathVariable(value = "id") long id) {
        return buildMsg(roleService.deleteById(id));
    }

    @RequiresPermissions("admin:userrole:view")
    @RequestMapping(value = "/list/{uid}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户角色列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getRoleByUser(
            @ApiParam(name="uid",value="角色ID",required = true)
            @PathVariable(value = "uid") long uid) {
        return buildMsg(roleService.getRoleByUser(uid));
    }

    @SysLog("修改用户角色信息")
    @RequiresPermissions("admin:userrole:update")
    @RequestMapping(value = "/userRole", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户角色",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg updateUserRole(
            @ApiParam(name="uid",value = "用户ID",required = true)
            @RequestParam(value = "uid", defaultValue = "0") long uid,
            @ApiParam(name="permissions",value = "角色ID",required = true)
            @RequestParam(value = "permissions", defaultValue = "") String permissions) {
        return buildMsg(roleService.updateUserRole(uid,permissions));
    }
}