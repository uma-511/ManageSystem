package com.warrior.base.controller;

import com.google.common.collect.Maps;
import com.warrior.base.entity.User;
import com.warrior.base.service.PermissionService;
import com.warrior.base.service.UserService;
import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import com.warrior.common.web.WarriorSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "PermissionController",tags = "权限管理",description = "权限管理")
@RestController
@RequestMapping("/permission")
@Log4j
public class PermissionController extends WarriorBaseController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    /**
     * 获取用户权限信息
     *
     * @param ownId 权限拥有者ID
     * @return
     */
    @RequiresPermissions("admin:userperm:view")
    @RequestMapping(value = "/user/{ownId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户权限信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getUserPermission(
            @ApiParam(name="ownId",value = "用户ID",required = true)
            @PathVariable(value = "ownId") long ownId) {
        return buildMsg(permissionService.getPermission(ownId,1));
    }

    /**
     * 获取用户权限
     *
     * @return
     */
    @RequiresPermissions("admin:userperm:view")
    @RequestMapping(value = "/userPermission", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户权限信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getUserPermission(HttpServletRequest request) {
        User user = WarriorSession.getItem(request.getParameter("token"));
        Map<String,Object> data = Maps.newHashMap();
        data.put("list",permissionService.getUserPermission(user.getUid()));
        data.put("permStr",permissionService.getPermissionStr(user.getUid()));
        return buildMsg(data);
    }

    /**
     * 修改用户权限
     *
     * @param uid
     * @param permissions
     * @return
     */
    @SysLog("修改用户权限")
    @RequiresPermissions("admin:userperm:update")
    @RequestMapping(value = "/userPermission", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户权限信息",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg updateUserPermission(
            @ApiParam(name="uid",value = "用户ID",required = true)
            @RequestParam(value = "uid", defaultValue = "") long uid,
            @ApiParam(name="permissions",value = "权限值",required = true)
            @RequestParam(value = "permissions", defaultValue = "") String permissions) {
        return buildMsg(permissionService.updatePermission(uid, 1, permissions));
    }

    /**
     * 修改角色权限
     *
     * @param rid
     * @param permissions
     * @return
     */
    @SysLog("修改角色权限")
    @RequiresPermissions("admin:roleperm:update")
    @RequestMapping(value = "/rolePermission", method = RequestMethod.PUT)
    @ApiOperation(value = "修改角色权限信息",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg updateRolePermission(
            @ApiParam(name="rid",value = "角色ID",required = true)
            @RequestParam(value = "rid", defaultValue = "") long rid,
            @ApiParam(name="permissions",value = "权限值",required = true)
            @RequestParam(value = "permissions", defaultValue = "") String permissions) {
        return buildMsg(permissionService.updatePermission(rid, 0, permissions));
    }

    @RequiresPermissions("admin:roleperm:view")
    @RequestMapping(value = "/role/{ownId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色权限信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getRolePermission(
            @ApiParam(name="ownId",value = "角色ID",required = true)
            @PathVariable(value = "ownId") long ownId) {
        return buildMsg(permissionService.getPermission(ownId,0));
    }
}