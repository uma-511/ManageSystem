package com.warrior.permissions.controller;

import com.google.common.collect.Maps;
import com.warrior.permissions.service.PermissionService;
import com.warrior.user.model.UserModel;
import com.warrior.util.common.Contacts;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.SessionUtil;
import com.warrior.util.web.WarriorBaseController;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/permission")
@Log4j
public class PermissionController extends WarriorBaseController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据类型获取相关权限信息
     *
     * @param ownId 权限拥有者ID
     * @return
     */
    @RequiresPermissions("admin:userperm:view")
    @RequestMapping(value = "/user/{ownId}", method = RequestMethod.GET)
    public JSONMsg getUserPermission(@PathVariable(value = "ownId") long ownId) {
        return buildMsg(permissionService.getPermission(ownId,1));
    }

    /**
     * 获取用户权限
     *
     * @return
     */
    @RequiresPermissions("admin:userperm:view")
    @RequestMapping(value = "/userPermission", method = RequestMethod.GET)
    public JSONMsg getUserPermission() {
        UserModel entity = SessionUtil.getValue(Contacts.SESSION_USER);
        Map<String,Object> data = Maps.newHashMap();
        data.put("list",permissionService.getUserPermission(entity.getUid()));
        data.put("permStr",permissionService.getPermissionStr(entity.getUid()));
        return buildMsg(data);
    }

    /**
     * 修改用户权限
     *
     * @param uid
     * @param permissions
     * @return
     */
    @RequiresPermissions("admin:userperm:update")
    @RequestMapping(value = "/userPermission", method = RequestMethod.PUT)
    public JSONMsg updateUserPermission(
            @RequestParam(value = "uid", defaultValue = "") long uid,
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
    @RequiresPermissions("admin:roleperm:update")
    @RequestMapping(value = "/rolePermission", method = RequestMethod.PUT)
    public JSONMsg updateRolePermission(
            @RequestParam(value = "rid", defaultValue = "") long rid,
            @RequestParam(value = "permissions", defaultValue = "") String permissions) {
        return buildMsg(permissionService.updatePermission(rid, 0, permissions));
    }

    @RequiresPermissions("admin:roleperm:view")
    @RequestMapping(value = "/role/{ownId}", method = RequestMethod.GET)
    public JSONMsg getRolePermission(@PathVariable(value = "ownId") long ownId) {
        return buildMsg(permissionService.getPermission(ownId,0));
    }
}