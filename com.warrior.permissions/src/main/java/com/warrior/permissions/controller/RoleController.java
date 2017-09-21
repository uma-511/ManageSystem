package com.warrior.permissions.controller;

import com.warrior.permissions.entity.Role;
import com.warrior.permissions.service.RoleService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController extends WarriorBaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 新增角色
     * @param role
     * @return
     */
    @RequestMapping(value = {"","/"},method = RequestMethod.POST)
    public JSONMsg addRole(Role role){
        return buildMsg(roleService.insert(role));
    }

    /**
     * 根据ID获取角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONMsg get(@PathVariable(value="id") long id){
        return buildMsg(roleService.selectById(id));
    }

    /**
     * 修改角色
     * @param id
     * @param role
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public JSONMsg modified(@PathVariable(value="id") long id,Role role){
        role.setRid(id);
        return buildMsg(roleService.insertOrUpdate(role));
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public JSONMsg delete(@PathVariable(value="id") long id){
        return buildMsg(roleService.deleteById(id));
    }

    /**
     * 根据用户ID获取角色
     * @param uid
     * @return
     */
    @RequestMapping(value = "/list/{uid}",method = RequestMethod.GET)
    public JSONMsg getRoleByUser(@PathVariable(value = "uid") long uid){
        return buildMsg(roleService.getRoleListByUser(uid));
    }
}