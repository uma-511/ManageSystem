package com.warrior.permissions.controller;

import com.warrior.permissions.service.PermissionService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param type  权限类型
     * @return
     */
    @RequestMapping(value = "/userPermission/{type}/{ownId}", method = RequestMethod.GET)
    public JSONMsg getPermission(@PathVariable(value = "ownId") long ownId, @PathVariable(value = "type") int type) {
        return buildMsg(permissionService.getPermission(ownId, type));
    }

    /**
     * 获取用户权限
     *
     * @return
     */
    @RequestMapping(value = "/userPermission", method = RequestMethod.GET)
    public JSONMsg getUserPermission() {
        return buildMsg(permissionService.getUserPermission());
    }

    /**
     * 修改用户权限
     *
     * @param uid
     * @param permissions
     * @return
     */
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
    @RequestMapping(value = "/rolePermission", method = RequestMethod.PUT)
    public JSONMsg updateRolePermission(
            @RequestParam(value = "rid", defaultValue = "") long rid,
            @RequestParam(value = "permissions", defaultValue = "") String permissions) {
        return buildMsg(permissionService.updatePermission(rid, 0, permissions));
    }
}