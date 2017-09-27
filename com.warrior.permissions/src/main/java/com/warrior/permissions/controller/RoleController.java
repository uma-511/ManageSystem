package com.warrior.permissions.controller;

import com.warrior.permissions.entity.Role;
import com.warrior.permissions.service.RoleService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/role")
public class RoleController extends WarriorBaseController {

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("admin:role:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONMsg getRoleList(
            @RequestParam(value = "roleName", defaultValue = "") String roleName,
            @RequestParam(value = "status", defaultValue = "-1") int status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "rows", defaultValue = "10") int rows) {
        return buildMsg(roleService.getRoleList(roleName, status, page, rows));
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @RequiresPermissions("admin:role:add")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public JSONMsg addRole(Role role) {
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
    public JSONMsg get(@PathVariable(value = "id") long id) {
        return buildMsg(roleService.selectById(id));
    }

    /**
     * 修改角色
     *
     * @param id
     * @param role
     * @return
     */
    @RequiresPermissions("admin:role:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JSONMsg modified(@PathVariable(value = "id") long id, Role role) {
        role.setRid(id);
        role.setUpdateTime(new Date());
        return buildMsg(roleService.insertOrUpdate(role));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:role:del")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JSONMsg delete(@PathVariable(value = "id") long id) {
        return buildMsg(roleService.deleteById(id));
    }

    @RequiresPermissions("admin:userrole:view")
    @RequestMapping(value = "/list/{uid}", method = RequestMethod.GET)
    public JSONMsg getRoleByUser(@PathVariable(value = "uid") long uid) {
        return buildMsg(roleService.getRoleByUser(uid));
    }

    @RequiresPermissions("admin:userrole:update")
    @RequestMapping(value = "/userRole", method = RequestMethod.PUT)
    public JSONMsg updateUserRole(@RequestParam(value = "uid", defaultValue = "0") long uid, @RequestParam(value = "permissions", defaultValue = "") String permissions) {
        return buildMsg(roleService.updateUserRole(uid,permissions));
    }
}