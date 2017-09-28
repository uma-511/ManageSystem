package com.warrior.base.controller;

import com.warrior.common.JSONMsg;
import com.warrior.base.entity.Role;
import com.warrior.base.service.RoleService;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
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
    @SysLog("新增角色")
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
    @SysLog("修改角色")
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
    @SysLog("删除角色")
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

    @SysLog("修改用户角色信息")
    @RequiresPermissions("admin:userrole:update")
    @RequestMapping(value = "/userRole", method = RequestMethod.PUT)
    public JSONMsg updateUserRole(@RequestParam(value = "uid", defaultValue = "0") long uid, @RequestParam(value = "base", defaultValue = "") String permissions) {
        return buildMsg(roleService.updateUserRole(uid,permissions));
    }
}